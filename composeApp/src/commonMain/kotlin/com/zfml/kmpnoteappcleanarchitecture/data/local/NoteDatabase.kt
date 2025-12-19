package com.zfml.kmpnoteappcleanarchitecture.data.local

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import com.zfml.kmpnoteappcleanarchitecture.data.local.model.NoteEntity

@Database(entities = [NoteEntity::class], version = 1)
@ConstructedBy(NoteDatabaseConstructor::class)

abstract class NoteDatabase: RoomDatabase(){
    abstract fun getNoteDao(): NoteDao

    companion object {
        const val DB_NAME = "notes.db"
    }
}

@Suppress("KotlinNoActualForExpect")
expect object NoteDatabaseConstructor: RoomDatabaseConstructor<NoteDatabase> {
    override fun initialize(): NoteDatabase
}