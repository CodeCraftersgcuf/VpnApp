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
import com.google.android.material.tabs.TabLayout
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import java.util.Locale
import android.content.SharedPreferences
import com.example.yourapp.LocationActivity

class MainActivity : AppCompatActivity(), SelectLanguageDialogFragment.LanguageSelectionListener {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    // Add a tag for logging
    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Load the selected language
        loadSelectedLanguage(this)

        // Set up View Binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up the action bar with custom title
        supportActionBar?.title = "New Text"
        setSupportActionBar(binding.appBarMain.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)

        // Set up the navigation configuration with top-level destinations
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
                R.id.nav_upgrade_to_pro -> {
                    val intent = Intent(this, PremiumFeaturesActivity::class.java)
                    startActivity(intent)
                    drawerLayout.closeDrawers() // Close the drawer after launching the activity
                    Log.d(TAG, "Upgrade to PRO clicked")
                    true
                }
                R.id.nav_select_language -> {
                    // Show the Select Language dialog
                    val dialog = SelectLanguageDialogFragment()
                    if (!dialog.isAdded) {  // Prevent showing the dialog twice
                        dialog.show(supportFragmentManager, "SelectLanguageDialog")
                    }
                    drawerLayout.closeDrawers() // Close the drawer
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
        val countrySelector: LinearLayout? = findViewById(R.id.countrySelector)
        countrySelector?.setOnClickListener {
            // Navigate to CountryActivity
            val intent = Intent(this@MainActivity, Country::class.java)
            startActivity(intent)
            Log.d(TAG, "Country Selector clicked")
        }

        // Handling closeButton click inside the drawer
        val closeButton: ImageView? = navView.getHeaderView(0).findViewById(R.id.closeButton)
        closeButton?.setOnClickListener {
            drawerLayout.closeDrawers()  // Close the navigation drawer
            Log.d(TAG, "Close Button clicked")
        }

        // Set up ViewPager and TabLayout
        val viewPager: ViewPager2? = findViewById(R.id.viewpager)
        val tabLayout: TabLayout? = findViewById(R.id.tablayout)

        // Ensure views are not null before proceeding
        if (viewPager == null || tabLayout == null) {
            Log.e(TAG, "ViewPager or TabLayout is null, please check your layout file.")
            return
        }

        // Create an instance of VPAdapter and set it to the ViewPager
        val adapter = VPAdapter(this)
        viewPager.adapter = adapter

        // Attach the TabLayout with the ViewPager
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "All"
                1 -> "Streaming"
                2 -> "Gaming"
                else -> null
            }
        }.attach()
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    // This method is called when a language is selected in the dialog
    override fun onLanguageSelected(languageCode: String) {
        updateLanguage(this, languageCode)
        saveSelectedLanguage(this, languageCode)
        recreate()  // Recreate activity to apply language change
    }

    fun updateLanguage(context: Context, languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)

        val config = Configuration()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            config.setLocale(locale)
            context.createConfigurationContext(config)
        } else {
            config.locale = locale
        }

        context.resources.updateConfiguration(config, context.resources.displayMetrics)
    }

    fun saveSelectedLanguage(context: Context, languageCode: String) {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putString("selected_language", languageCode)
            apply()
        }
    }

    fun loadSelectedLanguage(context: Context) {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val languageCode = sharedPreferences.getString("selected_language", "en")
        updateLanguage(context, languageCode ?: "en")
    }
}
