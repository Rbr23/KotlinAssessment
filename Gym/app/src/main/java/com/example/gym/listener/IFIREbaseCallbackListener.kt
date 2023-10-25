package com.example.gym.listener

import com.example.gym.model.UserEntity
import java.util.Objects

interface IFIREbaseCallbackListener<T: List<UserEntity>> {
    fun onFirebaseLoadSuccess(T: Objects)
    fun onFirebaseLoadFailed(message: String)
}