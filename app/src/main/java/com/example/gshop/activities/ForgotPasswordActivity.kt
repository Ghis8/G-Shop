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

class ForgotPasswordActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
        val send_email=findViewById<Button>(R.id.send_email)
        val email=findViewById<EditText>(R.id.reset_email_id)
        val goBack=findViewById<TextView>(R.id.go_back)

        goBack.setOnClickListener {
            finish()
        }
        send_email.setOnClickListener {
            sendEmail(email.text.toString())

        }
    }
    fun sendEmail(email:String){
        if (!email.trim { it <=' ' }.isNullOrEmpty()){
            showProgressDialog()
            FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    if(task.isSuccessful){
                        hideProgressDialog()
                        //Toast.makeText(this,"Reset Password Email sent Successfully",Toast.LENGTH_SHORT)
                        showErrorSnackBar("Reset Password Email sent successfully",false)
                        finish()
                    }else{
                        hideProgressDialog()
                        showErrorSnackBar(task.exception!!.message.toString(),true)
                        //Toast.makeText(this,task.exception!!.message.toString(),Toast.LENGTH_SHORT).show()
                    }
                }
        }else{
            showErrorSnackBar("Email Address required",true)
            //Toast.makeText(this,"Email address required",Toast.LENGTH_SHORT).show()
        }
    }
}