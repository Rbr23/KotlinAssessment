package com.example.gym.ui

import android.content.Intent
import android.graphics.Color
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.gym.R
import com.example.gym.databinding.FragmentCadastroBinding
import com.example.gym.databinding.FragmentGymBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import kotlinx.coroutines.Dispatchers

class CadastroFragment : Fragment() {

    private var _binding: FragmentCadastroBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: CadastroViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        FirebaseApp.initializeApp(requireActivity());
        val auth = FirebaseAuth.getInstance()
        _binding = FragmentCadastroBinding.inflate(layoutInflater)

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
                                //   navegarPraMain(email)
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

        return binding.root;
    }

    private fun mensagem(view: View, mensagem: String, color: String) {
        val snackbar = Snackbar.make(view, mensagem, Snackbar.LENGTH_SHORT)
        snackbar.setBackgroundTint(Color.parseColor(color))
        snackbar.setTextColor(Color.parseColor("#FFFFFF"))
        snackbar.show()
    }

}