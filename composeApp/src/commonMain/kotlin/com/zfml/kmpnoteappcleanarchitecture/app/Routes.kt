package com.zfml.kmpnoteappcleanarchitecture.app

import kotlinx.serialization.Serializable

sealed interface Route {

    @Serializable
    data object NoteGraph: Route

    @Serializable
    data object NoteList: Route

    @Serializable
    data class NoteDetail(val id: Long): Route
}