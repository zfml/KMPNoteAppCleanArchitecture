package com.zfml.kmpnoteappcleanarchitecture.presentation.notes

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zfml.kmpnoteappcleanarchitecture.domain.model.Note
import com.zfml.kmpnoteappcleanarchitecture.domain.repository.NoteRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class NoteListViewModel(
    private val noteRepository: NoteRepository
) : ViewModel() {

    private val searchQuery = MutableStateFlow("")

    fun onSearchQueryChange(query: String) {
        searchQuery.value = query
    }
    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val uiState: StateFlow<NoteListState> =
        searchQuery
            .debounce(300)
            .distinctUntilChanged()
            .flatMapLatest { query ->

                val isSearching = query.isNotBlank()

                val source = if (isSearching) {
                    noteRepository.searchNote(query)
                } else {
                    noteRepository.getAllNotes()

                }
                source.map { result ->
                    result.fold(
                        onSuccess = { notes ->
                            NoteListState(
                                noteList = notes,
                                searchQuery = searchQuery.value,
                                isLoading = false,
                                isSearching = isSearching
                            )
                        },
                        onFailure = { error ->
                            NoteListState(
                                errorMessage = error.message.orEmpty(),
                                isLoading = false,
                                isSearching = isSearching
                            )
                        }
                    )
                }
            }

            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = NoteListState(isLoading = true)
            )

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            noteRepository.deleteNote(note)
        }
    }
}
