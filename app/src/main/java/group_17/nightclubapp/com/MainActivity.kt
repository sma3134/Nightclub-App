package group_17.nightclubapp.com


import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import com.google.android.material.tabs.TabLayoutMediator
import group_17.nightclubapp.com.databinding.ActivityMainBinding
import group_17.nightclubapp.com.map.MapsActivity


private lateinit var mainBinding: ActivityMainBinding
val navArray = arrayOf(
    "Home",
    "Requests",
    "Menu",
    "Book",
    "Contact"
)
private val tabIcons = intArrayOf(
    R.drawable.ic_action_home,
    R.drawable.ic_action_request,
    R.drawable.ic_action_menu,
    R.drawable.ic_action_book,
    R.drawable.ic_action_contact
)

class MainActivity : AppCompatActivity() {
    private lateinit var tabLayoutMediator: TabLayoutMediator
    private lateinit var view: View
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        // This is the clubID that user has clicked on from map.
        val currClubID = this.intent.getStringExtra(MapsActivity.PLACE_ID_KEY)

        // Set up Action bar
        supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar!!.setCustomView(R.layout.action_bar)
        val parent = supportActionBar!!.customView.parent as androidx.appcompat.widget.Toolbar
        parent.setContentInsetsAbsolute(0, 0)
        view= supportActionBar!!.customView
        val clubName = view.findViewById<TextView>(R.id.clubName)
        clubName.text=this.intent.getStringExtra(MapsActivity.PLACE_NAME)
        setActionBarClickListeners()

        val tabLayout = mainBinding.tabLayout
        val viewPager = mainBinding.viewPager

        //set up viewpager adapter
        viewPager.adapter = ViewPagerAdapter(supportFragmentManager, lifecycle)
        //set up tab layout
        tabLayoutMediator = TabLayoutMediator(tabLayout, viewPager, true)
        { tab, position ->
            tab.icon = AppCompatResources.getDrawable(this, tabIcons[position])
            tab.text = navArray[position]
        }
        tabLayoutMediator.attach()
    }

    //destroy tab
    override fun onDestroy() {
        super.onDestroy()
        tabLayoutMediator.detach()
    }

    //Set up click listeners
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