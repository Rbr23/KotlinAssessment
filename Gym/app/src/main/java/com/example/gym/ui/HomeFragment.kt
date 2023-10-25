package com.example.gym.ui

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.example.gym.R
import com.example.gym.adapter.ServicosAdapter
import com.example.gym.databinding.ActivityHomeBinding
import com.example.gym.databinding.FragmentGymBinding
import com.example.gym.databinding.FragmentHomeBinding
import com.example.gym.model.Servicos
import com.example.gym.service.CardService
import com.example.gym.view.Agendamento
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val args: HomeFragmentArgs by navArgs()
    private lateinit var viewModel: HomeViewModel
    private lateinit var servicosAdapter: ServicosAdapter
    private val listaServicos: MutableList<Servicos> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHomeBinding.inflate(
            inflater,
            container,
            false
        )

        //var retorfrit: Retrofit = Retrofit.Builder().baseUrl("").addConverterFactory(GsonConverterFactory.create()).build();

        //val cardService = retorfrit.create(CardService::class.java)

        /*GlobalScope.launch {
            val card = cardService.getCards()
        }*/

        binding.txtNomeUsuario.text = "Bem-vindo, ${args.email} "
        val recyclerViewServicos = binding.recyclerViewServicos
        recyclerViewServicos.layoutManager = GridLayoutManager(requireActivity(), 2)
        servicosAdapter = ServicosAdapter(requireActivity(), listaServicos)
        recyclerViewServicos.adapter = servicosAdapter
        getServicos()

        binding.btAgendar.setOnClickListener {
            val direction = HomeFragmentDirections.actionHomeFragmentToAgendamentoFragment(args.email)
            findNavController().navigate(direction)
        }

        binding.btListar.setOnClickListener{
            val direction = HomeFragmentDirections.actionHomeFragmentToAgendamentoListFragment(args.email)
            findNavController().navigate(direction)
        }

        return binding.root
    }

    private fun getServicos() {
        val servico1 = Servicos(R.drawable.academia, "Aula de musculação")
        listaServicos.add(servico1)

        val servico2 = Servicos(R.drawable.academia, "Aula de bike")
        listaServicos.add(servico1)
    }


}