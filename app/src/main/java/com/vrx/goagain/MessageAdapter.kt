package com.vrx.goagain

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class MessageAdapter(val context : Context, val messageList: ArrayList<Message>):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val ITEM_RECEIVED = 1;
    val ITEM_SENT = 2;

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if(viewType==1){
            val view : View = LayoutInflater.from(context).inflate(R.layout.received,parent,false)
            return receivedViewHolder(view)
        }else{

            val view : View = LayoutInflater.from(context).inflate(R.layout.sent,parent,false)
            return sentViewHolder(view)
        }

    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val currentMessage = messageList[position]

        if(holder.javaClass == sentViewHolder::class.java){
            val viewHolder = holder as sentViewHolder

            holder.sentMessage.text = currentMessage.message

        }
        else{
            val viewHolder = holder as receivedViewHolder
            holder.recievedMessage.text = currentMessage.message
        }


    }

    override fun getItemViewType(position: Int):Int {


        val currentMessage = messageList[position]

        if(FirebaseAuth.getInstance().currentUser?.uid.equals(currentMessage.senderId)){
            return ITEM_SENT
        }
        else{
            return ITEM_RECEIVED
        }

    }

    class sentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val sentMessage = itemView.findViewById<TextView>(R.id.text_sent)


    }
    class receivedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val recievedMessage = itemView.findViewById<TextView>(R.id.text_recieved)



    }

}