package com.zfml.kmpnoteappcleanarchitecture.data.local

import androidx.room.RoomDatabase

expect class DatabaseFactory {
    fun create(): RoomDatabase.Builder<NoteDatabase>
}