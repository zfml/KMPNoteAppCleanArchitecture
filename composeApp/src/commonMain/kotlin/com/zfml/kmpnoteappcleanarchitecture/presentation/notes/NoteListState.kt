package com.zfml.kmpnoteappcleanarchitecture.presentation.notes

import com.zfml.kmpnoteappcleanarchitecture.domain.model.Note

data class NoteListState(
    val noteList: List<Note> = emptyList(),
    val searchQuery: String = "",
    val isLoading: Boolean = true,
    val errorMessage: String = "",
    val isSearching: Boolean = false
)
