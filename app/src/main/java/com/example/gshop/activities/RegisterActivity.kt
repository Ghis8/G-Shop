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
import com.example.gshop.firestore.FirestoreClass
import com.example.gshop.models.User
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class RegisterActivity : BaseActivity() {
    private lateinit var firstName:EditText
    private lateinit var lastName: EditText
    private lateinit var ed_email:EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        firstName=findViewById(R.id.first_name)
        lastName=findViewById(R.id.last_name)
        ed_email=findViewById(R.id.email_id)
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
                    ed_email.text.toString(),
                    password.text.toString(),
                    confirmPassword.text.toString()
            )){
                showProgressDialog()
                registerUser(ed_email.text.toString(),password.text.toString())

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
                //Toast.makeText(this,"First Name required",Toast.LENGTH_SHORT).show()
                showErrorSnackBar("First name required",true)
                false
            }
            lastName?.trim { it <=' '}.isNullOrEmpty()->{
                //Toast.makeText(this,"Last Name required",Toast.LENGTH_SHORT).show()
                showErrorSnackBar("Last name required",true)
                false
            }
            email?.trim { it <=' '}.isNullOrEmpty()->{
                //Toast.makeText(this,"Email Address required",Toast.LENGTH_SHORT).show()
                showErrorSnackBar("Email Address required",true)
                false
            }
            password?.trim { it <=' '}.isNullOrEmpty()->{
                //Toast.makeText(this,"Password required",Toast.LENGTH_SHORT).show()
                showErrorSnackBar("Password required",true)
                false
            }
            confirmPassword?.trim { it <=' '}.isNullOrEmpty()->{
                //Toast.makeText(this,"Confirm Password required",Toast.LENGTH_SHORT).show()
                showErrorSnackBar("Confirm Password required",true)
                false
            }
            password?.trim{it <=' '} != confirmPassword?.trim { it <= ' '}->{
                //Toast.makeText(this,"Password and Confirm Password does not match",Toast.LENGTH_SHORT).show()
                showErrorSnackBar("Password and Confirm Password does not match!",true)
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
                        hideProgressDialog()
                        val firebaseUser:FirebaseUser=task.result!!.user!!
                        val user= User(
                            firebaseUser.uid,
                            firstName.text.toString().trim { it <=' ' },
                            lastName.text.toString().trim { it <=' ' },
                            ed_email.text.toString().trim { it <=' ' },
                        )
                        FirestoreClass().registerUser(this,user)
                        val intent=Intent(this,LoginActivity::class.java)
                        startActivity(intent)
                    }else{
                        hideProgressDialog()
                        showErrorSnackBar(task.exception!!.message.toString(),true)
                        //Toast.makeText(this,task.exception!!.message.toString(),Toast.LENGTH_SHORT).show()
                    }
                }
            )
    }

    fun userRegistrationSuccess(){
        showErrorSnackBar("User registered successfully",false)
        //Toast.makeText(this,"User registered successfully.",Toast.LENGTH_SHORT).show()
    }


}