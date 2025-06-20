package com.haris0035.lovelyball.ui.theme.screen

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haris0035.lovelyball.model.Player
import com.haris0035.lovelyball.network.ApiStatus
import com.haris0035.lovelyball.network.PlayerApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream

class MainViewModel : ViewModel() {
    var data = mutableStateOf(emptyList<Player>())
        private set
    var status = MutableStateFlow(ApiStatus.LOADING)
        private set
    var errorMessage = mutableStateOf<String?>(null)

    fun retrieveData(userId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            status.value = ApiStatus.LOADING
            try {
                data.value = PlayerApi.service.getPlayer(userId)
                status.value = ApiStatus.SUCCESS
            } catch (e: Exception) {
                Log.d("MainViewModel", "Failure: ${e.message}")
                status.value = ApiStatus.FAILED
            }
        }
    }

    fun saveData(userId: String, nama: String, noPunggung: String, bitmap: Bitmap) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = PlayerApi.service.postPlayer(
                    userId,
                    nama.toRequestBody("text/plain".toMediaTypeOrNull()),
                    noPunggung.toRequestBody("text/plain".toMediaTypeOrNull()),
                    bitmap.toMultipartBody()
                )
                if (result.status == "success")
                    retrieveData(userId)
                else
                    throw Exception(result.message)
            } catch (e: Exception) {
                Log.d("MainViewModel", "Failure: ${e.message}")
                errorMessage.value = "Error: ${e.message}"
            }
        }
    }

    fun updatePlayerWithoutImage(
        userId: String,
        playerId: String,
        nama: String,
        noPunggung: String,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = PlayerApi.service.updatePlayerWithoutImage(
                    userId,
                    playerId.toRequestBody("text/plain".toMediaTypeOrNull()),
                    nama.toRequestBody("text/plain".toMediaTypeOrNull()),
                    noPunggung.toRequestBody("text/plain".toMediaTypeOrNull())
                )

                if (result.status == "success") {
                    retrieveData(userId)
                } else {
                    throw Exception(result.message)
                }
            } catch (e: Exception) {
                Log.d("MainViewModel", "Update failed: ${e.message}")
                errorMessage.value = "Error: ${e.message}"
            }
        }
    }

    fun deletePlayer(userId: String, playerId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = PlayerApi.service.deletePlayer(userId, playerId)
                if (result.status == "success") {
                    retrieveData(userId)
                } else {
                    throw Exception(result.message)
                }
            } catch (e: Exception) {
                Log.d("MainViewModel", "Delete failed: ${e.message}")
                errorMessage.value = "Error: ${e.message}"
            }
        }
    }





    private fun Bitmap.toMultipartBody(): MultipartBody.Part {
        val stream = ByteArrayOutputStream()
        compress(Bitmap.CompressFormat.JPEG, 80, stream)
        val byteArray = stream.toByteArray()
        val requestBody =
            byteArray.toRequestBody("image/jpg".toMediaTypeOrNull(), 0, byteArray.size)
        return MultipartBody.Part.createFormData("image", "image.jpg", requestBody)
    }

    fun clearMessage() {
        errorMessage.value = null
    }
}
