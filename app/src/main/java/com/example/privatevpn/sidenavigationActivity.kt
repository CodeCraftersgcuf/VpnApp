package com.example.privatevpn

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

lateinit var closes: ImageView
lateinit var loactions: LinearLayout
lateinit var share: LinearLayout
lateinit var speedtext: LinearLayout
lateinit var linearLayout2: LinearLayout
lateinit var feedback: LinearLayout
lateinit var buttomSheets: LinearLayout

class sidenavigationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sidenavigation)

        closes = findViewById(R.id.closes)
        loactions = findViewById(R.id.loactions)
        share = findViewById(R.id.sher)
        linearLayout2 = findViewById(R.id.linearLayout2)
        feedback = findViewById(R.id.feedbacks)
        speedtext = findViewById(R.id.speedtext)
        buttomSheets = findViewById(R.id.buttomSheets)

        // BottomSheetFragment setup
        val bottomsheetFragment = BottomsheetFragment()
        buttomSheets.setOnClickListener {
            bottomsheetFragment.show(supportFragmentManager, "")
        }

        // Speed test activity
        speedtext.setOnClickListener {
            val intent = Intent(this, SpeedTestActivity::class.java)
            startActivity(intent)
        }

        // Share intent
        share.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "This is my text to send.")
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }

        // Close button: instead of creating a new MainActivity, just finish the current activity
        closes.setOnClickListener {
            finish()  // This will close the sidenavigationActivity and return to the previous activity in the back stack
        }

        // Location activity
        loactions.setOnClickListener {
            val intent = Intent(this, checkLocationActivity::class.java)
            startActivity(intent)
        }

        // Subscription activity
        linearLayout2.setOnClickListener {
            val intent = Intent(this, subciActivity::class.java)
            startActivity(intent)
        }

        // Feedback activity
        feedback.setOnClickListener {
            val intent = Intent(this, FeedBackActivity::class.java)
            startActivity(intent)
        }
    }
}
