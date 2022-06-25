package com.sdk.planetnew.presentation.ui.detail

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sdk.planetnew.domain.detail.DetailRepository

class DetailViewModelFactory(
    private val app: Application,
    private val detailRepository: DetailRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetailViewModel(app, detailRepository) as T
    }
}