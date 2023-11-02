package com.example.gym.ui

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.gym.R
import com.example.gym.databinding.FragmentAgendamentoBinding
import com.example.gym.model.UserEntity
import com.example.gym.util.UIState
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Calendar
import java.util.Locale

class AgendamentoFragment : Fragment(R.layout.fragment_agendamento) {

    private var _binding: FragmentAgendamentoBinding? = null
    private val binding get() = _binding!!
    private val calendar: Calendar = Calendar.getInstance()
    private val args: AgendamentoFragmentArgs by navArgs()
    private var data: String = ""
    private var hora: String = ""
    private var email: String = ""
    private val viewModel: AgendamentoViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val direction =
                    AgendamentoFragmentDirections.actionAgendamentoFragmentToHomeFragment(args.email)
                findNavController().navigate(direction)
            }

        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

        _binding = FragmentAgendamentoBinding.inflate(
            inflater,
            container,
            false
        )

        val datePicker = binding.datePicker
        datePicker.setOnDateChangedListener { _, year, monthOfYear, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, monthOfYear)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            var dia = dayOfMonth.toString()
            var mes: String

            if (dayOfMonth < 10) {
                dia = "0$dayOfMonth"
            }
            if (monthOfYear < 10) {
                mes = "" + (monthOfYear + 1)
            } else {
                mes = (monthOfYear + 1).toString()
            }

            data = "$dia/$mes/$year"
        }

        binding.timePicker.setOnTimeChangedListener { _, hourOfDay, minute ->
            val minuto: String
            if (minute < 10) {
                minuto = "0$minute"
            } else {
                minuto = minute.toString()
            }
            hora = "$hourOfDay:$minuto"
        }

        binding.timePicker.setIs24HourView(true)

        binding.btnAgendar.setOnClickListener {
            email = binding.editNome.text.toString()
            when {
                hora.isEmpty() -> {
                    mensagem(it, "Preencha o horário!", "#FF0000")
                }

                hora < "8:00" && hora > "19:00" -> {
                    mensagem(it, "Academia fechada", "#FF0000")
                }

                data.isEmpty() -> {
                    mensagem(it, "Preencha a data", "#FF0000")
                }

                email.isEmpty() -> {
                    mensagem(it, "Coloque o e-mail do usuário!", "#FF0000")
                }

                data.isNotEmpty() && hora.isNotEmpty() && email.isNotEmpty() -> {
                    viewModel.addUser(
                        UserEntity(email.lowercase(Locale.ROOT), data, hora),
                        args.email
                    )
                    viewModel.users.observe(viewLifecycleOwner) { state ->
                        when (state) {
                            is UIState.Loading -> {

                            }

                            is UIState.Success -> {
                                mensagem(it, "Agendamento realizado com sucesso!", "#FF03DAC5")
                            }

                            is UIState.Failure -> {
                                mensagem(it, "Erro no servidor", "#ff0000")
                            }
                        }
                    }
                }
            }
        }

        return binding.root
    }

    private fun mensagem(view: View, mensagem: String, cor: String) {
        val snackbar = Snackbar.make(view, mensagem, Snackbar.LENGTH_SHORT)
        snackbar.setBackgroundTint(Color.parseColor(cor))
        snackbar.setTextColor(Color.parseColor("#FFFFFF"))
        snackbar.show()
    }

    private fun salvarAgendamento(view: View, cliente: String, data: String, hora: String) {
        val db = FirebaseFirestore.getInstance()

        val dadosusuario = hashMapOf(
            "cliente" to cliente.lowercase(Locale.ROOT),
            "data" to data.lowercase(Locale.ROOT),
            "hora" to hora.lowercase(Locale.ROOT)
        )

        db.collection("agendamento").document(cliente.lowercase(Locale.ROOT)).set(dadosusuario)
            .addOnCompleteListener {
                mensagem(view, "Agendamento realizado com sucesso!", "#FF03DAC5")
            }.addOnFailureListener {
                mensagem(view, "Erro no servidor", "#ff0000")
            }
    }

}