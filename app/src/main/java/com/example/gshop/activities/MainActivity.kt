 package com.example.gshop.activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.gshop.R
import com.example.gshop.utils.Constants

 class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val t=findViewById<TextView>(R.id.tv_main)
        val sharedPreferences=getSharedPreferences(Constants.GSHOP_PREFERENCES,Context.MODE_PRIVATE)
        val username=sharedPreferences.getString(Constants.LOGGED_IN_USERNAME,"")!!
        t.text="Hello $username."
    }
}