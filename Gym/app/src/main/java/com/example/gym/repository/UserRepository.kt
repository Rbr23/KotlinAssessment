package com.example.gym.repository

import com.example.gym.model.UserEntity
import com.example.gym.util.UIState

interface UserRepository {
    fun getUser(email: String, result: (UIState<List<UserEntity>>) -> Unit)
    fun update(email: String, user: UserEntity, result: (UIState<List<UserEntity>>) -> Unit)
    fun delete(email: String, user: UserEntity, result: (UIState<List<UserEntity>>) -> Unit)
    fun addUser(user: UserEntity, email: String, result: (UIState<List<UserEntity>>) -> Unit)
}