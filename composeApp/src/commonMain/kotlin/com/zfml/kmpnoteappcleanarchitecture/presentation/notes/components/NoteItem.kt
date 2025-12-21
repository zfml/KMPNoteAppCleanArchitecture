package com.zfml.kmpnoteappcleanarchitecture.presentation.notes.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.zfml.kmpnoteappcleanarchitecture.core.util.formatEpochMillis
import com.zfml.kmpnoteappcleanarchitecture.domain.model.Note

@Composable
fun NoteItem(note: Note) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
        )
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(note.title, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(4.dp))
            Text(note.content, style = MaterialTheme.typography.bodySmall, maxLines = 5)
            Spacer(Modifier.height(8.dp))
            Text(
                text = formatEpochMillis(note.createdDate),
                style = MaterialTheme.typography.labelSmall, // Custom or smallest available
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}