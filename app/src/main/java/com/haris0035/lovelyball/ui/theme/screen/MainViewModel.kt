package com.haris0035.lovelyball.ui.theme.screen

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

class MainViewModel : ViewModel() {
    var data = mutableStateOf(emptyList<Player>())
        private set
    var status = MutableStateFlow(ApiStatus.LOADING)
        private set
    init {
        retrieveData()
    }
    private fun retrieveData() {
        viewModelScope.launch(Dispatchers.IO) {
            status.value = ApiStatus.LOADING
            try {
                data.value = PlayerApi.service.getPlayer()
                status.value = ApiStatus.SUCCESS
            } catch (e: Exception) {
                Log.d("MainViewModel", "Failure: ${e.message}")
            }
        }
    }
}