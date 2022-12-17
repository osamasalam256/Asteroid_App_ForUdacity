package com.udacity.asteroidradar.repository

import com.udacity.asteroidradar.api.NetRequestConverter
import com.udacity.asteroidradar.asDataBaseModel
import com.udacity.asteroidradar.database.AsteroidDatabase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AsteroidRepo(private val database: AsteroidDatabase) {

    suspend fun refreshAstroData(){
        withContext(Dispatchers.IO){
            val asteroidsData = NetRequestConverter.getAsteroidsList().asDataBaseModel()
            database.asteroidDao.insertAll(asteroidsData)

        }
    }
}

//class ImageRepo(private val ImgDatabase: ImageDatabase){
//
//    suspend fun refreshImageData(){
//        withContext(Dispatchers.IO){
//         val imgData = NetRequestConverter.getImage()
//         ImgDatabase.imageDao.insertImage(imgData)
//        }
//    }
//}

