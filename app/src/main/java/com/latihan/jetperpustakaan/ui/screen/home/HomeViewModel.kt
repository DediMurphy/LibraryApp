package com.latihan.jetperpustakaan.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.latihan.jetperpustakaan.data.BooksRepository
import com.latihan.jetperpustakaan.model.OrderBooks
import com.latihan.jetperpustakaan.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: BooksRepository): ViewModel()
{
    private val _uiState: MutableStateFlow<UiState<List<OrderBooks>>> = MutableStateFlow(UiState.Loading)

    val uiState: StateFlow<UiState<List<OrderBooks>>>
        get() = _uiState

    fun getAllBooks() {
        viewModelScope.launch {
            repository.getAllBooks()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { orderBooks ->
                    _uiState.value = UiState.Success(orderBooks)
                }
        }
    }
}