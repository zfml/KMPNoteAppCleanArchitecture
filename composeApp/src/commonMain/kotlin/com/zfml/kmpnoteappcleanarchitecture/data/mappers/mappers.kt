package com.zfml.kmpnoteappcleanarchitecture.data.mappers

import com.zfml.kmpnoteappcleanarchitecture.data.local.model.NoteEntity
import com.zfml.kmpnoteappcleanarchitecture.domain.model.Note

fun NoteEntity.toNote() = Note(
    id = id,
    title = title,
    content = content,
    createdDate = createdDate
)

fun Note.toNoteEntity() = NoteEntity(
    id = id,
    title = title,
    content = content,
    createdDate = createdDate
)