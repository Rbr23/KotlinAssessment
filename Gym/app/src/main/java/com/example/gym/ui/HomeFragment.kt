package com.example.gym.ui

import android.content.Intent
import android.nfc.Tag
import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresExtension
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.example.gym.R
import com.example.gym.adapter.ServicosAdapter
import com.example.gym.databinding.FragmentHomeBinding
import com.example.gym.model.Card
import com.example.gym.model.Servicos
import com.example.gym.service.CardService
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
    private val viewModel: HomeViewModel by viewModels()
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

        viewModel.cards.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                listaServicos.add(Servicos(R.drawable.academia, it[0].cards?.get(0)?.name))
                listaServicos.add(Servicos(R.drawable.academia, it[0].cards?.get(0)?.name))
                binding.txtNomeUsuario.text = "Bem-vindo, ${args.email} "
                val recyclerViewServicos = binding.recyclerViewServicos
                recyclerViewServicos.layoutManager = GridLayoutManager(requireActivity(), 2)
                servicosAdapter = ServicosAdapter(requireActivity(), listaServicos)
                recyclerViewServicos.adapter = servicosAdapter
            }
        }

        binding.btAgendar.setOnClickListener {
            val direction =
                HomeFragmentDirections.actionHomeFragmentToAgendamentoFragment(args.email)
            findNavController().navigate(direction)
        }

        binding.btListar.setOnClickListener {
            val direction =
                HomeFragmentDirections.actionHomeFragmentToAgendamentoListFragment(args.email)
            findNavController().navigate(direction)
        }


        return binding.root
    }
}


