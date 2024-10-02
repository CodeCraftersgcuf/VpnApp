package com.example.privatevpn

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
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
    private lateinit var startSpeedTestButton: MaterialButton
    private lateinit var downloadRate: TextView
    private lateinit var uploadRate: TextView
    private lateinit var backhomes: ImageView

    private var speedTestJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_speed_test)

        // Initialize views
        backhomes = findViewById(R.id.back_home)
        downloadSpeed = findViewById(R.id.textView4)
        uploadSpeed = findViewById(R.id.uploadRateTextView)
        ipAddress = findViewById(R.id.ipAddressTextView)
        startSpeedTestButton = findViewById(R.id.disconnect)
        downloadRate = findViewById(R.id.textView4)
        uploadRate = findViewById(R.id.uploadRateTextView)

        // Back button to finish the activity
        backhomes.setOnClickListener {
            finish();
        }

        // Start speed test on button click
        startSpeedTestButton.setOnClickListener {
            if (isInternetAvailable()) {
                fetchIPAddress()
                startSpeedTest()
            } else {
                Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun startSpeedTest() {
        // Cancel any ongoing speed test
        speedTestJob?.cancel()

        speedTestJob = CoroutineScope(Dispatchers.Main).launch {
            startSpeedTestButton.isEnabled = false // Disable button during test
            try {
                val client = OkHttpClient.Builder()
                    .callTimeout(30, java.util.concurrent.TimeUnit.SECONDS) // Increased timeout
                    .build()

                // Download Speed Test
                downloadSpeed.text = "Measuring Download Speed..."
                val downloadSpeeds = mutableListOf<Double>()
                withContext(Dispatchers.IO) {
                    measureDownloadSpeed(client, downloadSpeeds)
                }

                // Calculate and display average download speed
                val avgDownloadSpeed = downloadSpeeds.average()
                downloadSpeed.text = String.format("Download: %.2f MBps", avgDownloadSpeed)
                downloadRate.text = String.format("Download Rate: %.2f MBps", avgDownloadSpeed)

                // Upload Speed Test
                uploadSpeed.text = "Measuring Upload Speed..."
                val uploadSpeeds = mutableListOf<Double>()
                withContext(Dispatchers.IO) {
                    measureUploadSpeed(client, uploadSpeeds)
                }

                // Calculate and display average upload speed
                val avgUploadSpeed = uploadSpeeds.average()
                uploadSpeed.text = String.format("Upload: %.2f MBps", avgUploadSpeed)
                uploadRate.text = String.format("Upload Rate: %.2f MB" +
                        "ps", avgUploadSpeed)

            } catch (e: Exception) {
                Log.e("SpeedTestActivity", "Error during speed test", e)
                Toast.makeText(this@SpeedTestActivity, "Error during speed test: ${e.message}", Toast.LENGTH_SHORT).show()
            } finally {
                startSpeedTestButton.isEnabled = true // Re-enable button after test
            }
        }
    }

    private suspend fun measureDownloadSpeed(client: OkHttpClient, downloadSpeeds: MutableList<Double>) {
        val downloadUrl = "https://jsonplaceholder.typicode.com/posts" // Update to your download test URL
        val totalDataSizeInKB = 1024.0 // Approximate data size in KB (1 MB)

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

                    // Update the UI with the current download speed
                    withContext(Dispatchers.Main) {
                        downloadRate.text = String.format("Download Rate: %.2f MBps", downloadSpeedMbps)
                    }

                    Log.i("SpeedTestActivity", "Download $index completed in $downloadTime ms at speed: ${downloadSpeedMbps} MBps")
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
        val uploadUrl = "https://jsonplaceholder.typicode.com/posts" // Update to your upload test URL
        val totalDataSizeInKB = 1024.0 // Approximate data size in KB (1 MB)
        val uploadData = """{"title": "foo", "body": "bar", "userId": 1}""" // Example JSON data
        val requestBody = RequestBody.create("application/json".toMediaTypeOrNull(), uploadData)

        repeat(10) { index ->
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

                    // Update the UI with the current upload speed
                    withContext(Dispatchers.Main) {
                        uploadRate.text = String.format("Upload Rate: %.2f MBps", uploadSpeedMbps)
                    }

                    Log.i("SpeedTestActivity", "Upload $index completed in $uploadTime ms at speed: ${uploadSpeedMbps} MBps")
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

    private fun fetchIPAddress() {
        CoroutineScope(Dispatchers.IO).launch {
            val client = OkHttpClient()
            val request = Request.Builder()
                .url("https://ipinfo.io/json")
                .build()

            try {
                client.newCall(request).execute().use { response ->
                    if (!response.isSuccessful) throw IOException("Unexpected code $response")
                    val ipResponse = response.body?.string()
                    // Extracting the IP address from the JSON response
                    val ip = ipResponse?.let { parseIpFromJson(it) }
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

    private fun parseIpFromJson(json: String): String? {
        // Simple JSON parsing to extract the IP address
        return try {
            val jsonObject = org.json.JSONObject(json)
            jsonObject.getString("ip")
        } catch (e: Exception) {
            Log.e("SpeedTestActivity", "JSON parsing error", e)
            null
        }
    }

    private fun isInternetAvailable(): Boolean {
        // Your implementation of internet availability check here
        return true // This should be replaced with actual check logic
    }
}
