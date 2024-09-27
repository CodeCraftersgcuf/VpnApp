package com.example.privatevpn

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale

// Declare UI elements
lateinit var countrySelector: LinearLayout
lateinit var premium: ImageView
lateinit var toggle: ImageView
lateinit var button: ImageView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Initialize UI elements
        countrySelector = findViewById(R.id.countrySelector)
        premium = findViewById(R.id.priemum)
        toggle = findViewById(R.id.toggle)
        button = findViewById(R.id.button)

        // Set up click listeners
        toggle.setOnClickListener {
            val intent = Intent(this, sidenavigationActivity::class.java)
            startActivity(intent)
        }

        premium.setOnClickListener {
            val intent = Intent(this, subciActivity::class.java)
            startActivity(intent)
        }

        countrySelector.setOnClickListener {
            val intent = Intent(this, AllServersActivity::class.java)
            startActivity(intent)
        }

        button.setOnClickListener {
            val intent = Intent(this, ConnectActivity::class.java)
            startActivity(intent)
        }

        // Load the selected language on app start
        loadSelectedLanguage()
    }

    private fun loadSelectedLanguage() {
        val sharedPreferences = getSharedPreferences("app_language", MODE_PRIVATE)
        val selectedLanguage = sharedPreferences.getString("selected_language", "en") // Default to English
        setLocale(selectedLanguage ?: "en")
    }

    private fun setLocale(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
    }

    override fun onBackPressed() {
        Toast.makeText(this, "Close", Toast.LENGTH_SHORT).show()
        super.onBackPressed()
    }
}
