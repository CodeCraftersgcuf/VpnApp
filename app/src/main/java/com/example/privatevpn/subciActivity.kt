package com.example.privatevpn

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

lateinit var cros: ImageView

class subciActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subci)

        cros = findViewById(R.id.cros)

        // Close the current activity and return to the previous one in the back stack
        cros.setOnClickListener {
            finish()  // This will close the activity without refreshing MainActivity
        }
    }
}
