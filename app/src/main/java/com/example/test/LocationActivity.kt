package com.example.yourapp

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.test.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject

class LocationActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mapView: MapView
    private var googleMap: GoogleMap? = null
    private var isMapReady = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)

        // Initialize the MapView
        mapView = findViewById(R.id.map_view)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

        // Fetch and display the IP address location
        fetchIpAddressLocation()

        // Handle back button click
        val backButton: ImageView = findViewById(R.id.backButton)
        backButton.setOnClickListener {
            finish() // Go back to the previous activity
        }
    }

    private fun fetchIpAddressLocation() {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                Log.d("FETCH_IP", "Starting IP address fetch...")
                val client = OkHttpClient()
                val request = Request.Builder()
                    .url("http://ip-api.com/json")
                    .build()
                val response = client.newCall(request).execute()

                val responseData = response.body?.string()
                if (!response.isSuccessful || responseData == null) {
                    Log.e("FETCH_IP", "Request failed with code: ${response.code}, data: $responseData")
                    return@launch
                }

                Log.d("FETCH_IP", "Response received: $responseData")

                val json = JSONObject(responseData)
                val ip = json.optString("query", "Unknown IP")
                val timezone = json.optString("timezone", "Unknown Timezone")
                val city = json.optString("city", "Unknown City")
                val countryCode = json.optString("countryCode", "Unknown Country")
                val lat = json.optDouble("lat", 0.0)
                val lon = json.optDouble("lon", 0.0)

                Log.d("PARSE_JSON", "Parsed IP: $ip, Timezone: $timezone, City: $city, Country: $countryCode, Lat: $lat, Lon: $lon")

                withContext(Dispatchers.Main) {
                    if (lat != 0.0 && lon != 0.0) {
                        Log.d("UPDATE_UI_CALL", "Calling updateUI with lat: $lat, lon: $lon")
                        updateUI(ip, timezone, city, countryCode, lat, lon)
                    } else {
                        Log.e("LOCATION_ERROR", "Invalid latitude or longitude values.")
                    }
                }
            } catch (e: Exception) {
                Log.e("FETCH_ERROR", "Error fetching IP address location: ${e.message}")
                e.printStackTrace()
            }
        }
    }

    private fun updateUI(ip: String, timezone: String, city: String, countryCode: String, lat: Double, lon: Double) {
        try {
            Log.d("UPDATE_UI", "Updating UI with IP: $ip, Timezone: $timezone, Location: $city, $countryCode")

            findViewById<TextView>(R.id.ip_address).text = "IP Address: $ip"
            findViewById<TextView>(R.id.time_zone).text = "Timezone: $timezone"
            findViewById<TextView>(R.id.location_name).text = "$city, $countryCode"

            if (isMapReady && googleMap != null) {
                Log.d("MAP_READY_CHECK", "GoogleMap is ready. Updating map with marker.")
                val location = LatLng(lat, lon)
                googleMap!!.addMarker(MarkerOptions().position(location).title("$city, $countryCode"))
                googleMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 12f))
                Log.d("MAP_UPDATE", "Map updated with marker at: $lat, $lon")
            } else {
                Log.e("MAP_ERROR", "GoogleMap is not ready yet or is null.")
            }
        } catch (e: Exception) {
            Log.e("UPDATE_UI_ERROR", "Error updating UI: ${e.message}")
            e.printStackTrace()
        }
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        isMapReady = true
        Log.d("MAP_READY", "GoogleMap is ready.")
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }
}
