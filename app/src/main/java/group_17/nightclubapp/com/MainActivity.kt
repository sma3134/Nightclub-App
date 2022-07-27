package group_17.nightclubapp.com


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toolbar
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import com.google.android.material.tabs.TabLayoutMediator
import group_17.nightclubapp.com.databinding.ActivityMainBinding


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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)
        val intent = Intent(this, SettingsActivity::class.java)

        supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar!!.setCustomView(R.layout.action_bar)

        val parent = supportActionBar!!.customView.parent as androidx.appcompat.widget.Toolbar
        parent.setContentInsetsAbsolute(0, 0)
        val view: View = supportActionBar!!.customView
        val settingsButton=view.findViewById<ImageView>(R.id.settings)
        settingsButton.setOnClickListener{
            startActivity(intent)
        }

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
}