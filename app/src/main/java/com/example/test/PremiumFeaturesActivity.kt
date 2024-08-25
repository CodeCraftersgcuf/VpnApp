package com.example.test

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class PremiumFeaturesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_premium_features)

        // Handle close button
        val closeButton: ImageView = findViewById(R.id.closeButton)
        closeButton.setOnClickListener {
            finish() // Closes the current activity
        }
    }
}
