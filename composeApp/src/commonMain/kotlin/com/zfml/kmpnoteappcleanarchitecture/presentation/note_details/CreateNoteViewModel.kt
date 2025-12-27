package com.zfml.kmpnoteappcleanarchitecture.presentation.note_details

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
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CreateNoteViewModel(
    private val noteRepository: NoteRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val noteIdArgs = savedStateHandle.toRoute<Route.NoteDetail>()

    private val _uiState = MutableStateFlow(CreateNoteUiState())

    val uiState: StateFlow<CreateNoteUiState> = _uiState.asStateFlow()


    init {
        val noteId = noteIdArgs.id
        if(noteId != -1L) {
            loadNote(noteId)
        }
    }

    private fun loadNote(noteId: Long) {
        viewModelScope.launch {
            noteRepository.getNoteById(noteId).onSuccess { note ->
                _uiState.update {
                    it.copy(
                        title = note.title,
                        description = note.description,
                        isEditing = true
                    )
                }
            }.onFailure { error ->
                _uiState.update {
                    it.copy(
                        errorMessage = error.message.toString()
                    )
                }
            }
        }
    }

    fun onTitleChange(newTitle: String) {
        _uiState.update { it.copy(title = newTitle) }
    }

    fun onDescriptionChange(newDescription: String) {
        _uiState.update { it.copy(description = newDescription) }
    }


    fun saveNote() {

        val currentState = _uiState.value
        viewModelScope.launch {
            val note = Note(
                id = if(noteIdArgs.id == -1L) 0 else noteIdArgs.id,
                title = currentState.title,
                description = currentState.description
            )
            noteRepository.insertNote(note)
                .onSuccess {
                    _uiState.update { it.copy(isSuccess = true) }
                }
                .onFailure { error ->
                    _uiState.update { it.copy(errorMessage = error.message.toString())  }
                }
        }
    }


}

data class CreateNoteUiState(
    val title: String = "",
    val description: String = "",
    val errorMessage: String = "",
    val isSuccess: Boolean = false,
    val isEditing: Boolean = false
)