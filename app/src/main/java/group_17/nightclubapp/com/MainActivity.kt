package group_17.nightclubapp.com


import android.os.Bundle
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        val tabLayout = mainBinding.tabLayout
        val viewPager = mainBinding.viewPager

        //set up viewpager adapter
        viewPager.adapter = ViewPagerAdapter(supportFragmentManager, lifecycle)
        viewPager.offscreenPageLimit = 4
        //set up tab layout
        tabLayoutMediator = TabLayoutMediator(tabLayout, viewPager, true)
        { tab, position ->
            tab.icon = AppCompatResources.getDrawable(this, tabIcons[position])
            tab.text = navArray[position]
        }
        tabLayoutMediator.attach()

        // This is the clubID that user has clicked on from map.
        val currClubID = intent.getStringExtra(MapsActivity.PLACE_ID_KEY)
    }

    //destroy tab
    override fun onDestroy() {
        super.onDestroy()
        tabLayoutMediator.detach()
    }
}