package com.sdk.planetnew.presentation.ui.favorite

import android.app.Application
import androidx.lifecycle.*
import com.sdk.planetnew.data.model.Planet
import com.sdk.planetnew.domain.favorite.FavoriteRepository
import kotlinx.coroutines.launch

class FavoriteViewModel(
    application: Application,
    private val favoriteRepository: FavoriteRepository
) : AndroidViewModel(application) {
    fun getAllPlanets() = favoriteRepository.getAllPlanets()

    fun deleteAllPlanets() = viewModelScope.launch {
        favoriteRepository.deleteAllPlanets()
    }

    fun deleteAllNumbers() = viewModelScope.launch {
        favoriteRepository.deleteAllNumbers()
    }
}