package com.example.gym.view

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.gym.R
import com.example.gym.databinding.ActivityCadastroBinding
import com.example.gym.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import kotlinx.coroutines.Dispatchers.Main

class Cadastro : AppCompatActivity() {
    private lateinit var binding: ActivityCadastroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this);
        val auth = FirebaseAuth.getInstance()
        binding = ActivityCadastroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.btCadastro.setOnClickListener {
            val email = binding.editEmailCadastro.text.toString()
            val senha = binding.ediSenhaCadastro.text.toString()

            when {
                email.isEmpty() -> {
                    mensagem(it, "Coloque o teu email!", "#FF0000")
                }

                senha.isEmpty() -> {
                    mensagem(it, "Preencha a senha!", "#FF0000")
                }

                senha.length <= 5 -> {
                    mensagem(it, "Senha precisa ser maior que 5 caracteres", "#FF0000")
                }

                else -> {
                    auth.createUserWithEmailAndPassword(email, senha)
                        .addOnCompleteListener { cadastro ->
                            if (cadastro.isSuccessful) {
                                mensagem(it, "Sucesso ao cadastrar usuário", "#0000ff")
                                binding.editEmailCadastro.setText("")
                                binding.ediSenhaCadastro.setText("")
                                navegarPraMain(email)
                            }
                        }.addOnFailureListener { exception ->
                            val mensagemErro = when (exception) {
                                is FirebaseAuthInvalidCredentialsException -> "Digite um e-mail válido!"
                                is FirebaseAuthUserCollisionException -> "Está conta já existe"
                                is FirebaseNetworkException -> "Sem conexão com a internet"
                                else -> "Erro ao cadastrar usuário"
                            }

                            mensagem(it, mensagemErro, "#ff0000")

                        }
                }
            }
        }
    }

    private fun mensagem(view: View, mensagem: String, color: String) {
        val snackbar = Snackbar.make(view, mensagem, Snackbar.LENGTH_SHORT)
        snackbar.setBackgroundTint(Color.parseColor(color))
        snackbar.setTextColor(Color.parseColor("#FFFFFF"))
        snackbar.show()
    }

    private fun navegarPraMain(nome: String) {
        val intent = Intent(this, Main::class.java)
        intent.putExtra("nome", nome)
        startActivity(intent)
    }
}