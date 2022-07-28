package group_17.nightclubapp.com.map

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.model.Place
import group_17.nightclubapp.com.MainActivity
import group_17.nightclubapp.com.R
import group_17.nightclubapp.com.databinding.ActivityMapsBinding
import java.time.LocalDate
import java.util.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var placeDetail: place
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var currPlaceID: String? = null
    private var myLongitude: Double? = null
    private var myLatitude: Double? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        checkPermissions(savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        if (myLatitude == null || myLatitude == null)
            getLocation()

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setTitle("Nearby Clubs")

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val MapsViewModel = ViewModelProvider(this).get(MapsViewModel::class.java)
        val card_view = findViewById<CardView>(R.id.cardView)

        //theses two list's indexes are synchronized
        val markerList = ArrayList<String>()
        val placeList = ArrayList<place>()

        //get API response and set markers returning place object
        fun setMarker(place: Place){
            val res = place(place)
            val loc = res.LatLng
            val cameraSetting = CameraPosition.Builder().target(loc).zoom(15.0f).build()
            val marker = mMap.addMarker(MarkerOptions().position(loc).title(res.Name))
            marker!!.tag = res.ID
            mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraSetting))
            markerList.add(res.ID)
            placeList.add(res)
        }

        MapsViewModel.getPlaceData()//API request
        for (place in MapsViewModel.data) {
            place.observe(this){
                setMarker(it)
            }
        }

        mMap.setOnMarkerClickListener(object : GoogleMap.OnMarkerClickListener {
            lateinit var place: place
            override fun onMarkerClick(marker: Marker): Boolean {
                card_view.visibility = View.VISIBLE
                val index = markerList.indexOf(marker.tag)
                if (index != -1) {
                    place = placeList[index]
                    currPlaceID = place.ID
                    val name = findViewById<TextView>(R.id.placeName)
                    val rate = findViewById<RatingBar>(R.id.placeRating)
                    val address = findViewById<TextView>(R.id.placeAddress)
                    val phone = findViewById<TextView>(R.id.placePhone)
                    val hours = findViewById<TextView>(R.id.placeHours)
                    name.text = place.Name
                    address.text = place.Address
                    phone.text = place.Phone

                    val rating = place.Rating?.toFloat()
                    if (rating != null) {
                        rate.rating = rating
                        rate.visibility = View.VISIBLE
                    } else {
                        rate.visibility = View.GONE
                    }

                    val dayOfWeek = LocalDate.now().dayOfWeek.name
                    place.OpeningH?.weekdayText?.forEach {
                        if (it.contains(dayOfWeek, true)) {
                            if (it.contains("closed", true)) {
                                hours.text = resources.getString(R.string.closed_today)
                                hours.setTextColor(resources.getColor(R.color.red))
                            } else if (it.contains(":")) {
                                hours.text = resources.getString(R.string.open_today_from,
                                             it.substringAfter(": "))
                                hours.setTextColor(resources.getColor(R.color.green))
                            }
                        }
                    }

                }
                return false
            }
        })
        //if blank map clicked, cardview is disappeared
        mMap.setOnMapClickListener(object : GoogleMap.OnMapClickListener {
            override fun onMapClick(latLng: LatLng) {
                card_view.visibility = View.GONE
            }
        })

        val goBtn = findViewById<Button>(R.id.gogo)
        goBtn.setOnClickListener {
            val homeIntent = Intent(this, MainActivity::class.java)
            homeIntent.putExtra(PLACE_ID_KEY, currPlaceID)
            homeIntent.putExtra(LAT_ID_KEY, myLatitude)
            homeIntent.putExtra(LNG_ID_KEY, myLongitude)
            startActivity(homeIntent)
        }
    }

    fun checkPermissions(savedInstanceState: Bundle?) {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                0)

        }
    }

    private fun getLocation() {
        fusedLocationClient.lastLocation.addOnSuccessListener { location : Location? ->
            location?.let {
                myLongitude = it.longitude
                myLatitude = it.latitude
            }
        }
    }

    companion object {
        const val PLACE_ID_KEY = "PLACE_ID_KEY"
        const val LAT_ID_KEY = "LAT_ID_KEY"
        const val LNG_ID_KEY = "LNG_ID_KEY"
    }
}