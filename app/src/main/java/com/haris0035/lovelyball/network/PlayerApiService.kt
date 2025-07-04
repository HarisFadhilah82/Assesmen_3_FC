package com.haris0035.lovelyball.network

import com.haris0035.lovelyball.model.OpStatus
import com.haris0035.lovelyball.model.Player
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

private const val BASE_URL = "https://store.sthresearch.site/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface PlayerApiService {
    @GET("pemain_bola.php")
    suspend fun getPlayer(
        @Header("Authorization") userID: String
    ): List<Player>

    @Multipart
    @POST("pemain_bola.php")
    suspend fun postPlayer(
        @Header("Authorization") userID: String,
        @Part("nama") nama: RequestBody,
        @Part("no_punggung") no_punggung: RequestBody,
        @Part image: MultipartBody.Part
    ): OpStatus

    @Multipart
    @POST("pemain_bola.php")
    suspend fun updatePlayerWithoutImage(
        @Header("Authorization") userId: String,
        @Part("id") id: RequestBody,
        @Part("nama") nama: RequestBody,
        @Part("no_punggung") noPunggung: RequestBody
    ): OpStatus

    @DELETE("pemain_bola.php")
    suspend fun deletePlayer(
        @Header("Authorization") userId: String,
        @Query("id") id: String
    ): OpStatus
}

object PlayerApi {
    val service: PlayerApiService by lazy {
        retrofit.create(PlayerApiService::class.java)
    }

    fun getPlayerUrl(imageId: String): String {
        return "${BASE_URL}image.php?id=$imageId"
    }
}

enum class ApiStatus { LOADING, SUCCESS, FAILED }
