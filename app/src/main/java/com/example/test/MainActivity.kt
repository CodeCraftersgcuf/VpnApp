package com.example.test

import RateUsDialogFragment
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.test.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import com.example.test.PremiumFeaturesActivity
import com.example.test.SpeedTestActivity
import com.example.test.Country
import com.example.yourapp.LocationActivity

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    // Add a tag for logging
    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.title = "New Text"

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        // Handling goProButton click
        val goProButton: ImageView? = findViewById(R.id.goPro)
        goProButton?.setOnClickListener {
            val intent = Intent(this, PremiumFeaturesActivity::class.java)
            startActivity(intent)
        }

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top-level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        // Handling clicks on navigation items
        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_speed_test -> {
                    val intent = Intent(this, SpeedTestActivity::class.java)
                    startActivity(intent)
                    drawerLayout.closeDrawers()
                    Log.d(TAG, "Speed Test clicked")
                    true
                }
                R.id.nav_current_location -> {
                    // Open the LocationActivity when "Current Location" is clicked
                    val intent = Intent(this, LocationActivity::class.java)
                    startActivity(intent)
                    drawerLayout.closeDrawers()
                    Log.d(TAG, "Current Location clicked")
                    true
                }
                R.id.nav_share_with_friends -> {
                    // Share functionality
                    val shareIntent = Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(Intent.EXTRA_SUBJECT, "Check out this app!")
                        putExtra(Intent.EXTRA_TEXT, "Check out this amazing app: www.google.com")
                    }
                    startActivity(Intent.createChooser(shareIntent, "Share via"))
                    drawerLayout.closeDrawers()
                    Log.d(TAG, "Share with friends clicked")
                    true
                }
                R.id.rate_us -> {
                    Log.d(TAG, "Rate Us clicked")
                    // Show the Rate Us dialog
                    val rateUsDialog = RateUsDialogFragment()
                    rateUsDialog.show(supportFragmentManager, "RateUsDialog")
                    drawerLayout.closeDrawers() // Close the drawer
                    true
                }
                else -> false
            }
        }

        // Adding the countrySelector click event
        val countrySelector: LinearLayout = findViewById(R.id.countrySelector)
        countrySelector.setOnClickListener {
            // Navigate to CountryActivity
            val intent = Intent(this@MainActivity, Country::class.java)
            startActivity(intent)
            Log.d(TAG, "Country Selector clicked")
        }

        // Handling closeButton click inside the drawer
        val closeButton: ImageView = navView.getHeaderView(0).findViewById(R.id.closeButton)
        closeButton.setOnClickListener {
            drawerLayout.closeDrawers()  // Close the navigation drawer
            Log.d(TAG, "Close Button clicked")
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
