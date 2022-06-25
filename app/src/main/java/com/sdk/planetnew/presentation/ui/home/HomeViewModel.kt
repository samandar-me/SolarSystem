package com.sdk.planetnew.presentation.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sdk.planetnew.data.model.Planet
import com.sdk.planetnew.domain.main.HomeRepository
import com.sdk.planetnew.data.util.extensions.log

class HomeViewModel : ViewModel() {
    private val homeRepository = HomeRepository()
    private val planetsList: MutableLiveData<List<Planet>> by lazy {
        MutableLiveData<List<Planet>>().also {
            loadPlanetsData()
        }
    }

    fun getPlanetList(): LiveData<List<Planet>> {
        return planetsList
    }

    private fun loadPlanetsData() {
        homeRepository.queryPlanets().addOnCompleteListener {
            if (it.isSuccessful) {
                val result = it.result
                if (!result.isEmpty) {
                    planetsList.value = result.toObjects(Planet::class.java)
                } else {
                    planetsList.value!!.plus(result.toObjects(Planet::class.java))
                }
            }
        }.addOnFailureListener {
            log(it.stackTraceToString())
        }
    }
}