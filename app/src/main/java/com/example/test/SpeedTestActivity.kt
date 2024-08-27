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
import kotlinx.coroutines.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import java.io.IOException
import kotlin.system.measureTimeMillis

class SpeedTestActivity : AppCompatActivity() {

    private lateinit var downloadSpeed: TextView
    private lateinit var uploadSpeed: TextView
    private lateinit var ipAddress: TextView
    private lateinit var pingValue: TextView
    private lateinit var startSpeedTestButton: Button
    private lateinit var speedText: TextView
    private lateinit var downloadRate: TextView
    private lateinit var uploadRate: TextView
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
        downloadRate = findViewById(R.id.downloadRate)
        uploadRate = findViewById(R.id.uploadRate)
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
        CoroutineScope(Dispatchers.Main).launch {
            val client = OkHttpClient.Builder()
                .callTimeout(10, java.util.concurrent.TimeUnit.SECONDS) // Set timeout limit
                .build()

            // Ping Test
            pingValue.text = "Measuring Ping..."
            val pingTime = measurePing(client, "https://jsonplaceholder.typicode.com/posts/1")
            pingValue.text = String.format("Ping: %d ms", pingTime)

            // Download Speed Test
            downloadSpeed.text = "Measuring Download Speed..."
            val downloadSpeeds = mutableListOf<Double>()
            withContext(Dispatchers.IO) {
                measureDownloadSpeed(client, downloadSpeeds)
            }

            // Calculate and display average download speed
            val avgDownloadSpeed = downloadSpeeds.average()
            downloadSpeed.text = String.format("Download: %.2f Mbps", avgDownloadSpeed)
            downloadRate.text = String.format("Download Rate: %.2f Mbps", avgDownloadSpeed)

            // Upload Speed Test
            uploadSpeed.text = "Measuring Upload Speed..."
            val uploadSpeeds = mutableListOf<Double>()
            withContext(Dispatchers.IO) {
                measureUploadSpeed(client, uploadSpeeds)
            }

            // Calculate and display average upload speed
            val avgUploadSpeed = uploadSpeeds.average()
            uploadSpeed.text = String.format("Upload: %.2f Mbps", avgUploadSpeed)
            uploadRate.text = String.format("Upload Rate: %.2f Mbps", avgUploadSpeed)

            // Display the average speed after tests
            val overallAvgSpeed = (avgDownloadSpeed + avgUploadSpeed) / 2
            speedText.text = String.format("Avg Speed: %.2f Mbps", overallAvgSpeed)
        }
    }

    private suspend fun measurePing(client: OkHttpClient, url: String): Long {
        return withContext(Dispatchers.IO) {
            measureTimeMillis {
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
    }

    private suspend fun measureDownloadSpeed(client: OkHttpClient, downloadSpeeds: MutableList<Double>) {
        val downloadUrl = "https://jsonplaceholder.typicode.com/posts/1"
        val totalDataSizeInKB = 1024.0 // Adjust to reflect actual data size in KB

        repeat(10) { index ->
            var success = false
            repeat(3) { attempt ->
                try {
                    val downloadTime = measureTimeMillis {
                        client.newCall(Request.Builder().url(downloadUrl).build()).execute().use { response ->
                            if (!response.isSuccessful) throw IOException("Failed to download file: ${response.message}")
                            response.body?.string() // Assuming the response is valid
                        }
                    }
                    success = true
                    val downloadSpeedMbps = (totalDataSizeInKB / (downloadTime / 1000.0)) / 1024 // Convert to Mbps
                    downloadSpeeds.add(downloadSpeedMbps)
                    runOnUiThread {
                        downloadRate.text = String.format("Download Rate: %.2f Mbps", downloadSpeedMbps)
                    }
                    Log.i("SpeedTestActivity", "Download $index completed in $downloadTime ms at speed: ${downloadSpeedMbps}Mbps")
                    return@repeat
                } catch (e: IOException) {
                    Log.e("SpeedTestActivity", "Retrying download $index... (${attempt + 1}/3)", e)
                }
            }
            if (!success) {
                Log.e("SpeedTestActivity", "Failed to download $index after 3 attempts")
            }
        }
    }

    private suspend fun measureUploadSpeed(client: OkHttpClient, uploadSpeeds: MutableList<Double>) {
        val uploadUrl = "https://jsonplaceholder.typicode.com/posts"
        val totalDataSizeInKB = 512.0 // Adjust to reflect actual data size in KB

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
                    success = true
                    val uploadSpeedMbps = (totalDataSizeInKB / (uploadTime / 1000.0)) / 1024 // Convert to Mbps
                    uploadSpeeds.add(uploadSpeedMbps)
                    runOnUiThread {
                        uploadRate.text = String.format("Upload Rate: %.2f Mbps", uploadSpeedMbps)
                    }
                    Log.i("SpeedTestActivity", "Upload $index completed in $uploadTime ms at speed: ${uploadSpeedMbps}Mbps")
                    return@repeat
                } catch (e: IOException) {
                    Log.e("SpeedTestActivity", "Retrying upload $index... (${attempt + 1}/3)", e)
                }
            }
            if (!success) {
                Log.e("SpeedTestActivity", "Failed to upload $index after 3 attempts")
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
        CoroutineScope(Dispatchers.IO).launch {
            val client = OkHttpClient()
            val request = Request.Builder()
                .url("https://api.ipify.org?format=text")
                .build()

            try {
                client.newCall(request).execute().use { response ->
                    if (!response.isSuccessful) throw IOException("Unexpected code $response")
                    val ip = response.body?.string()
                    withContext(Dispatchers.Main) {
                        ipAddress.text = "IP Address: $ip"
                    }
                }
            } catch (e: Exception) {
                Log.e("SpeedTestActivity", "Failed to fetch IP address", e)
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@SpeedTestActivity, "Failed to fetch IP address", Toast.LENGTH_SHORT).show()
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
