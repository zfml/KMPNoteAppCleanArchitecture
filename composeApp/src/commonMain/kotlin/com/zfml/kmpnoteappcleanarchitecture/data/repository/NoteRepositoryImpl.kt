package com.zfml.kmpnoteappcleanarchitecture.data.repository

import com.zfml.kmpnoteappcleanarchitecture.data.local.NoteDao
import com.zfml.kmpnoteappcleanarchitecture.data.mappers.toNote
import com.zfml.kmpnoteappcleanarchitecture.data.mappers.toNoteEntity
import com.zfml.kmpnoteappcleanarchitecture.domain.model.Note
import com.zfml.kmpnoteappcleanarchitecture.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class NoteRepositoryImpl(
    private val noteDao: NoteDao
): NoteRepository {
    override suspend fun searchNote(searchQuery: String): Flow<Result<List<Note>>> =
        noteDao.searchNotesByTitle(title = searchQuery).map {
            noteEntities ->
            Result.success(noteEntities.map { it.toNote() })
        }.catch {
            emit(Result.failure(it))
        }

    override fun getAllNotes(): Flow<Result<List<Note>>> = noteDao.getAllNotesAsFlow().map {
        noteEntities ->
        Result.success(noteEntities.map { it.toNote() })
    }.catch {
        emit(Result.failure(it))
    }

    override suspend fun insertNote(note: Note): Result<Unit> =
        try {
            noteDao.insertNote(note.toNoteEntity())
            Result.success(Unit)
        }catch (e: Exception) {
            Result.failure(e)
        }



    override suspend fun getNoteById(id: Long): Result<Note> = try {
            Result.success(noteDao.getNoteById(id).toNote())
    }catch (e: Exception){
            Result.failure(e)
    }

}