package com.zfml.kmpnoteappcleanarchitecture

import androidx.compose.ui.window.ComposeUIViewController
import com.zfml.kmpnoteappcleanarchitecture.di.initKoin

fun MainViewController() = ComposeUIViewController (
    configure = {
        initKoin()
    }
){
    App()
}