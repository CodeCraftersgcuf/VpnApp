package com.example.privatevpn

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

lateinit var cros : ImageView

class subciActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subci)

        cros = findViewById(R.id.cros)

        cros.setOnClickListener{
            intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
//            overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left)
        }

    }
}