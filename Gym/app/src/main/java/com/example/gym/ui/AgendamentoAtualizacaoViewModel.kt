package com.example.gym.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gym.model.UserEntity
import com.example.gym.repository.UserRepositoryImpl
import com.example.gym.util.UIState

class AgendamentoAtualizacaoViewModel : ViewModel() {
    private val _users = MutableLiveData<UIState<List<UserEntity>>>()
    val users: LiveData<UIState<List<UserEntity>>> get() = _users
    val repository: UserRepositoryImpl = UserRepositoryImpl()

    fun updateUser(user: UserEntity) {
        _users.value = UIState.Loading
        repository.update(user) {
            _users.value = it
        }
    }
}