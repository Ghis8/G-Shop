package com.example.gshop.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.gshop.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val firstName=findViewById<EditText>(R.id.first_name)
        val lastName=findViewById<EditText>(R.id.last_name)
        val email=findViewById<EditText>(R.id.email_id)
        val password=findViewById<EditText>(R.id.password)
        val confirmPassword=findViewById<EditText>(R.id.confirm_password)
        val registerBtn=findViewById<Button>(R.id.btn_register)
        val loginButton=findViewById<TextView>(R.id.to_login)

        loginButton.setOnClickListener {
            val intent=Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }

        // register user
        registerBtn.setOnClickListener {
            if (inputValidation(
                    firstName.text.toString(),
                    lastName.text.toString(),
                    email.text.toString(),
                    password.text.toString(),
                    confirmPassword.text.toString()
            )){
                registerUser(email.text.toString(),password.text.toString())
            }else{
                Toast.makeText(this,"Something went wrong",Toast.LENGTH_SHORT).show()
            }
        }
    }


    // validate user inputs
    private fun inputValidation(
        firstName:String?,
        lastName:String?,
        email:String?,
        password:String?,
        confirmPassword:String?
    ):Boolean{
        return when{
            firstName?.trim { it <=' '}.isNullOrEmpty()->{
                Toast.makeText(this,"First Name required",Toast.LENGTH_SHORT).show()
                false
            }
            lastName?.trim { it <=' '}.isNullOrEmpty()->{
                Toast.makeText(this,"Last Name required",Toast.LENGTH_SHORT).show()
                false
            }
            email?.trim { it <=' '}.isNullOrEmpty()->{
                Toast.makeText(this,"Email Address required",Toast.LENGTH_SHORT).show()
                false
            }
            password?.trim { it <=' '}.isNullOrEmpty()->{
                Toast.makeText(this,"Password required",Toast.LENGTH_SHORT).show()
                false
            }
            confirmPassword?.trim { it <=' '}.isNullOrEmpty()->{
                Toast.makeText(this,"Confirm Password required",Toast.LENGTH_SHORT).show()
                false
            }
            password?.trim{it <=' '} != confirmPassword?.trim { it <= ' '}->{
                Toast.makeText(this,"Password and Confirm Password does not match",Toast.LENGTH_SHORT).show()
                false
            }else->{
                true
            }
        }
    }
    private fun registerUser(email:String,password:String){
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener(
                OnCompleteListener<AuthResult> { task ->
                    if (task.isSuccessful){
                        // register firebase user
                        val firebaseUser:FirebaseUser=task.result!!.user!!
                        Toast.makeText(this,"User registered successfully. your email ${firebaseUser.email}",Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(this,task.exception!!.message.toString(),Toast.LENGTH_SHORT).show()
                    }
                }
            )
    }


}