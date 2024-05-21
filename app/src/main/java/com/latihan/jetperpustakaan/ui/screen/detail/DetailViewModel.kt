package com.latihan.jetperpustakaan.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.latihan.jetperpustakaan.data.BooksRepository
import com.latihan.jetperpustakaan.model.OrderBooks
import com.latihan.jetperpustakaan.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: BooksRepository) :ViewModel() {
    private val _uiState: MutableStateFlow<UiState<OrderBooks>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<OrderBooks>>
        get() = _uiState

    fun getBooksId(bookId: Long) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(repository.getBookId(bookId))
        }
    }
}