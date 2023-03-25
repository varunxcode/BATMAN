package com.vrx.goagain

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ChatActivity : AppCompatActivity() {

    private lateinit var chatview: RecyclerView
    private lateinit var messagetext : EditText
    private lateinit var sendbutton : ImageView
    private lateinit var messageAdapter: MessageAdapter
    private lateinit var messageList : ArrayList<Message>
    private lateinit var dbref: DatabaseReference


    var recieverRoom  : String? = null
    var senderRoom  : String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val name = intent.getStringExtra("name")
        val recieveruid = intent.getStringExtra("uid")

        val senderUid = FirebaseAuth.getInstance().currentUser?.uid

        senderRoom = recieveruid + senderUid
        recieverRoom = senderUid + recieveruid
        dbref = FirebaseDatabase.getInstance().getReference()

        supportActionBar?.title = name

        chatview = findViewById(R.id.chatview)
        messagetext = findViewById(R.id. message_text)
        sendbutton = findViewById(R.id.send_button)

        messageList = ArrayList()
        messageAdapter = MessageAdapter(this,messageList)

        chatview.layoutManager = LinearLayoutManager(this)
        chatview.adapter = messageAdapter

        dbref.child("chats").child(senderRoom!!).child("messages")
            .addValueEventListener(object: ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {

                     messageList.clear()
                    for(postSnapshot in snapshot.children){
                        val message = postSnapshot.getValue(Message::class.java)
                        messageList.add(message!!)

                    }
                    messageAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })

        sendbutton.setOnClickListener{

            val message = messagetext.text.toString()
            val messageObject = Message(message,senderUid)

            dbref.child("chats").child(senderRoom!!).child("messages").push()
                .setValue(messageObject).addOnSuccessListener {
                    dbref.child("chats").child(recieverRoom!!).child("messages").push()
                        .setValue(messageObject)
                }
            messagetext.setText("")



        }



    }
}