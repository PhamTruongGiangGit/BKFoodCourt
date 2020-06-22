package com.example.bkfoodcourt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.core.os.HandlerCompat.postDelayed

class test : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        Handler().postDelayed(Runnable{
            val loginIntent = Intent(this, MainActivity::class.java)
            startActivity(loginIntent)
        }, 2000)
    }
}