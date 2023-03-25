package com.vrx.goagain

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
//import android.widget.SearchView
import androidx.appcompat.widget.SearchView;
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {

    private lateinit var displayusers : RecyclerView
    private lateinit var userList : ArrayList<User>
    private lateinit var adapter : Useradapter
    private lateinit var auth: FirebaseAuth
    private lateinit var dbref : DatabaseReference
    private lateinit var srchView : SearchView
    private lateinit var mtList : ArrayList<User>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.title = "BATMAN"
        srchView = findViewById(R.id.search_here)

        mtList = ArrayList()

        srchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }

        })

        auth = FirebaseAuth.getInstance()
        dbref = FirebaseDatabase.getInstance().getReference()

        userList = ArrayList()
        adapter = Useradapter(this,userList)

        displayusers = findViewById(R.id.userrecyclerview)
        displayusers.layoutManager = LinearLayoutManager(this)
        displayusers.adapter = adapter

        dbref.child("user").addValueEventListener(object : ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {

                userList.clear()
                for(postSnapshot in snapshot.children){
                    val currentUser = postSnapshot.getValue(User::class.java)
                    if(auth.currentUser?.uid != currentUser?.uid){
                            userList.add(currentUser!!)
                    }
                }
                adapter.notifyDataSetChanged()

            }

            override fun onCancelled(error: DatabaseError) {
            }


        })


    }

    private fun filterList(newText: String?) {

        if(newText!=null){
            val filteredList  = ArrayList<User>()
            for(i in userList){
                if(i.name?.lowercase()?.contains(newText.lowercase()) == true){
                    filteredList.add(i)
                }
            }

            if(filteredList.isNotEmpty()){
                adapter.setFilteredList(filteredList)
            }
            else{
                adapter.setFilteredList(mtList)
            }


        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.log_out){

            auth.signOut()
            val intent = Intent(this@MainActivity,Login::class.java)
            finish()
            startActivity(intent)
            return true
        }

        return true
    }



}


