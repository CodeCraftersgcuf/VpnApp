package com.example.privatevpn

import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.net.VpnService
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.airbnb.lottie.LottieAnimationView // Import Lottie
import de.blinkt.openvpn.VpnProfile
import de.blinkt.openvpn.core.ConfigParser
import de.blinkt.openvpn.core.ProfileManager
import de.blinkt.openvpn.core.VPNLaunchHelper
import okhttp3.*
import java.io.IOException
import java.io.InputStreamReader
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var countrySelector: LinearLayout
    private lateinit var premium: ImageView
    private lateinit var toggle: ImageView
    private lateinit var button: LottieAnimationView // Change to LottieAnimationView

    private var isConnected = false
    private lateinit var vpnProfile: VpnProfile

    // To prevent multiple clicks during animation
    private var isAnimating = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        countrySelector = findViewById(R.id.countrySelector)
        premium = findViewById(R.id.premium)
        toggle = findViewById(R.id.toggle)
        button = findViewById(R.id.button)

        // Request permissions for notifications on newer Android versions
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.POST_NOTIFICATIONS), 102)
            }
        }

        // Set up listeners for UI components
        toggle.setOnClickListener {
            startActivity(Intent(this, sidenavigationActivity::class.java))
        }

        premium.setOnClickListener {
            startActivity(Intent(this, subciActivity::class.java))
        }

        countrySelector.setOnClickListener {
            startActivity(Intent(this, AllServersActivity::class.java))
        }

        button.setOnClickListener {
            if (isAnimating) return@setOnClickListener // Ignore clicks if already animating

            isAnimating = true
            button.loop(true) // Set the animation to loop
            button.playAnimation() // Start the animation

            // Immediately start the connection/disconnection logic
            if (isConnected) {
                disconnectVPN() // Call disconnectVPN() immediately

                // Use Handler to stop the animation after 1 second for disconnecting
                Handler(Looper.getMainLooper()).postDelayed({
                    // Stop the animation
                    button.cancelAnimation()
                    button.setProgress(0f) // Reset to the start if needed
                    isAnimating = false // Allow clicks again after the animation
                }, 1000) // 1000 milliseconds = 1 second
            } else {
                fetchOpenVPNProfile() // Call fetchOpenVPNProfile() immediately

                // Use Handler to stop the animation after 4 seconds for connecting
                Handler(Looper.getMainLooper()).postDelayed({
                    // Stop the animation
                    button.cancelAnimation()
                    button.setProgress(0f) // Reset to the start if needed
                    isAnimating = false // Allow clicks again after the animation
                }, 4000) // 4000 milliseconds = 4 seconds
            }
        }





        loadSelectedLanguage()
    }

    private fun fetchOpenVPNProfile() {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://govpn.ai/api/ovpn-file/1?vpn_username=12312")
            .get()
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(this@MainActivity, "Failed to fetch VPN profile", Toast.LENGTH_SHORT).show()
                    Log.e("VPNResponse", "Failed to fetch VPN profile", e)
                }
            }

            override fun onResponse(call: Call, response: Response) {
                response.use { res ->
                    val responseBody = res.body?.string()
                    if (res.isSuccessful && responseBody != null) {
                        Log.d("VPNResponse", "Success: $responseBody")
                        runOnUiThread {
                            parseAndStartVPN(responseBody)
                        }
                    } else {
                        Log.e("VPNResponse", "Failed: ${res.code}, ${res.message}")
                        runOnUiThread {
                            Toast.makeText(this@MainActivity, "Failed to fetch VPN profile: ${res.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        })
    }

    private fun parseAndStartVPN(ovpnConfig: String) {
        try {
            val cp = ConfigParser()
            cp.parseConfig(InputStreamReader(ovpnConfig.byteInputStream()))
            vpnProfile = cp.convertProfile()
            ProfileManager.getInstance(this).addProfile(vpnProfile)
            startVPN(vpnProfile)
        } catch (e: Exception) {
            Log.e("VPN", "Error parsing VPN profile", e)
            runOnUiThread {
                Toast.makeText(this, "Failed to import OpenVPN profile", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun startVPN(vpnProfile: VpnProfile) {
        val vpnIntent = VpnService.prepare(this)
        if (vpnIntent != null) {
            // Request VPN permission if necessary
            startActivityForResult(vpnIntent, 0)
        } else {
            // Start VPN without needing permission (already granted)
            try {
                ProfileManager.getInstance(this).addProfile(vpnProfile) // Add profile to ProfileManager

                // Start VPN with only the required parameters
                VPNLaunchHelper.startOpenVpn(vpnProfile, this)

                isConnected = true  // Update connection state
                // Do not change button appearance here to maintain Lottie
            } catch (e: Exception) {
                Log.e("VPN", "Failed to start VPN", e)
                Toast.makeText(this, "Failed to start VPN", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun disconnectVPN() {
        try {
            val disconnectIntent = Intent(this, de.blinkt.openvpn.core.OpenVPNService::class.java)
            disconnectIntent.action = de.blinkt.openvpn.core.OpenVPNService.DISCONNECT_VPN
            startService(disconnectIntent)
            isConnected = false
            // Do not change button appearance here to maintain Lottie
            Toast.makeText(this, "Disconnected successfully", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Log.e("VPN", "Failed to disconnect VPN", e)
            Toast.makeText(this, "Failed to disconnect VPN", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadSelectedLanguage() {
        val sharedPreferences = getSharedPreferences("app_language", MODE_PRIVATE)
        val selectedLanguage = sharedPreferences.getString("selected_language", "en")
        setLocale(selectedLanguage ?: "en")
    }

    private fun setLocale(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
    }

    override fun onBackPressed() {
        Toast.makeText(this, "Close", Toast.LENGTH_SHORT).show()
        super.onBackPressed()
    }
}
