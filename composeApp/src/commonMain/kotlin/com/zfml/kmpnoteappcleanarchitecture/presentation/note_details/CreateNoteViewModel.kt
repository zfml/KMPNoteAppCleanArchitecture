package com.zfml.kmpnoteappcleanarchitecture.presentation.note_details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.zfml.kmpnoteappcleanarchitecture.app.Route
import com.zfml.kmpnoteappcleanarchitecture.domain.model.Note
import com.zfml.kmpnoteappcleanarchitecture.domain.repository.NoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CreateNoteViewModel(
    private val noteRepository: NoteRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val noteIdArgs = savedStateHandle.toRoute<Route.NoteDetail>()

    private val _uiState = MutableStateFlow(CreateNoteUiState())

    val uiState: StateFlow<CreateNoteUiState> = _uiState.asStateFlow()


    var noteId by mutableStateOf("")
    init {
        noteId = noteIdArgs.id.toString()
    }

    fun createNote(note: Note) {
        viewModelScope.launch {
            val result = noteRepository.insertNote(note)
            if(result.isSuccess) {
                _uiState.value = _uiState.value.copy(isSuccess = true)
            } else {
                _uiState.value = _uiState.value.copy(isSuccess = false, errorMessage = "Something went wrong!")
            }
        }
    }


}

data class CreateNoteUiState(
    val errorMessage: String = "",
    val isSuccess: Boolean = false
)