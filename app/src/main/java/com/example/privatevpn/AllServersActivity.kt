package com.example.privatevpn

import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

lateinit var backs : ImageView

class AllServersActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_servers)

        backs = findViewById(R.id.backs)
        backs.setOnClickListener{
            intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left)
        }



        var tabLayout = findViewById(R.id.my_tab_layout) as TabLayout

        var viewPager : ViewPager = findViewById(R.id.my_view_pager) as ViewPager

        var myvpAdapter = MyvpAdapter(supportFragmentManager)
        myvpAdapter.addFragmnet(AllFragment(),"All")
        myvpAdapter.addFragmnet(StreamingFragment(),"Streaming")
        myvpAdapter.addFragmnet(GamingFragment(),"Gaming")

        viewPager.adapter = myvpAdapter
        tabLayout.setupWithViewPager(viewPager)

    }
}