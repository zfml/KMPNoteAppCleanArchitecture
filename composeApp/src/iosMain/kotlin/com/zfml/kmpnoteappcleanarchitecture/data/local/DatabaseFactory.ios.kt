package com.zfml.kmpnoteappcleanarchitecture.data.local

import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDirectory

actual class DatabaseFactory {
    actual fun create(): RoomDatabase.Builder<NoteDatabase> {
        val dbFile = documentDirectory() + "/${NoteDatabase.DB_NAME}"

        return Room.databaseBuilder<NoteDatabase>(
            name = dbFile
        )
    }


    @OptIn(ExperimentalForeignApi::class)
    private fun documentDirectory(): String {
        val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
            directory = NSDocumentDirectory,
            inDomain = NSUserDirectory,
            appropriateForURL = null,
            create = false,
            error = null
        )
        return requireNotNull(documentDirectory?.path)
    }
}