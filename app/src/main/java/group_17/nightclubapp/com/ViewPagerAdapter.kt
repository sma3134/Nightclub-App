package group_17.nightclubapp.com


import android.app.DownloadManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import group_17.nightclubapp.com.book.BookFragment
import group_17.nightclubapp.com.contact.ContactFragment
import group_17.nightclubapp.com.home.HomeFragment
import group_17.nightclubapp.com.menu.MenuFragment
import group_17.nightclubapp.com.request.RequestFragment

private const val NUM_TABS =5

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle){

    override fun getItemCount(): Int {
        return NUM_TABS
    }

    //show different fragment depending on position value
    override fun createFragment(position: Int): Fragment {
        when(position){
            0 -> return HomeFragment()
            1 -> return RequestFragment()
            2 -> return MenuFragment()
            3 -> return BookFragment()
        }
        return ContactFragment()
    }

}