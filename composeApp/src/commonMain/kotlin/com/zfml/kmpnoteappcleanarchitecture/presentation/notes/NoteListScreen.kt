package com.zfml.kmpnoteappcleanarchitecture.presentation.notes

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.EditNote
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.zfml.kmpnoteappcleanarchitecture.domain.model.Note
import com.zfml.kmpnoteappcleanarchitecture.presentation.notes.components.AddNoteDialog
import com.zfml.kmpnoteappcleanarchitecture.presentation.notes.components.EmptyNotePlaceholder
import com.zfml.kmpnoteappcleanarchitecture.presentation.notes.components.EmptySearchResult
import com.zfml.kmpnoteappcleanarchitecture.presentation.notes.components.NoteItem
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun NoteListScreenRoot(
    viewModel: NoteListViewModel = koinViewModel<NoteListViewModel>(),
    navigateToCreateNoteScreen: (Long) -> Unit
) {

    val state by viewModel.uiState.collectAsStateWithLifecycle()

    NoteListScreen(
        state,
        navigateToCreateNoteScreen = { nodeId ->
            navigateToCreateNoteScreen(nodeId)
        },
        onDeleteNote = { note ->
            viewModel.deleteNote(note)
        },
        onQueryChange = {
            viewModel.onSearchQueryChange(it)
        }
    )



}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteListScreen(
    state: NoteListState,
    onDeleteNote: (Note) -> Unit,
    onQueryChange: (String) -> Unit,
    navigateToCreateNoteScreen: (Long) -> Unit,
) {

    var localSearchQuery by remember { mutableStateOf(state.searchQuery) }

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = {
                        Text(
                            "My Notes",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                )
                NoteSearchBar(
                    query = localSearchQuery,
                    onQueryChange = { newQuery ->
                        localSearchQuery = newQuery
                        onQueryChange(newQuery)
                    }
                )
            }

        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navigateToCreateNoteScreen(-1)
                },
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Note")
            }
        }
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                state.isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                state.noteList.isEmpty() && state.isSearching -> {
                    EmptySearchResult(
                        query = state.searchQuery,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                state.noteList.isEmpty() -> {
                    EmptyNotePlaceholder(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                else -> {
                    LazyVerticalStaggeredGrid(
                        columns = StaggeredGridCells.Fixed(2),
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(12.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalItemSpacing = 10.dp
                    ) {
                        items(state.noteList) { note ->
                            NoteItem(
                                note,
                                onDeleteNote = onDeleteNote,
                                navigateToCreateNoteScreen = {
                                    navigateToCreateNoteScreen(note.id)
                                }
                            )
                        }
                    }
                }
            }

        }
    }
}

@Composable
fun NoteSearchBar(
    query: String,
    onQueryChange: (String) -> Unit
) {
    TextField(
        value = query,
        onValueChange = onQueryChange,
        placeholder = { Text("Search your notes...") },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
        trailingIcon = {
            if (query.isNotEmpty()) {
                IconButton(onClick = { onQueryChange("") }) {
                    Icon(Icons.Default.Close, contentDescription = "Clear")
                }
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(24.dp), // Pill shaped
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
    )
}

