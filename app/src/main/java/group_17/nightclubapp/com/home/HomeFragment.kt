package group_17.nightclubapp.com.home

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import group_17.nightclubapp.com.R
import group_17.nightclubapp.com.contact.model.Club
import group_17.nightclubapp.com.contact.model.DAOClub
import group_17.nightclubapp.com.map.MapsActivity.Companion.PLACE_ID_KEY

class HomeFragment : Fragment(), ValueEventListener, LocationListener {

    private lateinit var locationManager: LocationManager
    private lateinit var daoClub: DAOClub

    private lateinit var imageView: ImageView
    private lateinit var tvTitle: TextView
    private lateinit var tvAddress: TextView
    private lateinit var tvDistance: TextView
    private lateinit var tvHour: TextView
    private lateinit var tvWebsite: TextView
    private lateinit var tvAnnouncement: TextView
    private lateinit var tvAbout: TextView
    private lateinit var tvFAQ: TextView
    private lateinit var tvOther: TextView
    private lateinit var llAnnoucement: LinearLayout
    private lateinit var llAbout: LinearLayout
    private lateinit var llFAQ: LinearLayout
    private lateinit var llOther: LinearLayout
    private var clubInfo: Club? = null
    private var currPlaceID: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        tvTitle = root.findViewById(R.id.tv_home_title)
        tvAddress = root.findViewById(R.id.tv_home_address)
        tvDistance = root.findViewById(R.id.tv_home_distance)
        tvHour = root.findViewById(R.id.tv_home_hours)
        tvWebsite = root.findViewById(R.id.tv_home_hours)
        tvAnnouncement = root.findViewById(R.id.tv_home_announcement)
        tvAbout = root.findViewById(R.id.tv_home_about)
        tvFAQ = root.findViewById(R.id.tv_home_faq)
        tvOther = root.findViewById(R.id.tv_home_other)
        llAnnoucement = root.findViewById(R.id.ll_announcement)
        llAbout = root.findViewById(R.id.ll_about)
        llFAQ = root.findViewById(R.id.ll_faq)
        llOther = root.findViewById(R.id.ll_other)

        daoClub = DAOClub()

        val intent = activity?.intent
        currPlaceID = intent?.getStringExtra(PLACE_ID_KEY)
        println("debug: currplaceID $currPlaceID")
        daoClub.getClubs().addValueEventListener(this)
        println("debug: clubinfo $clubInfo")

        return root
    }

    override fun onDataChange(snapshot: DataSnapshot) {
        snapshot.children.forEach {
            val req = it.getValue(Club::class.java)
            if (req?.clubId == currPlaceID) {
                tvTitle.text = req?.name
                tvAddress.text = req?.address
                tvWebsite.text = req?.website
                tvHour.text = req?.hours
                if (req?.announcement!!.isEmpty()) {
                    llAnnoucement.visibility = View.INVISIBLE
                } else {
                    tvAnnouncement.text = req.announcement
                }
                if (req.about!!.isEmpty()) {
                    llAbout.visibility = View.INVISIBLE
                } else {
                    tvAbout.text = req.about
                }
                if (req.faq!!.isEmpty()) {
                    llFAQ.visibility = View.INVISIBLE
                } else {
                    tvFAQ.text = req.faq
                }
                if (req.other!!.isEmpty()) {
                    llOther.visibility = View.INVISIBLE
                } else {
                    tvOther.text = req.other
                }

            }
        }
    }

    override fun onCancelled(error: DatabaseError) {

    }

    override fun onLocationChanged(location: Location) {
        val lat = location.latitude
        val long = location.longitude
        println("debug: lat lng $lat $long")
    }

}