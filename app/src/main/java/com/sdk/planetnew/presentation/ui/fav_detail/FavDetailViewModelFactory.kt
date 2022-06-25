package com.sdk.planetnew.presentation.ui.fav_detail

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sdk.planetnew.domain.favorite.FavDetailRepository

class FavDetailViewModelFactory(
    private val application: Application,
    private val favDetailRepository: FavDetailRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FavDetailViewModel(application, favDetailRepository) as T
    }
}