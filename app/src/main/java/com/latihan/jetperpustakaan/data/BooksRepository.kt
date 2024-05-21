package com.latihan.jetperpustakaan.data

import com.latihan.jetperpustakaan.model.Book
import com.latihan.jetperpustakaan.model.BooksData
import com.latihan.jetperpustakaan.model.OrderBooks
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.Flow


class BooksRepository {

    private val booksList = mutableListOf<OrderBooks>()

    fun getAllBooks(): Flow<List<OrderBooks>> {
        return flowOf(booksList)
    }

    init {
        if (booksList.isEmpty()) {
            BooksData.bookList.forEach {
                booksList.add(OrderBooks(it))
            }
        }
    }


    fun getBookId(bookId: Long): OrderBooks {
        return booksList.first {
            it.reward.id == bookId
        }
    }



    fun getInstance(): BooksRepository =
        instance ?: synchronized(this) {
            BooksRepository().apply {
                instance = this
            }
        }



    companion object {
        @Volatile
        private var instance: BooksRepository? = null

    }
}