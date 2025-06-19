package com.haris0035.lovelyball.network

import com.haris0035.lovelyball.model.Player
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://store.sthresearch.site/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()
private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface PlayerApiService {
    @GET("static-api.json")
    suspend fun getPlayer(): List<Player>
}
object PlayerApi {
    val service: PlayerApiService by lazy {
        retrofit.create(PlayerApiService::class.java)
    }
    fun getPlayerUrl(imageId: String): String {
        return "$BASE_URL$imageId.jpg"
    }
}
enum class ApiStatus { LOADING, SUCCESS, FAILED }