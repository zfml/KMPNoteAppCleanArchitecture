package com.zfml.kmpnoteappcleanarchitecture.domain.repository

import com.zfml.kmpnoteappcleanarchitecture.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    suspend fun searchNote(searchQuery: String): Flow<Result<List<Note>>>

     fun getAllNotes(): Flow<Result<List<Note>>>

    suspend fun insertNote(note: Note): Result<Unit>

    suspend fun deleteNote(note: Note): Result<Unit>

    suspend fun getNoteById(id: Long): Result<Note>

}