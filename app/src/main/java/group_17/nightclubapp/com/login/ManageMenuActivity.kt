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
import group_17.nightclubapp.com.setting.SettingsActivity

class ManageMenuActivity : AppCompatActivity() {
    private lateinit var view: View
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_menu)

        //Setting up action bar
        supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar!!.setCustomView(R.layout.action_bar)
        val parent = supportActionBar!!.customView.parent as androidx.appcompat.widget.Toolbar
        parent.setContentInsetsAbsolute(0, 0)
        view= supportActionBar!!.customView
        val clubName = view.findViewById<TextView>(R.id.clubName)
        val settings = view.findViewById<ImageButton>(R.id.settings)
        settings.visibility = View.INVISIBLE
        clubName.text="Menu"
        setActionBarClickListeners()




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
}