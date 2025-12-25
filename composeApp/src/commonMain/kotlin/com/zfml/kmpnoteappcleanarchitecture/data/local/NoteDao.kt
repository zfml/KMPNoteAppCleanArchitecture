package com.zfml.kmpnoteappcleanarchitecture.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.zfml.kmpnoteappcleanarchitecture.data.local.model.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Insert
    suspend fun insertNote(noteEntity: NoteEntity)

    @Query("SELECT * FROM NoteEntity WHERE title=:title")
    fun searchNotesByTitle(title: String): Flow<List<NoteEntity>>

    @Query("SELECT * FROM NoteEntity")
    fun getAllNotesAsFlow(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM NoteEntity WHERE id=:id")
    suspend fun getNoteById(id: Long): NoteEntity

    @Delete
    suspend fun deleteNote(noteEntity: NoteEntity)
}