package com.example.gym.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.gym.R
import com.example.gym.model.UserEntity


class AgendamentoListAdapter(
    val c: Context,
    val users: List<UserEntity>,
    var quandoClicaNoItemListener: Action =
        object : Action {
            override fun action(user: UserEntity, number: Int) {

            }
        }
) :
    RecyclerView.Adapter<AgendamentoListAdapter.AgendamentoListViewHolder>() {

    interface Action {
        fun action(user: UserEntity, number: Int)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AgendamentoListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false)

        return AgendamentoListViewHolder(view, c, users, quandoClicaNoItemListener)
    }

    override fun getItemCount() = users.size

    override fun onBindViewHolder(holder: AgendamentoListViewHolder, position: Int) {
        holder.bindView(users[position])
    }

    @SuppressLint("CutPasteId")
    class AgendamentoListViewHolder(
        itemView: View,
        c: Context,
        users: List<UserEntity>,
        quandoClicaNoItemListener: Action
    ) :
        RecyclerView.ViewHolder(itemView) {
        private val textViewUserEmail: TextView
        private val textViewUserHora: TextView
        private var mMenus: TextView

        init {
            textViewUserEmail = itemView.findViewById(R.id.text_user)
            textViewUserHora = itemView.findViewById(R.id.text_hora)
            mMenus = itemView.findViewById(R.id.text_hora)
            mMenus.setOnClickListener {
                popupMenu(c, it, users, quandoClicaNoItemListener)
            }
        }

        @SuppressLint("ResourceType")
        private fun popupMenu(
            c: Context,
            view: View,
            users: List<UserEntity>,
            quandoClicaNoItemListener: Action
        ) {
            val popupMenu = PopupMenu(c, view)
            popupMenu.inflate(R.drawable.show_menu)
            popupMenu.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.editText -> {
                        Toast.makeText(c, "Botão de edição foi clicado", Toast.LENGTH_SHORT).show()
                        quandoClicaNoItemListener.action(users[adapterPosition], 1)
                        true
                    }

                    R.id.delete -> {
                        Toast.makeText(c, "Botão de deleção foi clicado", Toast.LENGTH_SHORT).show()
                        quandoClicaNoItemListener.action(users[adapterPosition], 2)
                        true
                    }

                    else -> true
                }
            }
            popupMenu.show()
            val popup = PopupMenu::class.java.getDeclaredField("mPopup")
            popup.isAccessible = true
            val menu = popup.get(popupMenu)
            menu.javaClass.getDeclaredMethod("setForceShowIcon", Boolean::class.java)
                .invoke(menu, true)
        }

        fun bindView(user: UserEntity) {
            textViewUserEmail.text = user.cliente
            textViewUserHora.text = user.hora

        }
    }
}