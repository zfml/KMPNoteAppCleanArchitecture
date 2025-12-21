package com.zfml.kmpnoteappcleanarchitecture

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.zfml.kmpnoteappcleanarchitecture.app.Route
import com.zfml.kmpnoteappcleanarchitecture.presentation.notes.NoteListScreenRoot
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import kmpnoteappcleanarchitecture.composeapp.generated.resources.Res
import kmpnoteappcleanarchitecture.composeapp.generated.resources.compose_multiplatform

@Composable
@Preview
fun App() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Route.NoteGraph
    ) {
        navigation<Route.NoteGraph>(
            startDestination = Route.NoteList
        ) {
            composable<Route.NoteList>{
                NoteListScreenRoot()
            }
        }
    }
}