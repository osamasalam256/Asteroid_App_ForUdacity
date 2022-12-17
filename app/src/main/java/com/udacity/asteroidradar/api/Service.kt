package com.udacity.asteroidradar.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants.API
import com.udacity.asteroidradar.Constants.BASE_URL
import com.udacity.asteroidradar.PictureOfDay
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


const val asteroidsKey = "neo/rest/v1/feed"
const val imageKey = "planetary/apod"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory()).build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface AsteroidsApiService {

    @GET(asteroidsKey)
    fun getAllAsteroid(@Query("api_key") apiKey: String): String

    @GET(imageKey)
    suspend fun getImageOfDay(@Query("api_key") apiKey: String): PictureOfDay


}

object NetRequestConverter{
    fun getAsteroidsList(): List<Asteroid> {
        val asteroidResult = AsteroidsApi.retrofitService.getAllAsteroid(API)
        return parseAsteroidsJsonResult(JSONObject(asteroidResult))
    }

    suspend fun getImage(): PictureOfDay{
        return AsteroidsApi.retrofitService.getImageOfDay(API)
    }
}
object AsteroidsApi{
    val retrofitService: AsteroidsApiService by lazy {
         retrofit.create(AsteroidsApiService::class.java)
    }

}