package com.example.privatevpn

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


lateinit var back : ImageView

class FeedBackActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed_back)

        back = findViewById(R.id.backs)

        back.setOnClickListener{
//            intent = Intent(this,sidenavigationActivity::class.java)
//            startActivity(intent)

            finish();
//            overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left)
        }

    }
}