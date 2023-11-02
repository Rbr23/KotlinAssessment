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
import androidx.compose.ui.text.toLowerCase
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.gym.R
import com.example.gym.databinding.FragmentAgendamentoAtualizacaoBinding
import com.example.gym.model.UserEntity
import com.example.gym.util.UIState
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Calendar
import java.util.Locale

class AgendamentoAtualizacaoFragment : Fragment(R.layout.fragment_agendamento_atualizacao) {

    private var _binding: FragmentAgendamentoAtualizacaoBinding? = null
    private val binding get() = _binding!!
    private val calendar: Calendar = Calendar.getInstance()
    private val args: AgendamentoAtualizacaoFragmentArgs by navArgs()
    private var data: String = ""
    private var hora: String = ""
    private val viewModel: AgendamentoAtualizacaoViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val direction =
                    AgendamentoAtualizacaoFragmentDirections.actionAgendamentoAtualizacaoFragmentToHomeFragment(
                        args.email
                    )
                findNavController().navigate(direction)
            }

        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)


        _binding = FragmentAgendamentoAtualizacaoBinding.inflate(
            inflater,
            container,
            false
        )

        val email = args.email
        val user = args.user
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

        binding.btnAtualizar.setOnClickListener {
            when {
                hora.isEmpty() -> {
                    mensagem(it, "É necessário atualizar o horário!", "#FF0000")
                }

                hora < "8:00" && hora > "19:00" -> {
                    mensagem(it, "Academia fechada", "#FF0000")
                }

                data.isEmpty() -> {
                    mensagem(it, "É necessário atualizar a data", "#FF0000")
                }

                data.isNotEmpty() && hora.isNotEmpty() -> {
                    viewModel.updateUser(email.lowercase(Locale.ROOT), UserEntity(user, data, hora))
                    viewModel.users.observe(viewLifecycleOwner) { state ->
                        when (state) {
                            is UIState.Loading -> {

                            }

                            is UIState.Success -> {
                                mensagem(it, "Agendamento atualizado com sucesso!", "#FF03DAC5")
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
}