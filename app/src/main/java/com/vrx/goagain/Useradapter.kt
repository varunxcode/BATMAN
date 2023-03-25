package com.vrx.goagain

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.firebase.auth.FirebaseAuth

class Useradapter(val context : Context, var userList: ArrayList<User>):
    RecyclerView.Adapter<Useradapter.UserViewHolder>() {

    fun setFilteredList(userList: ArrayList<User>){
        this.userList = userList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view : View = LayoutInflater.from(context).inflate(R.layout.user_layout,parent,false)
        return UserViewHolder(view)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
    val currentuser = userList[position]
        holder.textname.text = currentuser.name

        holder.itemView.setOnClickListener{
        val intent = Intent(context,ChatActivity::class.java)

        intent.putExtra("name",currentuser.name)
        intent.putExtra("uid",currentuser.uid)

        context.startActivity(intent )}

    }

    class UserViewHolder(itemView: View) : ViewHolder(itemView){

        val textname = itemView.findViewById<TextView>(R.id.txt_name)

    }



}