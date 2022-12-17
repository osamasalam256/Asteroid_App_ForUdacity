package com.udacity.asteroidradar.detail

import android.app.Application
import androidx.lifecycle.*
import com.udacity.asteroidradar.domain.AsteroDomain


class DetailViewModel(app: Application, private val Asteroid: AsteroDomain): AndroidViewModel(app) {

    private val _asteroid = MutableLiveData<AsteroDomain>()
    val asteroid: LiveData<AsteroDomain> = _asteroid

    init {
        _asteroid.value = Asteroid
    }

}

@Suppress("UNCHECKED_CAST")
class DetailFactory(private val app: Application, private val asteroid: AsteroDomain): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(app, asteroid) as T
        }
        throw IllegalArgumentException("Unable to construct MainViewModel")
    }
}