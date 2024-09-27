package com.example.privatevpn

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

lateinit var back_home : ImageView

class DisconnectActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_disconnect)

        back_home = findViewById(R.id.back_home)

        back_home.setOnClickListener{
            intent = Intent(this,MainActivity::class.java)
            startActivity(intent)

//            overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left)
        }

    }
}