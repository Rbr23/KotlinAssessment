package com.example.gym.ui

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gym.R
import com.example.gym.adapter.AgendamentoListAdapter
import com.example.gym.model.UserEntity
import com.example.gym.util.UIState
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.Locale


class AgendamentoListFragment : Fragment(R.layout.fragment_agendamento_list) {

    private lateinit var adapter: AgendamentoListAdapter
    private val viewModel: AgendamentoListViewModel by viewModels()
    private val args: AgendamentoListFragmentArgs by navArgs()
    private lateinit var recyclerView: RecyclerView
    val db = Firebase.firestore
    var listaServicos: MutableList<UserEntity> = arrayListOf()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val direction =
                    AgendamentoListFragmentDirections.actionAgendamentoListFragmentToHomeFragment(
                        args.email
                    )
                findNavController().navigate(direction)
            }

        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)


        super.onViewCreated(view, savedInstanceState)
        viewModel.getUsers(args.email.lowercase(Locale.ROOT))
        viewModel.users.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UIState.Loading -> {

                }

                is UIState.Success -> {
                    val layoutManager = LinearLayoutManager(context)
                    recyclerView = view.findViewById(R.id.recycler_user)
                    recyclerView.layoutManager = layoutManager
                    recyclerView.setHasFixedSize(true)
                    adapter = AgendamentoListAdapter(requireActivity(), state.data)
                    recyclerView.adapter = adapter

                    adapter.quandoClicaNoItemListener = object : AgendamentoListAdapter.Action {
                        override fun action(user: UserEntity, number: Int) {
                            if (number == 1) {
                                val direction =
                                    AgendamentoListFragmentDirections.actionAgendamentoListFragmentToAgendamentoAtualizacaoFragment(
                                        args.email, user.cliente.toString()
                                    )
                                findNavController().navigate(direction)
                            } else if (number == 2) {
                                viewModel.deleteUser(
                                    args.email.lowercase(Locale.ROOT), user
                                )
                                viewModel.users.observe(viewLifecycleOwner) { state ->
                                    when (state) {
                                        is UIState.Loading -> {

                                        }

                                        is UIState.Success -> {
                                            Log.d("", "deletou")
                                        }

                                        is UIState.Failure -> {
                                        }
                                    }
                                }

                            }
                        }
                    }
                }

                is UIState.Failure -> {

                }
            }
        }

    }

}