package com.example.gym.repository

import android.util.Log
import com.example.gym.model.UserEntity
import com.example.gym.util.UIState
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.Locale

class UserRepositoryImpl() : UserRepository {
    val db = Firebase.firestore

    override fun getUser(email: String, result: (UIState<List<UserEntity>>) -> Unit) {
        db.collection("agendamento")
            .whereEqualTo("cliente", email)
            .get()
            .addOnSuccessListener {
                var listaServicos: MutableList<UserEntity> = arrayListOf()
                for (doc in it) {
                    listaServicos.add(
                        doc.toObject(UserEntity::class.java)
                    )

                }
                result.invoke(UIState.Success(listaServicos))

            }.addOnFailureListener {
                result.invoke(UIState.Failure(it.localizedMessage))

            }
    }

    override fun update(user: UserEntity, result: (UIState<List<UserEntity>>) -> Unit) {
        db.collection("agendamento").document(user.cliente.toString())
            .update("data", user.data.toString(), "hora", user.hora.toString())
            .addOnCompleteListener {
                result.invoke(UIState.Success(listOf()))
            }.addOnFailureListener {
                result.invoke(UIState.Failure(it.localizedMessage))
            }
    }

    override fun delete(email: String, result: (UIState<List<UserEntity>>) -> Unit) {
        db.collection("agendamento")
            .document(email)
            .delete().addOnCompleteListener {
                result.invoke(UIState.Success(listOf()))
            }.addOnFailureListener {
                result.invoke(UIState.Failure(it.localizedMessage))
            }
    }

    override fun addUser(user: UserEntity, result: (UIState<List<UserEntity>>) -> Unit) {
        db.collection("agendamento").document(user.cliente.toString()).set(user)
            .addOnCompleteListener {
                result.invoke(UIState.Success(listOf()))//mensagem(view, "Agendamento realizado com sucesso!", "#FF03DAC5")
            }.addOnFailureListener {
                result.invoke(UIState.Failure(it.localizedMessage))//mensagem(view, "Erro no servidor", "#ff0000")
            }
    }


}