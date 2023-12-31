package com.example.gym.repository

import android.util.Log
import com.example.gym.model.UserEntity
import com.example.gym.util.UIState
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.Locale

class UserRepositoryImpl() : UserRepository {
    val db = Firebase.firestore

    override fun getUser(email: String, result: (UIState<List<UserEntity>>) -> Unit) {
        db.collection(email).get()
            .addOnSuccessListener {
                var listaServicos: MutableList<UserEntity> = arrayListOf()
                it.documents.forEach {
                    listaServicos.add(
                        UserEntity(
                            it.data?.get("cliente").toString(),
                            it.data?.get("data").toString(),
                            it.data?.get("hora").toString()
                        )
                    )
                }
                result.invoke(UIState.Success(listaServicos))

            }.addOnFailureListener {
                result.invoke(UIState.Failure(it.localizedMessage))

            }
    }

    override fun update(
        email: String,
        user: UserEntity,
        result: (UIState<List<UserEntity>>) -> Unit
    ) {
        db.collection(email).document(user.cliente.toString())
            .update("data", user.data.toString(), "hora", user.hora.toString())
            .addOnCompleteListener {
                result.invoke(UIState.Success(listOf()))
            }.addOnFailureListener {
                result.invoke(UIState.Failure(it.localizedMessage))
            }
    }

    override fun delete(
        email: String,
        user: UserEntity,
        result: (UIState<List<UserEntity>>) -> Unit
    ) {
        db.collection(email)
            .document(user.cliente.toString())
            .delete().addOnCompleteListener {
                result.invoke(UIState.Success(listOf()))
            }.addOnFailureListener {
                result.invoke(UIState.Failure(it.localizedMessage))
            }
    }

    override fun addUser(
        user: UserEntity,
        email: String,
        result: (UIState<List<UserEntity>>) -> Unit
    ) {
        db.collection(email).document(user.cliente.toString()).set(user)
            .addOnCompleteListener {
                result.invoke(UIState.Success(listOf()))
            }.addOnFailureListener {
                result.invoke(UIState.Failure(it.localizedMessage))
            }
    }


}