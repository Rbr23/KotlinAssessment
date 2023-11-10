package com.example.gym.ui

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.example.gym.R
import com.example.gym.databinding.FragmentGymBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class GymFragment : Fragment(R.layout.fragment_gym) {
    private var _binding: FragmentGymBinding? = null
    private val binding get() = _binding!!

    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        FirebaseApp.initializeApp(requireActivity());
        val auth = FirebaseAuth.getInstance()

        val gson =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(
                getString(
                    com.firebase.ui.auth.R.string.default_web_client_id
                )
            ).requestEmail().build()

        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gson)


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

        binding.btnGoogle.setOnClickListener { signInWithGoogle() }

        return binding.root
    }

    private fun signInWithGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        launcher.launch(signInIntent)
    }

    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                handleResults(task)
            }
        }

    private fun handleResults(task: Task<GoogleSignInAccount>) {
        if (task.isSuccessful) {
            val account: GoogleSignInAccount? = task.result
            if (account != null) {
                updateUi(account)
            }
        }
    }

    private fun updateUi(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)


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