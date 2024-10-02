package com.example.privatevpn

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout

lateinit var backs: ImageView

class AllServersActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_servers)

        // Set up the back button behavior
        backs = findViewById(R.id.backs)
        backs.setOnClickListener {
            finish()  // This will go back to the previous activity in the back stack
        }

        // Initialize TabLayout and ViewPager
        val tabLayout = findViewById<TabLayout>(R.id.my_tab_layout)
        val viewPager = findViewById<ViewPager>(R.id.my_view_pager)

        // Set up ViewPager adapter
        val myvpAdapter = MyvpAdapter(supportFragmentManager)
        myvpAdapter.addFragment(AllFragment(), "All")
        myvpAdapter.addFragment(StreamingFragment(), "Streaming")
        myvpAdapter.addFragment(GamingFragment(), "Gaming")
        viewPager.adapter = myvpAdapter

        // Link TabLayout with ViewPager
        tabLayout.setupWithViewPager(viewPager)

        // Set hardcoded text colors for unselected and selected tabs (both white)
        tabLayout.setTabTextColors(
            0xFFFFFFFF.toInt(),   // Unselected tab text color (white)
            0xFFFFBF00.toInt()    // Selected tab text color (yellow)
        )
    }
}
