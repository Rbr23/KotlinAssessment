package com.example.gym.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gym.R
import com.example.gym.adapter.AgendamentoListAdapter
import com.example.gym.model.UserEntity
import com.google.firebase.firestore.FirebaseFirestore

class AgendamentoList : Fragment(R.layout.activity_agendamento_list) {

   /* private lateinit var adapter: AgendamentoListAdapter
    private lateinit var view: AgendamentoList
    private lateinit var recyclerView: RecyclerView
    private lateinit var users: UserEntity

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val db = FirebaseFirestore.getInstance()
        db.collection("agendamento").document("Roberto@gmail.com")
            .addSnapshotListener { documento, error ->
                if (documento != null) {
                    users.cliente = documento.get("cliente").toString()
                    users.hora = documento.get("hora").toString()
                }
            }
        val users =
            listOf(
                UserEntity(this.users.cliente, this.users.hora),
            )


        val layoutManager = LinearLayoutManager(context)
        recyclerView = view.findViewById(R.id.recycler_user)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        adapter = AgendamentoListAdapter(users)
        recyclerView.adapter = adapter*/


    //}

}
