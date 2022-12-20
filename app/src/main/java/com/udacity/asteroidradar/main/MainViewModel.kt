package com.udacity.asteroidradar.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.udacity.asteroidradar.Constants.getDayDate
import com.udacity.asteroidradar.Constants.getLastDate
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.NetRequestConverter
import com.udacity.asteroidradar.database.*
import com.udacity.asteroidradar.domain.AsteroDomain
import com.udacity.asteroidradar.repository.AsteroidRepo
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class MainViewModel(application: Application) :AndroidViewModel(application) {

    private val asteroDatabase = getDatabase(application)
    private val asteroRepository = AsteroidRepo(asteroDatabase)


    private val _asteroidList= MutableLiveData<List<AsteroDomain>>()
    val asteroidList: LiveData<List<AsteroDomain>> =_asteroidList

    private val _dayImage= MutableLiveData<PictureOfDay>()
        val dayImage: LiveData<PictureOfDay> = _dayImage

    private val _navigateToSelectedProperty = MutableLiveData<AsteroDomain?>()
    val navigateToSelectedProperty: LiveData<AsteroDomain?> = _navigateToSelectedProperty

    init {
        refreshData()
        getDayImage()
        getAllAstero()
    }

    private fun refreshData(){
        viewModelScope.launch {
            try {
                asteroRepository.refreshAstroData()

            }catch (e:Exception){
            Log.i("MainViewModel", "Exception: ${e.localizedMessage}")
            }
        }
    }

    fun getAllAstero(){
        viewModelScope.launch {
            try {
                asteroDatabase.asteroidDao.getAllAsteroids().collect{
                    _asteroidList.value = it.asDomainModel()
                }
            }catch (e:Exception){
                Log.i("MainViewModel", "get data from DB Exception: ${e.localizedMessage}")
            }

        }

    }
    fun weekAsteroids(){
        viewModelScope.launch {
        asteroDatabase.asteroidDao
            .getAsteroidWithSpecificDate(getDayDate(), getLastDate()).collect{
                _asteroidList.value = it.asDomainModel()
            }
        }
    }
    fun dayAsteroids(){
        viewModelScope.launch {
            asteroDatabase.asteroidDao
                .getAsteroidWithSpecificDate(getDayDate(), getDayDate()).collect{
                    _asteroidList.value = it.asDomainModel()
                }
        }
    }

    private fun getDayImage(){
        viewModelScope.launch {
            try{
                _dayImage.value = NetRequestConverter.getImage()
            }catch (e:Exception){

            }

        }
    }

    fun displayPropertyDetails(asteroProperty: AsteroDomain) {
        _navigateToSelectedProperty.value = asteroProperty
    }

    fun displayPropertyDetailsComplete() {
        _navigateToSelectedProperty.value = null
    }
}

@Suppress("UNCHECKED_CAST")
class MainFactory(private val app: Application):ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(app) as T
        }
        throw IllegalArgumentException("Unable to construct MainViewModel")
    }
}