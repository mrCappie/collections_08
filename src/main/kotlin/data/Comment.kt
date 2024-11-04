package org.example.data

data class Comment(
    val id: ULong = 0uL,
    val noteId: ULong,
    val message: String,
    val deleteMark: Boolean = false

)
