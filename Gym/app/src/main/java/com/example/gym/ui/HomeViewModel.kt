package com.example.gym.ui

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gym.api.ApiClient
import com.example.gym.model.Card
import com.example.gym.model.CardsResponse
import com.example.gym.service.CardService
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    val cards = MutableLiveData<List<CardsResponse>>()
    val msg = MutableLiveData<String>()

    init {
        viewModelScope.launch {
            try {
                val serviceCards = ApiClient.card().getCards()
                cards.postValue(listOf(serviceCards))

            } catch (e: Exception) {
                msg.postValue(e.message)
            }
        }
    }

}