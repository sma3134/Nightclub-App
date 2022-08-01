package group_17.nightclubapp.com.setting

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import group_17.nightclubapp.com.R
import group_17.nightclubapp.com.databinding.ActivityHelpActivityBinding

class HelpActivity : AppCompatActivity() {
    private lateinit var helpBinding:ActivityHelpActivityBinding
    private lateinit var view: View


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        helpBinding= ActivityHelpActivityBinding.inflate(layoutInflater)

        //Setting up action bar
        supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar!!.setCustomView(R.layout.action_bar)
        val parent = supportActionBar!!.customView.parent as androidx.appcompat.widget.Toolbar
        parent.setContentInsetsAbsolute(0, 0)
        view= supportActionBar!!.customView
        val clubName = view.findViewById<TextView>(R.id.clubName)
        val settings = view.findViewById<ImageButton>(R.id.settings)
        settings.visibility = View.INVISIBLE
        clubName.text="Get Help"
        setActionBarClickListeners()

        setContentView(helpBinding.root)
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

        helpBinding.phoneLL.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL);
            intent.data = Uri.parse("tel:${helpBinding.callTV.text}")
            try {
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(
                    this,
                    "No apps to handle the request",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        helpBinding.emailLL.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO)
            intent.data = Uri.parse("mailto:${helpBinding.emailTV.text}")
            try {
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(
                    this,
                    "No apps to handle the request",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

}