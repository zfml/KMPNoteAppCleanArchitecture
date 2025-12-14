package com.zfml.kmpnoteappcleanarchitecture

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform