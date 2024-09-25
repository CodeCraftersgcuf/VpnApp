package com.example.privatevpn

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

lateinit var countrySelector: LinearLayout
lateinit var priemum  : ImageView
lateinit var toggle : ImageView
lateinit var button : ImageView

//lateinit var toggle : ActionBarDrawerToggle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)


        countrySelector = findViewById(R.id.countrySelector)
        priemum = findViewById(R.id.priemum)
        toggle = findViewById(R.id.toggle)
        button = findViewById(R.id.button)


        toggle.setOnClickListener{
            intent = Intent(this,sidenavigationActivity::class.java)
            startActivity(intent)

            overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right)
        }

        priemum.setOnClickListener{
            intent = Intent(this,subciActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_out_left,R.anim.slide_in_right)

        }

        countrySelector.setOnClickListener{
            intent = Intent(this,AllServersActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_out_left,R.anim.slide_in_right)
        }

        button.setOnClickListener{
            intent = Intent(this,ConnectActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left)
        }

    }

    override fun onBackPressed() {
        Toast.makeText(this, "close", Toast.LENGTH_SHORT).show()
        super.onBackPressed()
    }
}
