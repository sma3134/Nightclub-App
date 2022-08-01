package group_17.nightclubapp.com.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import group_17.nightclubapp.com.R
import group_17.nightclubapp.com.contact.model.Club
import group_17.nightclubapp.com.databinding.ActivityManageBinding
import group_17.nightclubapp.com.map.MapsActivity
import group_17.nightclubapp.com.setting.SettingsActivity

class ManageActivity : AppCompatActivity() {
    private lateinit var view: View
    private lateinit var manageBinding: ActivityManageBinding
    var clubId =""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        clubId= this.intent.getStringExtra(MapsActivity.PLACE_ID_KEY).toString()
        manageBinding= ActivityManageBinding.inflate(layoutInflater)

        //Set up Action bar
        supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar!!.setCustomView(R.layout.action_bar)
        val parent = supportActionBar!!.customView.parent as androidx.appcompat.widget.Toolbar
        parent.setContentInsetsAbsolute(0, 0)
        view= supportActionBar!!.customView
        val clubName = view.findViewById<TextView>(R.id.clubName)
        val settings = view.findViewById<ImageButton>(R.id.settings)
        settings.visibility =View.INVISIBLE
        clubName.text="Management"
        setClickListeners()


        setContentView(manageBinding.root)
    }

    private fun setClickListeners(){
        val settingsButton = view.findViewById<ImageView>(R.id.settings)
        val backButton = view.findViewById<ImageView>(R.id.back)

        settingsButton.setOnClickListener() {

            val intentSettings = Intent(this, SettingsActivity::class.java)
            startActivity(intentSettings)
        }
        backButton.setOnClickListener {
            finish()
        }
        manageBinding.requestCV.setOnClickListener{
            val requestIntent=Intent(this, ManageRequestActivity::class.java)
            requestIntent.putExtra(MapsActivity.PLACE_ID_KEY, clubId)
            startActivity(requestIntent)
        }
        manageBinding.menuCV.setOnClickListener{
            val menuIntent=Intent(this, ManageMenuActivity::class.java)
            menuIntent.putExtra(MapsActivity.PLACE_ID_KEY, clubId)
            startActivity(menuIntent)
        }

        manageBinding.bookCV.setOnClickListener{
            val bookIntent=Intent(this, ManageBookActivity::class.java)
            bookIntent.putExtra(MapsActivity.PLACE_ID_KEY, clubId)
            startActivity(bookIntent)
        }
    }
}
