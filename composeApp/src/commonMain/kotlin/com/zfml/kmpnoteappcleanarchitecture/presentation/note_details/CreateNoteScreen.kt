package com.zfml.kmpnoteappcleanarchitecture.presentation.note_details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zfml.kmpnoteappcleanarchitecture.domain.model.Note
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateNoteScreenRoot(
   viewModel: CreateNoteViewModel = koinViewModel<CreateNoteViewModel>(),
   navigateToNoteListScreen: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.isSuccess) {
        if(uiState.isSuccess) {
            navigateToNoteListScreen()
        }
    }
    CreateNoteScreen( onSaveNote = { note ->
        viewModel.createNote(note)
    })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateNoteScreen(
    onSaveNote: (Note) -> Unit
) {
    // State for text fields
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("") }, // Minimalist look, often no title in TopBar
                navigationIcon = {
                    IconButton(onClick = { /* Handle back */ }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onSaveNote(Note(title = title, content = description))
                },
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            ) {
                Icon(Icons.Default.Check, contentDescription = "Save Note")
            }

        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Note Title Input
            TransparentHintTextField(
                text = title,
                hint = "Title",
                onValueChange = { title = it },
                textStyle = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Note Description Body
            TransparentHintTextField(
                text = description,
                hint = "Start typing your note...",
                onValueChange = { description = it },
                textStyle = MaterialTheme.typography.titleMedium,
                modifier = Modifier.fillMaxHeight()
            )
        }
    }
}

@Composable
fun TransparentHintTextField(
    text: String,
    hint: String,
    onValueChange: (String) -> Unit,
    textStyle: TextStyle,
    modifier: Modifier = Modifier,
    singleLine: Boolean = false
) {
    Box(modifier = modifier) {
        if (text.isEmpty()) {
            Text(
                text = hint,
                style = textStyle,
                color = Color.LightGray
            )
        }
        BasicTextField(
            value = text,
            onValueChange = onValueChange,
            singleLine = singleLine,
            textStyle = textStyle.copy(color = MaterialTheme.colorScheme.onSurface),
            modifier = Modifier.fillMaxWidth(),
            cursorBrush = SolidColor(MaterialTheme.colorScheme.primary)
        )
    }
}