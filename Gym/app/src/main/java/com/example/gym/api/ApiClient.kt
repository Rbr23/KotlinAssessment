package com.example.gym.api

import com.example.gym.service.CardService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class ApiClient {
    companion object {
        private var retorfrit: Retrofit =
            Retrofit.Builder().baseUrl("https://api.magicthegathering.io/")
                .addConverterFactory(GsonConverterFactory.create()).build()

        fun card() = retorfrit.create(CardService::class.java)
    }
}