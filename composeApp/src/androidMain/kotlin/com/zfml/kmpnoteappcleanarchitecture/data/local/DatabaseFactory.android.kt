package com.zfml.kmpnoteappcleanarchitecture.data.local

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

actual class DatabaseFactory(
    private val context: Context
){
    actual fun create(): RoomDatabase.Builder<NoteDatabase> {
        val appContext = context.applicationContext
        val dbFile = appContext.getDatabasePath(NoteDatabase.DB_NAME)

        return Room.databaseBuilder(
            context = appContext,
            name = dbFile.absolutePath
        )
    }
}