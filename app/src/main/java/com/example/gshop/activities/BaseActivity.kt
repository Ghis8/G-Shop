package com.example.gshop.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.example.gshop.R
import com.google.android.material.snackbar.Snackbar

open class BaseActivity : AppCompatActivity() {

    fun showErrorSnackBar(message:String,errorMessage:Boolean){
        val snackBar=Snackbar.make(findViewById(R.id.content),message,Snackbar.LENGTH_LONG)
        val snackBarView=snackBar.view

        if (errorMessage){
            snackBarView.setBackgroundColor(ContextCompat.getColor(this,R.color.ColorSnackBarError))
        }else{
            snackBarView.setBackgroundColor(ContextCompat.getColor(this,R.color.ColorSnackBarSuccess))
        }
        snackBar.show()
    }
}