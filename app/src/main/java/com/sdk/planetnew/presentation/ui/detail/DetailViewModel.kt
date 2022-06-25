package com.sdk.planetnew.presentation.ui.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.sdk.planetnew.data.model.Number
import com.sdk.planetnew.data.model.Planet
import com.sdk.planetnew.domain.detail.DetailRepository
import kotlinx.coroutines.launch

class DetailViewModel(
    application: Application,
    private val detailRepository: DetailRepository
) : AndroidViewModel(application) {

    fun savePlanet(planet: Planet) = viewModelScope.launch {
        detailRepository.savePlanet(planet)
    }

    fun saveNumber(num: Number) = viewModelScope.launch {
        detailRepository.saveNumber(num)
    }

    fun getAllNumbers() = detailRepository.getAllNumbers()
}