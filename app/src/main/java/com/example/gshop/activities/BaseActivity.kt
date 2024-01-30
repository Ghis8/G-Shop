package com.example.gshop.activities

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.example.gshop.R
import com.google.android.material.snackbar.Snackbar



open class BaseActivity : AppCompatActivity() {
    private lateinit var mProgressBar:Dialog
    fun showErrorSnackBar(message:String,errorMessage:Boolean){
        val snackBar=Snackbar.make(findViewById(android.R.id.content),message,Snackbar.LENGTH_LONG)
        val snackBarView=snackBar.view

        if (errorMessage){
            snackBarView.setBackgroundColor(ContextCompat.getColor(this,R.color.ColorSnackBarError))
        }else{
            snackBarView.setBackgroundColor(ContextCompat.getColor(this,R.color.ColorSnackBarSuccess))
        }
        snackBar.show()
    }

    fun showProgressDialog(){
        mProgressBar= Dialog(this)
        mProgressBar.setContentView(R.layout.dialog_progress)
        mProgressBar.setCancelable(false)
        mProgressBar.setCanceledOnTouchOutside(false)
        mProgressBar.show()
    }

    fun hideProgressDialog(){
        mProgressBar.dismiss()
    }
}