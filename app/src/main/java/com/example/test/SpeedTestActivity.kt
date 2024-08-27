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
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
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
                val client = OkHttpClient.Builder()
                    .callTimeout(10, java.util.concurrent.TimeUnit.SECONDS) // Set timeout limit
                    .build()

                // Ping Test
                runOnUiThread { pingValue.text = "Measuring Ping..." }
                val pingTime = measurePing(client, "https://jsonplaceholder.typicode.com/posts/1")
                runOnUiThread { pingValue.text = String.format("Ping: %d ms", pingTime) }

                // Download Speed Test
                runOnUiThread { downloadSpeed.text = "Measuring Download Speed..." }
                val downloadSpeedMbps = measureDownloadSpeed(client)
                runOnUiThread {
                    downloadSpeed.text = String.format("Download: %.2f Mbps", downloadSpeedMbps)
                    animateSpeedText(downloadSpeedMbps)
                }

                // Upload Speed Test
                runOnUiThread { uploadSpeed.text = "Measuring Upload Speed..." }
                val uploadSpeedMbps = measureUploadSpeed(client)
                runOnUiThread {
                    uploadSpeed.text = String.format("Upload: %.2f Mbps", uploadSpeedMbps)
                    animateSpeedText(uploadSpeedMbps)
                }

            } catch (e: Exception) {
                Log.e("SpeedTestActivity", "Error during speed test", e)
                runOnUiThread {
                    Toast.makeText(this, "Failed to perform speed test. Please try again later.", Toast.LENGTH_SHORT).show()
                    downloadSpeed.text = "Download: N/A"
                    uploadSpeed.text = "Upload: N/A"
                    pingValue.text = "Ping: N/A"
                }
            }
        }
    }

    private fun measurePing(client: OkHttpClient, url: String): Long {
        return measureTimeMillis {
            try {
                val request = Request.Builder().url(url).build()
                client.newCall(request).execute().use { response ->
                    if (!response.isSuccessful) throw IOException("Ping failed: ${response.message}")
                }
            } catch (e: Exception) {
                Log.e("SpeedTestActivity", "Error during ping", e)
            }
        }
    }

    private fun measureDownloadSpeed(client: OkHttpClient): Double {
        val downloadUrl = "https://jsonplaceholder.typicode.com/posts/1" // Simple API endpoint for download test
        var totalDownloadTime: Long = 0

        repeat(10) { index ->
            var success = false
            repeat(3) { attempt ->
                try {
                    val downloadTime = measureTimeMillis {
                        client.newCall(Request.Builder().url(downloadUrl).build()).execute().use { response ->
                            if (!response.isSuccessful) throw IOException("Failed to download file: ${response.message}")
                            response.body?.string() // Fetch the content as string
                        }
                    }
                    totalDownloadTime += downloadTime
                    success = true
                    Log.i("SpeedTestActivity", "Download $index completed in $downloadTime ms")
                    return@repeat
                } catch (e: IOException) {
                    Log.e("SpeedTestActivity", "Retrying download $index... (${attempt + 1}/3)", e)
                }
            }
            if (!success) {
                Log.e("SpeedTestActivity", "Failed to download $index after 3 attempts")
            }
        }

        // Assume each response is around 1KB (adjust as necessary based on actual response size)
        val totalDataSizeInKB = 10 * 1.0 // 10 requests, each 1KB
        val downloadSpeedKbps = totalDataSizeInKB / (totalDownloadTime / 1000.0) // Speed in Kbps
        return downloadSpeedKbps / 1024 // Convert to Mbps
    }

    private fun measureUploadSpeed(client: OkHttpClient): Double {
        val uploadUrl = "https://jsonplaceholder.typicode.com/posts"
        var totalUploadTime: Long = 0

        repeat(10) { index ->
            val uploadData = """{"title": "foo", "body": "bar", "userId": $index}"""
            val requestBody = RequestBody.create("application/json".toMediaTypeOrNull(), uploadData)

            var success = false
            repeat(3) { attempt ->
                try {
                    val uploadTime = measureTimeMillis {
                        client.newCall(Request.Builder().url(uploadUrl).post(requestBody).build()).execute().use { response ->
                            if (!response.isSuccessful) throw IOException("Failed to upload file: ${response.message}")
                        }
                    }
                    totalUploadTime += uploadTime
                    success = true
                    Log.i("SpeedTestActivity", "Upload $index completed in $uploadTime ms")
                    return@repeat
                } catch (e: IOException) {
                    Log.e("SpeedTestActivity", "Retrying upload $index... (${attempt + 1}/3)", e)
                }
            }
            if (!success) {
                Log.e("SpeedTestActivity", "Failed to upload $index after 3 attempts")
            }
        }

        // Assume each upload is around 0.5KB (adjust as necessary based on actual request size)
        val totalDataSizeInKB = 10 * 0.5 // 10 requests, each 0.5KB
        val uploadSpeedKbps = totalDataSizeInKB / (totalUploadTime / 1000.0) // Speed in Kbps
        return uploadSpeedKbps / 1024 // Convert to Mbps
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
