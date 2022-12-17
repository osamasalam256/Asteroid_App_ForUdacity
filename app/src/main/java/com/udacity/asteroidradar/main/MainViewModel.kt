package com.udacity.asteroidradar.main

import android.app.Application
import androidx.lifecycle.*
import com.udacity.asteroidradar.Constants.getDayDate
import com.udacity.asteroidradar.Constants.getLastDate
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.NetRequestConverter
import com.udacity.asteroidradar.database.*
import com.udacity.asteroidradar.domain.AsteroDomain
import com.udacity.asteroidradar.repository.AsteroidRepo
import kotlinx.coroutines.launch


class MainViewModel(application: Application) :AndroidViewModel(application) {

    private val asteroDatabase = AsteroidDatabase.getDatabase(application)
    private val asteroRepository = AsteroidRepo(asteroDatabase)

//    private val imgDatabase = ImageDatabase.getDatabase(application)
//    private val imgRepo = ImageRepo(imgDatabase)

    private val _asteroidList= MutableLiveData<List<AsteroDomain>>()
    val asteroidList: LiveData<List<AsteroDomain>> =_asteroidList

    private val _dayImage= MutableLiveData<PictureOfDay>()
        val dayImage: LiveData<PictureOfDay> = _dayImage

    private val _todayDate = MutableLiveData<String>()
    val todayDate: LiveData<String> = _todayDate

    private val _navigateToSelectedProperty = MutableLiveData<AsteroidEntity?>()
    val navigateToSelectedProperty: LiveData<AsteroidEntity?> = _navigateToSelectedProperty

    init {
        refreshData()
        //todayDate()
        getDayImage()
        getAllAstero()
    }

    private fun refreshData(){
        viewModelScope.launch {
            try {
                asteroRepository.refreshAstroData()

            }catch (e:Exception){

            }
        }
    }

    fun getAllAstero(){
        viewModelScope.launch {
            _asteroidList.value = asteroDatabase.asteroidDao.getAllAsteroids().value?.asDomainModel()
        }

    }
    fun weekAsteroids(){
        viewModelScope.launch {
        _asteroidList.value = asteroDatabase.asteroidDao
            .getAsteroidWithSpecificDate(getDayDate(), getLastDate()).value?.asDomainModel()
        }
    }
    fun dayAsteroids(){
        viewModelScope.launch {
            _asteroidList.value = asteroDatabase.asteroidDao
                .getAsteroidWithSpecificDate(getDayDate(), getDayDate()).value?.asDomainModel()
        }
    }

//    private fun todayDate(){
//        _todayDate.value = getDayDate()
//    }

    private fun getDayImage(){
        viewModelScope.launch {
            try{
                _dayImage.value = NetRequestConverter.getImage()
            }catch (e:Exception){

            }

        }
    }
//    fun refreshImage(){
//        viewModelScope.launch {
//            try {
//                todayDate()
//                if (_dayImage.value?.first()?.date != _todayDate.value ){
//                    imgDatabase.imageDao.clearPreviousImage()
//                    imgRepo.refreshImageData()
//                    getDayImage()
//                }else{
//                    getDayImage()
//                }
//            }catch (e:Exception){
//
//            }
//        }
//    }
    fun displayPropertyDetails(asteroProperty: AsteroidEntity) {
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