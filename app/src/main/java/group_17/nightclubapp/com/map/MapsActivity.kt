package group_17.nightclubapp.com.map

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RatingBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModelProvider

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
import group_17.nightclubapp.com.home.HomeFragment
import org.w3c.dom.Text

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var placeDetail: place

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
        MapsViewModel.data.observe(this){
            setMarker(it)
        }

        mMap.setOnMarkerClickListener(object : GoogleMap.OnMarkerClickListener {
            lateinit var place: place
            override fun onMarkerClick(marker: Marker): Boolean {
                card_view.visibility = View.VISIBLE
                var index = markerList.indexOf(marker.tag)
                if (index!=-1) {
                    place = placeList[index]
                    val name = findViewById<TextView>(R.id.placeName)
                    val rate = findViewById<RatingBar>(R.id.placeRating)
                    val address = findViewById<TextView>(R.id.placeAddress)
                    val phone = findViewById<TextView>(R.id.placePhone)
                    name.text = place.Name
                    rate.rating = place.Rating.toFloat()
                    address.text = place.Address
                    phone.text = place.Phone
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
            val HomeInt = Intent(this, MainActivity::class.java)
            startActivity(HomeInt)
        }
    }
}