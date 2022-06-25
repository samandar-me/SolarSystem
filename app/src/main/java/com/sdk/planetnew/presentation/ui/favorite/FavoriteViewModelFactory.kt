package com.sdk.planetnew.presentation.ui.favorite

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sdk.planetnew.domain.favorite.FavoriteRepository

class FavoriteViewModelFactory(
    private val application: Application,
    private val favoriteRepository: FavoriteRepository
): ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FavoriteViewModel(application, favoriteRepository) as T
    }
}