package com.example.gshop.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.gshop.R

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val loginBtn=findViewById<Button>(R.id.btn_login)
        val email=findViewById<EditText>(R.id.ed_email)
        val password=findViewById<EditText>(R.id.ed_password)
        val register=findViewById<TextView>(R.id.tv_register)

        loginBtn.setOnClickListener {
            if(email.text.toString() == "" || password.text.toString()==""){
                Toast.makeText(this,"Email and Password Required",Toast.LENGTH_SHORT).show()
            }else if (email.text.toString()=="ghislain@gmail.com" && password.text.toString()=="password") {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }else{
                Toast.makeText(this,"Wrong Credentials",Toast.LENGTH_SHORT)
            }
        }

        register.setOnClickListener {
            val intent=Intent(this,RegisterActivity::class.java)
            startActivity(intent)
        }
    }



}