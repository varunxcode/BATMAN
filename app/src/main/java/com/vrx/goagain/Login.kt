package com.vrx.goagain

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {


    private lateinit var username: EditText
    private lateinit var password: EditText
    private lateinit var btlogin: Button
    private lateinit var btsignup: Button

    private lateinit var auth: FirebaseAuth



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        supportActionBar?.hide()


        username = findViewById(R.id.login_username)
        password = findViewById(R.id.login_password)
        btlogin = findViewById(R.id.login_login)
        btsignup = findViewById(R.id.login_signup)
        auth = FirebaseAuth.getInstance()


        btsignup.setOnClickListener{
            val intent = Intent(this,Signup::class.java)
            startActivity(intent)

        }

        btlogin.setOnClickListener{
            val uname = username.text.toString()
            val pwd = password.text.toString()

            login(uname,pwd);

        }




    }

    private fun login(uname:String,pwd:String){

        auth.signInWithEmailAndPassword(uname, pwd)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    if(auth.currentUser?.isEmailVerified ==true){
                        val intent = Intent(this@Login,MainActivity::class.java)
                        finish()
                        startActivity(intent)
                    }

                    else{
                        Toast.makeText(this,"Please verify your email to login",Toast.LENGTH_SHORT).show()
                    }

                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this@Login, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }


    }




}


