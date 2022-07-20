package group_17.nightclubapp.com.request

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import group_17.nightclubapp.com.R
import group_17.nightclubapp.com.request.model.DAORequest
import group_17.nightclubapp.com.request.model.Request
import java.util.*

class RequestFragment : Fragment(), ValueEventListener {
    private lateinit var submitButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_request, container, false)
        val daoRequest = DAORequest()

        submitButton = view.findViewById(R.id.submitRequestBtn)

        //submit to database
        submitButton.setOnClickListener {
            val clubId = context?.getString(R.string.BarNone)?.toLong()
            val req = Request("Hit em up Tupac", Request.SONG_REQUEST, clubId, Calendar.getInstance().timeInMillis)
            if (req.isValid()) {
                daoRequest.add(req).addOnSuccessListener {
                    Toast.makeText(context, "Sent to DJ", Toast.LENGTH_SHORT).show()
                }.addOnFailureListener {
                    Toast.makeText(context, "Not Sent to DJ", Toast.LENGTH_SHORT).show()
                }
            }
        }

        //get requests will only fire
        daoRequest.getRequests().addValueEventListener(this)


        return view
    }

    override fun onDataChange(snapshot: DataSnapshot) {
        snapshot.children.forEach {
            val req = it.getValue(Request::class.java)
            if (req != null) {
                if (req.isValid()) {
                    println("debug: Request: ${req.request}"  )
                }
            }
        }
    }

    override fun onCancelled(error: DatabaseError) {

    }
}