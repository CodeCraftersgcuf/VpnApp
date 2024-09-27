package com.example.privatevpn

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


lateinit var closes : ImageView
lateinit var loaction: TextView
lateinit var share : LinearLayout
lateinit var speedtext : LinearLayout
lateinit var loactions : LinearLayout
lateinit var linearLayout2 : LinearLayout
lateinit var feedback: LinearLayout
lateinit var buttomSheets :LinearLayout

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

        
        val bottomsheetFragment = BottomsheetFragment()

        buttomSheets.setOnClickListener{
            bottomsheetFragment.show(supportFragmentManager,"")
        }


        speedtext.setOnClickListener{
            intent = Intent(this,SpeedTestActivity::class.java)
            startActivity(intent)
//            overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left)
        }

        share.setOnClickListener{
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "This is my text to send.")
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }

        closes.setOnClickListener{
            intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
//            overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left)

        }

//        location acvtivity
        loactions.setOnClickListener{
            intent = Intent(this,checkLocationActivity::class.java)
            startActivity(intent)
//            overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right)
        }
//        speed test activity
        linearLayout2.setOnClickListener{
            intent = Intent(this,subciActivity::class.java)
            startActivity(intent)
//            overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right)

        }

        feedback.setOnClickListener{
            intent = Intent(this,FeedBackActivity::class.java)
            startActivity(intent)
//            overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left)
        }
    }
}