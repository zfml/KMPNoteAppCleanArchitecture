package com.zfml.kmpnoteappcleanarchitecture.di

import com.zfml.kmpnoteappcleanarchitecture.data.local.DatabaseFactory
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module
    get() = module {
        single { DatabaseFactory() }
    }


