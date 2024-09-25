package com.example.privatevpn

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

lateinit var backhome: ImageView

class checkLocationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_location)

        backhome = findViewById(R.id.backhome)

        backhome.setOnClickListener{
            intent = Intent(this,sidenavigationActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left)
        }

    }

}