package com.zfml.kmpnoteappcleanarchitecture.presentation.notes

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zfml.kmpnoteappcleanarchitecture.domain.model.Note
import com.zfml.kmpnoteappcleanarchitecture.domain.repository.NoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class NoteListViewModel(
    private val noteRepository: NoteRepository
) : ViewModel() {

    val uiState: StateFlow<NoteListState> = noteRepository.getAllNotes()
        .map { result ->
            result.fold(
                onSuccess = { notes ->
                    NoteListState(
                        noteList = notes,
                        isLoading = false,
                        errorMessage = ""
                    )
                },
                onFailure = { error ->
                    NoteListState(
                        noteList = emptyList(),
                        isLoading = false,
                        errorMessage = error.message.toString()
                    )
                }
            )
        }
        .onStart {
            emit(NoteListState(isLoading = true))
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = NoteListState(isLoading = true)
        )

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            noteRepository.deleteNote(note)
        }
    }
}
