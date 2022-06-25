package com.sdk.planetnew.presentation.ui.fav_detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.sdk.planetnew.data.model.Number
import com.sdk.planetnew.data.model.Planet
import com.sdk.planetnew.domain.favorite.FavDetailRepository
import kotlinx.coroutines.launch

class FavDetailViewModel(
    application: Application,
    private val favDetailRepository: FavDetailRepository
) : AndroidViewModel(application) {
    fun deleteFromDatabase(planet: Planet) = viewModelScope.launch {
        favDetailRepository.deleteFromDatabase(planet)
    }
    fun deleteNumber(num: Number) = viewModelScope.launch {
        favDetailRepository.deleteNumber(num)
    }
}