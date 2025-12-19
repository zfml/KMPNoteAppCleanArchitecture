package com.zfml.kmpnoteappcleanarchitecture

import android.app.Application
import com.zfml.kmpnoteappcleanarchitecture.di.initKoin
import org.koin.android.ext.koin.androidContext

class NoteApp: Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@NoteApp)
        }

    }
}