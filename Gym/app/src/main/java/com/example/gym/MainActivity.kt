package com.example.gym

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.gym.databinding.ActivityMainBinding
import com.example.gym.view.Cadastro
import com.example.gym.view.Home
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        /*FirebaseApp.initializeApp(this);
        val auth = FirebaseAuth.getInstance()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.btLogin.setOnClickListener {
            val email = binding.editNome.text.toString()
            val senha = binding.ediSenha.text.toString()

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

                    auth.signInWithEmailAndPassword(email, senha)
                        .addOnCompleteListener { logar ->
                            if (logar.isSuccessful) {
                                mensagem(it, "Sucesso ao logar usuário", "#0000ff")
                                binding.editNome.setText("")
                                binding.ediSenha.setText("")
                                navegarPraHome(email)
                            }
                        }.addOnFailureListener { erro ->
                            mensagem(it, "E-mail ou senha errada", "#ff0000")
                        }
                }
            }
        }
        binding.btCadastrar.setOnClickListener {
            val intent = Intent(this, Cadastro::class.java)
            startActivity(intent)
        }

    }

    private fun mensagem(view: View, mensagem: String, color: String) {
        val snackbar = Snackbar.make(view, mensagem, Snackbar.LENGTH_SHORT)
        snackbar.setBackgroundTint(Color.parseColor(color))
        snackbar.setTextColor(Color.parseColor("#FFFFFF"))
        snackbar.show()
    }

    private fun navegarPraHome(nome: String) {
        val intent = Intent(this, Home::class.java)
        intent.putExtra("nome", nome)
        startActivity(intent)
    }*/


    }
}