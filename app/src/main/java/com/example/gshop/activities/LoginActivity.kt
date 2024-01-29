package com.example.gshop.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.gshop.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginActivity : AppCompatActivity() {
    private lateinit var email:EditText
    private lateinit var password: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        email=findViewById(R.id.reset_email_id)
        password=findViewById(R.id.ed_password)
        val loginBtn=findViewById<Button>(R.id.send_email)
        val register=findViewById<TextView>(R.id.tv_register)
        val forgot_password=findViewById<TextView>(R.id.forgot_password)

        forgot_password.setOnClickListener {
            val intent=Intent(this,ForgotPasswordActivity::class.java)
            startActivity(intent)
        }
        loginBtn.setOnClickListener {
            loginUser(email.text.toString(),password.text.toString())
        }

        register.setOnClickListener {
            val intent=Intent(this,RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loginUser(email:String,password:String){
        if(validateLoginDetails(email,password)){
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password)
                .addOnCompleteListener { task->
                    if(task.isSuccessful){
                        val user:FirebaseUser=task.result!!.user!!
                        Toast.makeText(this,"User logged successfully",Toast.LENGTH_SHORT).show()
                        val intent=Intent(this,MainActivity::class.java)
                        startActivity(intent)
                    }else{
                        Toast.makeText(this,task.exception!!.message.toString(),Toast.LENGTH_SHORT).show()
                    }
                }

        }
    }
    private fun validateLoginDetails(email:String,password:String):Boolean{
        return when{
            email.trim { it <=' ' }.isNullOrEmpty()->{
                Toast.makeText(this,"Email address required",Toast.LENGTH_SHORT).show()
                false
            }
            password.trim{it <=' '}.isNullOrEmpty()->{
                Toast.makeText(this,"Password required",Toast.LENGTH_SHORT).show()
                false
            }else->{
                true
            }
        }
    }





}