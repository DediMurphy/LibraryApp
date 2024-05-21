package com.latihan.jetperpustakaan.di

import com.latihan.jetperpustakaan.data.BooksRepository

object Injection {
    fun provideRepository(): BooksRepository {
        return BooksRepository().getInstance()
    }
}

