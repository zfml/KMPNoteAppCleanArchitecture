package com.zfml.kmpnoteappcleanarchitecture.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.zfml.kmpnoteappcleanarchitecture.data.local.DatabaseFactory
import com.zfml.kmpnoteappcleanarchitecture.data.local.NoteDatabase
import com.zfml.kmpnoteappcleanarchitecture.data.repository.NoteRepositoryImpl
import com.zfml.kmpnoteappcleanarchitecture.domain.repository.NoteRepository
import com.zfml.kmpnoteappcleanarchitecture.presentation.note_details.CreateNoteViewModel
import com.zfml.kmpnoteappcleanarchitecture.presentation.notes.NoteListViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformModule : Module


val sharedModule = module {
    single {
        get<DatabaseFactory>().create()
            .setDriver(BundledSQLiteDriver())
            .setQueryCoroutineContext(Dispatchers.IO)
            .build()
    }

    single {
        get<NoteDatabase>().getNoteDao()
    }

    singleOf(::NoteRepositoryImpl).bind<NoteRepository>()

    viewModelOf(::NoteListViewModel)
    viewModelOf(::CreateNoteViewModel)


}