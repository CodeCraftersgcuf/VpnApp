package com.example.privatevpn

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import de.hdodenhof.circleimageview.CircleImageView

lateinit var profile_image : CircleImageView
lateinit var priemums : ImageView
lateinit var toggles : ImageView
lateinit var disconnect : Button

class ConnectActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_connect)


        profile_image = findViewById(R.id.profile_image)
        priemums = findViewById(R.id.priemums)
        toggles = findViewById(R.id.toggles)
        disconnect = findViewById(R.id.disconnect)


        profile_image.setOnClickListener{
            intent = Intent(this,AllServersActivity::class.java)
            startActivity(intent)
//            overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right)
        }

        priemums.setOnClickListener{
            intent = Intent(this,subciActivity::class.java)
            startActivity(intent)
//            overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right)
        }

        toggles.setOnClickListener{
            intent = Intent(this,sidenavigationActivity::class.java)
            startActivity(intent)
//            overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right)
        }

        disconnect.setOnClickListener{
            intent = Intent(this,DisconnectActivity::class.java)
            startActivity(intent)
//            overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left)
        }

    }
}