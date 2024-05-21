package com.latihan.jetperpustakaan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.latihan.jetperpustakaan.data.BooksRepository
import com.latihan.jetperpustakaan.ui.screen.detail.DetailViewModel
import com.latihan.jetperpustakaan.ui.screen.home.HomeViewModel

class ViewModelFactory(private val repository: BooksRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}