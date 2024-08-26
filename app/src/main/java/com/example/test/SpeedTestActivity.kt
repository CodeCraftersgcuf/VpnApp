package com.example.test

import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Log
import android.view.animation.DecelerateInterpolator
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import kotlin.concurrent.thread
import kotlin.system.measureTimeMillis

class SpeedTestActivity : AppCompatActivity() {

    private lateinit var downloadSpeed: TextView
    private lateinit var uploadSpeed: TextView
    private lateinit var ipAddress: TextView
    private lateinit var pingValue: TextView
    private lateinit var startSpeedTestButton: Button
    private lateinit var speedText: TextView
    private lateinit var backButton: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_speed_test)

        // Initialize views
        downloadSpeed = findViewById(R.id.downloadSpeed)
        uploadSpeed = findViewById(R.id.uploadSpeed)
        ipAddress = findViewById(R.id.ipAddress)
        pingValue = findViewById(R.id.pingValue)
        startSpeedTestButton = findViewById(R.id.startSpeedTestButton)
        speedText = findViewById(R.id.speedText)
        backButton = findViewById(R.id.backButton)

        // Start speed test on button click
        startSpeedTestButton.setOnClickListener {
            if (isInternetAvailable()) {
                startSpeedTest()
            } else {
                Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show()
            }
        }

        // Back button to finish the activity
        backButton.setOnClickListener {
            finish()
        }

        // Fetch IP address
        fetchIPAddress()
    }

    private fun startSpeedTest() {
        thread {
            try {
                // Measure the time taken to download a small data from an API
                val url = "https://jsonplaceholder.typicode.com/posts" // Simple API endpoint
                val client = OkHttpClient()
                val request = Request.Builder().url(url).build()

                val downloadTime = measureTimeMillis {
                    client.newCall(request).execute().use { response ->
                        if (!response.isSuccessful) throw IOException("Failed to download file: ${response.message}")
                        response.body?.string() // Fetch the content
                    }
                }

                // Data size in kilobytes (adjust this value accordingly)
                val dataSizeInKB = 50.0 // Assume 50KB for the JSON response
                val downloadSpeedKbps = dataSizeInKB / (downloadTime / 1000.0) // Speed in Kbps
                val downloadSpeedMbps = downloadSpeedKbps / 1024 // Convert to Mbps

                // Update UI with the measured download speed
                runOnUiThread {
                    downloadSpeed.text = String.format("Download %.2f Mbps", downloadSpeedMbps)
                    animateSpeedText(downloadSpeedMbps)
                }

            } catch (e: Exception) {
                Log.e("SpeedTestActivity", "Error during speed test", e)
                runOnUiThread {
                    Toast.makeText(this, "Failed to perform speed test. Please try again later.", Toast.LENGTH_SHORT).show()
                    downloadSpeed.text = "Download: N/A"
                }
            }
        }
    }

    private fun animateSpeedText(targetSpeed: Double) {
        val animator = ObjectAnimator.ofFloat(0f, targetSpeed.toFloat())
        animator.duration = 1000 // Animation duration of 1 second
        animator.addUpdateListener { animation ->
            val animatedValue = animation.animatedValue as Float
            speedText.text = String.format("%.2f", animatedValue)
        }
        animator.interpolator = DecelerateInterpolator() // Smooth deceleration animation
        animator.start()
    }

    private fun fetchIPAddress() {
        thread {
            val client = OkHttpClient()
            val request = Request.Builder()
                .url("https://api.ipify.org?format=text")
                .build()

            try {
                client.newCall(request).execute().use { response ->
                    if (!response.isSuccessful) throw IOException("Unexpected code $response")
                    val ip = response.body?.string()
                    runOnUiThread {
                        ipAddress.text = "IP Address: $ip"
                    }
                }
            } catch (e: Exception) {
                Log.e("SpeedTestActivity", "Failed to fetch IP address", e)
                runOnUiThread {
                    Toast.makeText(this, "Failed to fetch IP address", Toast.LENGTH_SHORT).show()
                    ipAddress.text = "IP Address: N/A"
                }
            }
        }
    }

    private fun isInternetAvailable(): Boolean {
        // Your implementation of internet availability check here
        return true
    }
}
