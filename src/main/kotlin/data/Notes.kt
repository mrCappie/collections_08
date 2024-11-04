package org.example.data

import java.time.LocalDate

data class Notes(
    val id: ULong = 0uL,
    val title: String,
    val text: String,
    val comments: Int = 0,
    val readComments: Int = 0,
    val viewUrl: String = "",
    val canComment: Boolean = true,
    val deleteMark: Boolean = false
)
