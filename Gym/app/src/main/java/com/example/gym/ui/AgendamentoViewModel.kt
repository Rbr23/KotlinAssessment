package com.example.gym.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gym.model.UserEntity
import com.example.gym.repository.UserRepositoryImpl
import com.example.gym.util.UIState

class AgendamentoViewModel : ViewModel() {
    private val _users = MutableLiveData<UIState<List<UserEntity>>>()
    val users: LiveData<UIState<List<UserEntity>>> get() = _users
    val repository: UserRepositoryImpl = UserRepositoryImpl()

    fun addUser(user: UserEntity, email : String) {
        _users.value = UIState.Loading
        repository.addUser(user, email) {
            _users.value = it
        }
    }
}