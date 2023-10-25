package com.example.gym.service

import com.example.gym.model.CardsResponse
import retrofit2.http.GET

interface CardService {

    @GET("v1/cards")
    suspend fun getCards(): CardsResponse
}