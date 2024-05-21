package com.latihan.jetperpustakaan.model

data class Book(
    val id: Long,
    val photoUrl: Int,
    val nameBook: String,
    val author: String,
    val description: String,
    val releaseDate: String
)