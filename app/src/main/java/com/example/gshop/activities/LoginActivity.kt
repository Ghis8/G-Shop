package com.example.gshop.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.gshop.R
import com.example.gshop.firestore.FirestoreClass
import com.example.gshop.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginActivity : BaseActivity() {
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
            showProgressDialog()
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password)
                .addOnCompleteListener { task->
                    if(task.isSuccessful){
                        hideProgressDialog()
                        FirestoreClass().getUserDetails(this)
                    }else{
                        hideProgressDialog()
                        showErrorSnackBar(task.exception!!.message.toString(),true)
                        //Toast.makeText(this,task.exception!!.message.toString(),Toast.LENGTH_SHORT).show()
                    }
                }

        }
    }
    private fun validateLoginDetails(email:String,password:String):Boolean{
        return when{
            email.trim { it <=' ' }.isNullOrEmpty()->{
                //Toast.makeText(this,"Email address required",Toast.LENGTH_SHORT).show()
                showErrorSnackBar("Email Address required",true)
                false
            }
            password.trim{it <=' '}.isNullOrEmpty()->{
                //Toast.makeText(this,"Password required",Toast.LENGTH_SHORT).show()
                showErrorSnackBar("Password required",true)
                false
            }else->{
                true
            }
        }
    }

    fun userLoggedInSuccess(user:User){
        Log.i("First Name",user.firstName)
        Log.i("Last Name",user.lastName)
        Log.i("Email address",user.email)
        //showErrorSnackBar("User LoggedIn Successfully",false)
        startActivity(Intent(this,MainActivity::class.java))
        finish()

    }





}