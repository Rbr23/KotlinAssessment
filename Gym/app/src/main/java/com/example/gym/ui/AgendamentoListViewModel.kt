package com.example.gym.ui

import android.os.Looper
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gym.model.UserEntity
import com.example.gym.repository.UserRepository
import com.example.gym.repository.UserRepositoryImpl
import com.example.gym.util.UIState
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.logging.Logger
//import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

class AgendamentoListViewModel() : ViewModel() {

    private val _users = MutableLiveData<UIState<List<UserEntity>>>()
    val users: LiveData<UIState<List<UserEntity>>> get() = _users
    val repository: UserRepositoryImpl = UserRepositoryImpl()

    fun getUsers(email: String) {
        _users.value = UIState.Loading
        repository.getUser(email) {
            _users.value = it
        }
    }

    fun deleteUser(email: String) {
        _users.value = UIState.Loading
        repository.delete(email) {
            _users.value = it
        }
    }
}