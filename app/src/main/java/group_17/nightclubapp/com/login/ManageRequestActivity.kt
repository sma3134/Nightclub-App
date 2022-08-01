package group_17.nightclubapp.com.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import group_17.nightclubapp.com.R
import group_17.nightclubapp.com.login.model.ManageRequestActivityViewModel
import group_17.nightclubapp.com.login.model.RequestListAdapter
import group_17.nightclubapp.com.map.MapsActivity
import group_17.nightclubapp.com.request.model.DAORequest
import group_17.nightclubapp.com.request.model.RequestDB
import group_17.nightclubapp.com.setting.SettingsActivity

class ManageRequestActivity : AppCompatActivity(), ValueEventListener {
    private lateinit var view: View
    private lateinit var viewModel: ManageRequestActivityViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_request)

        //Setting up action bar
        supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar!!.setCustomView(R.layout.action_bar)
        val parent = supportActionBar!!.customView.parent as androidx.appcompat.widget.Toolbar
        parent.setContentInsetsAbsolute(0, 0)
        view= supportActionBar!!.customView
        val clubName = view.findViewById<TextView>(R.id.clubName)
        val settings = view.findViewById<ImageButton>(R.id.settings)
        settings.visibility = View.INVISIBLE
        clubName.text="Request"
        setActionBarClickListeners()

        viewModel = ViewModelProvider(this).get(ManageRequestActivityViewModel::class.java)
        val clubID = intent.getStringExtra(MapsActivity.PLACE_ID_KEY)
        if (clubID != null) {
            viewModel.clubID = clubID
        }

        if (!viewModel.isListening) {
            val daoRequest = DAORequest()
            daoRequest.getRequests().addValueEventListener(this)
            viewModel.isListening = true
        }

        val requestListView = findViewById<ListView>(R.id.djRequestListview)
        val arrayAdapter = RequestListAdapter(this, ArrayList())
        requestListView.adapter = arrayAdapter

        viewModel.requestList.observe(this) {
            arrayAdapter.replace(it)
            arrayAdapter.notifyDataSetChanged()
        }

    }

    private fun setActionBarClickListeners(){
        val intentSettings = Intent(this, SettingsActivity::class.java)
        val settingsButton = view.findViewById<ImageView>(R.id.settings)
        val backButton = view.findViewById<ImageView>(R.id.back)

        settingsButton.setOnClickListener() {
            startActivity(intentSettings)
        }
        backButton.setOnClickListener {
            finish()
        }
    }

    override fun onDataChange(snapshot: DataSnapshot) {
        viewModel.clearList()
        snapshot.children.forEach {
            val req = it.getValue(RequestDB::class.java)
            if (req != null) {
                if (req.isValid()) {
                    viewModel.addRequest(req)
                }
            }
        }
    }

    override fun onCancelled(error: DatabaseError) {

    }
}