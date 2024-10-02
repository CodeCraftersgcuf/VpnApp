package com.example.privatevpn

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

class checkLocationActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var backhome: ImageView
    private lateinit var ipAddressTextView: TextView
    private lateinit var timezoneTextView: TextView
    private lateinit var locationTextView: TextView
    private lateinit var mapView: MapView
    private lateinit var googleMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_location)

        backhome = findViewById(R.id.backhome)
        ipAddressTextView = findViewById(R.id.textView11) // Replace with the correct ID for IP address TextView
        timezoneTextView = findViewById(R.id.textView14) // Replace with the correct ID for Timezone TextView
        locationTextView = findViewById(R.id.textView13) // Replace with the correct ID for Location TextView
        mapView = findViewById(R.id.map_view)

        // Initialize the MapView
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

        backhome.setOnClickListener {
//            val intent = Intent(this, sidenavigationActivity::class.java)
//            startActivity(intent)
            finish();
//            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        fetchIPInfo()
    }

    private fun fetchIPInfo() {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://ipinfo.io/json") // Fetch IP information from ipinfo.io
            .build()

        client.newCall(request).enqueue(object : okhttp3.Callback {
            override fun onFailure(call: okhttp3.Call, e: IOException) {
                e.printStackTrace()
                runOnUiThread {
                    ipAddressTextView.text = "Error fetching IP info"
                }
            }

            override fun onResponse(call: okhttp3.Call, response: Response) {
                response.use {
                    if (!it.isSuccessful) throw IOException("Unexpected code $it")

                    val jsonData = it.body?.string()
                    val jsonObject = JSONObject(jsonData)

                    val ipAddress = jsonObject.getString("ip")
                    val timezone = jsonObject.getString("timezone")
                    val city = jsonObject.getString("city")
                    val region = jsonObject.getString("region")
                    val loc = jsonObject.getString("loc") // "loc" is a string in the format "lat,lon"
                    val latLng = loc.split(",") // Split into latitude and longitude
                    val latitude = latLng[0].toDouble()
                    val longitude = latLng[1].toDouble()

                    runOnUiThread {
                        ipAddressTextView.text = ipAddress
                        timezoneTextView.text = timezone
                        locationTextView.text = "$city, $region"
                        addMarkerToMap(LatLng(latitude, longitude)) // Add marker on the map
                    }
                }
            }
        })
    }

    private fun addMarkerToMap(location: LatLng) {
        // Add a marker at the given location and move the camera
        googleMap.addMarker(MarkerOptions().position(location).title("Your Location"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 10f)) // Zoom level of 10
    }

    // MapView lifecycle methods
    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap // Store reference to GoogleMap
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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }
}
