package com.vrx.goagain

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Signup : AppCompatActivity() {


    private lateinit var name: EditText

    private lateinit var username: EditText
    private lateinit var password: EditText
    private lateinit var btsignup: Button


    private lateinit var auth: FirebaseAuth
    private lateinit var dbref: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        supportActionBar?.hide()


        username=findViewById(R.id.login_username)
        name=findViewById(R.id.login_name)
        password=findViewById(R.id.login_password)
        btsignup=findViewById(R.id.login_signup)

        auth = FirebaseAuth.getInstance()

        btsignup.setOnClickListener {

            val name = name.text.toString()
            val uname = username.text.toString()
            val pwd = password.text.toString()

            signUp(name,uname,pwd)

        }



    }

    private fun signUp(name: String, uname:String,pwd:String){
        auth.createUserWithEmailAndPassword(uname, pwd)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {



                    auth.currentUser?.sendEmailVerification()
                        ?.addOnSuccessListener {
                            addusertothedb(name,uname, auth.currentUser?.uid!!)
                            Toast.makeText(this,"Please proceed through email verification.",Toast.LENGTH_SHORT).show()

                        }
                        ?.addOnFailureListener{
                            Toast.makeText(this,"Error 420",Toast.LENGTH_SHORT).show()
                        }

                    val intent = Intent(this@Signup,Login::class.java)
                    finish()
                    startActivity(intent)


                } else {
                    Toast.makeText(this@Signup, "Authentication failed \nMinimum characters in password - 6.",
                        Toast.LENGTH_SHORT).show()
                }
            }

    }

    private fun addusertothedb(name: String, uname: String, uid: String){

        dbref = FirebaseDatabase.getInstance().getReference()
        dbref.child("user").child(uid).setValue(User(name,uname,uid))




    }



}