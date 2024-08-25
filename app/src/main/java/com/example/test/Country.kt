package com.example.test

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Country : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set the content view first
        setContentView(R.layout.activity_country)

        // Find the ImageView by its ID after setting the content view
        val arrowImageView: ImageView = findViewById(R.id.imageView3)

        // Set a click listener on the ImageView
        arrowImageView.setOnClickListener {
            finish() // Go back to the previous activity
        }

        enableEdgeToEdge()

        // Handle window insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
