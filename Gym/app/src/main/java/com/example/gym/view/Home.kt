package com.example.gym.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.gym.R
import com.example.gym.adapter.ServicosAdapter
import com.example.gym.databinding.ActivityHomeBinding
import com.example.gym.model.Servicos

class Home : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var servicosAdapter: ServicosAdapter
    private val listaServicos: MutableList<Servicos> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        val nome = intent.extras?.getString("nome")

        binding.txtNomeUsuario.text = "Bem-vindo, $nome"
        val recyclerViewServicos = binding.recyclerViewServicos
        recyclerViewServicos.layoutManager = GridLayoutManager(this, 2)
        servicosAdapter = ServicosAdapter(this, listaServicos)
        recyclerViewServicos.adapter = servicosAdapter
        getServicos()

        binding.btAgendar.setOnClickListener {
            val intent = Intent(this, Agendamento::class.java)
            intent.putExtra("nome", nome)
            startActivity(intent)
        }
    }

    private fun getServicos() {
        val servico1 = Servicos(R.drawable.academia, "Aula de musculação")
        listaServicos.add(servico1)

        val servico2 = Servicos(R.drawable.academia, "Aula de bike")
        listaServicos.add(servico1)

        val servico3 = Servicos(R.drawable.academia, "Aula de abdominal")
        listaServicos.add(servico1)
    }
}