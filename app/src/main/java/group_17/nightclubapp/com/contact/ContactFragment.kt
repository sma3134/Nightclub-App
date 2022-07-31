package group_17.nightclubapp.com.contact

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import group_17.nightclubapp.com.R
import group_17.nightclubapp.com.contact.model.Club
import group_17.nightclubapp.com.contact.model.DAOClub
import group_17.nightclubapp.com.map.MapsActivity


class ContactFragment : Fragment(), ValueEventListener {
    private lateinit var daoClub: DAOClub

    //    private lateinit var imageView: ImageView
    private lateinit var phoneTV: TextView
    private lateinit var emailTV: TextView
    private lateinit var addressTV: TextView
    private lateinit var facebookIB: ImageButton
    private lateinit var instaIB: ImageButton
    private lateinit var twitterIB: ImageButton
    private lateinit var phoneLL: LinearLayout
    private lateinit var emailLL: LinearLayout
    private var currPlaceID: String? = null
    private var phoneNumber = ""
    private var emailLink = ""
    private var facebookLink = ""
    private var instaLink = ""
    private var twitterLink = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_contact, container, false)

        phoneTV = root.findViewById(R.id.callTV)
        emailTV = root.findViewById(R.id.emailTV)
        addressTV = root.findViewById(R.id.addressTv)
        facebookIB = root.findViewById(R.id.facebookIB)
        instaIB = root.findViewById(R.id.instaId)
        twitterIB = root.findViewById(R.id.twitterIB)
        phoneLL = root.findViewById(R.id.phoneLL)
        emailLL = root.findViewById(R.id.emailLL)
        daoClub = DAOClub()
        setClicks()
        currPlaceID = requireActivity().intent.getStringExtra(MapsActivity.PLACE_ID_KEY)
        daoClub.getClubs().addValueEventListener(this)
        return root
    }

    @SuppressLint("SetTextI18n")
    override fun onDataChange(snapshot: DataSnapshot) {
        snapshot.children.forEach {
            val req = it.getValue(Club::class.java)
            if (req?.clubId == currPlaceID) {
                phoneNumber = req!!.phoneNumber.toString()
                phoneTV.text = phoneNumber
                emailLink = req.email.toString()
                emailTV.text = emailLink
                addressTV.text = req.address

                facebookLink = req.facebook.toString()
                instaLink = req.instagram.toString()
                twitterLink = req.twitter.toString()

            }
        }

    }

    override fun onCancelled(error: DatabaseError) {
    }

    private fun setClicks() {
        facebookIB.setOnClickListener {
            if (facebookLink != "") {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(facebookLink))
                startActivity(browserIntent)
            } else {
                Toast.makeText(
                    requireActivity(),
                    "No Facebook page has been found",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        instaIB.setOnClickListener {
            if (instaLink != "") {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(instaLink))
                startActivity(browserIntent)
            } else {
                Toast.makeText(
                    requireActivity(),
                    "No Instagram page has been found",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        twitterIB.setOnClickListener {
            if (twitterLink != "") {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(twitterLink))
                startActivity(browserIntent)
            } else {
                Toast.makeText(
                    requireActivity(),
                    "No Twitter page has been found",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        phoneLL.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL);
            intent.data = Uri.parse("tel:$phoneNumber")
            try {
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(
                    requireActivity(),
                    "No apps to handle the request",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        emailLL.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.data = Uri.parse("mailto:$emailLink")
            try {
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(
                    requireActivity(),
                    "No apps to handle the request",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    }

}