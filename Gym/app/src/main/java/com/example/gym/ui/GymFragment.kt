package com.example.gym.ui

import android.content.Intent
import android.graphics.Color
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.gym.R
import com.example.gym.databinding.ActivityMainBinding
import com.example.gym.databinding.FragmentGymBinding
import com.example.gym.view.Cadastro
import com.example.gym.view.Home
import com.firebase.ui.auth.AuthUI
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth

class GymFragment : Fragment(R.layout.fragment_gym) {
    private var _binding: FragmentGymBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        FirebaseApp.initializeApp(requireActivity());
        val auth = FirebaseAuth.getInstance()


        _binding = FragmentGymBinding.inflate(inflater, container, false)

        binding.btCadastrar.setOnClickListener {
            findNavController().navigate(R.id.cadastroFragment)
        }

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

        return binding.root
    }

    private fun navegarPraHome(email: String) {
        val direction = GymFragmentDirections.actionGymFragmentToHomeFragment(email)
        findNavController().navigate(direction)
    }

    private fun mensagem(view: View, mensagem: String, color: String) {
        val snackbar = Snackbar.make(view, mensagem, Snackbar.LENGTH_SHORT)
        snackbar.setBackgroundTint(Color.parseColor(color))
        snackbar.setTextColor(Color.parseColor("#FFFFFF"))
        snackbar.show()
    }


}