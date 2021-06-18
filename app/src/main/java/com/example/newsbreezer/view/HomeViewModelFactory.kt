package com.example.newsbreezer.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newsbreezer.data.repository.MainRepository

@Suppress("UNCHECKED_CAST")
class HomeViewModelFactory(
        private val baseRepository: MainRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeViewModel(baseRepository) as T
    }
}