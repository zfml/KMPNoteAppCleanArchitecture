package com.zfml.kmpnoteappcleanarchitecture.domain.model

import kotlin.time.Clock
import kotlin.time.ExperimentalTime

class Note @OptIn(ExperimentalTime::class) constructor(
    val id: Long = 0,
    val title: String,
    val content: String,
    val createdDate: Long = Clock.System.now().toEpochMilliseconds(),
)