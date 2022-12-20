package com.udacity.asteroidradar.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.BuildConfig
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
 const val API = BuildConfig.NASA_API_KEY
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory()).build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

private val retrofit2 = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()
interface AsteroidsApiService {

    @GET(asteroidsKey)
    suspend fun getAllAsteroid(@Query("api_key") apiKey: String): String

    @GET(imageKey)
    suspend fun getImageOfDay(@Query("api_key") apiKey: String): PictureOfDay


}
// for Image of day request using moshi converter
private val retrofitService: AsteroidsApiService by lazy {
    retrofit.create(AsteroidsApiService::class.java)

}
// for Asteroid request using scalar converter
private val retrofitService2: AsteroidsApiService by lazy {
    retrofit2.create(AsteroidsApiService::class.java)

}
object NetRequestConverter{
   suspend fun getAsteroidsList(): List<Asteroid> {
        val asteroidResult = retrofitService2.getAllAsteroid(API)
        return parseAsteroidsJsonResult(JSONObject(asteroidResult))
    }

    suspend fun getImage(): PictureOfDay{
        return retrofitService.getImageOfDay(API)
    }
}
