package group_17.nightclubapp.com.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import group_17.nightclubapp.com.map.MapsActivity
import group_17.nightclubapp.com.R

class HomeFragment : Fragment() {

    private lateinit var imageView: ImageView
    private lateinit var tvTitle: TextView
    private lateinit var tvAddress: TextView
    private lateinit var tvDistance: TextView
    private lateinit var tvHour: TextView
    private lateinit var tvWebsite: TextView
    private lateinit var homeListView: ListView
    private lateinit var arrayAdapter: HomeListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        homeListView = root.findViewById(R.id.home_list)
        arrayAdapter = HomeListAdapter(requireActivity())
        homeListView.adapter = arrayAdapter

        return root
    }

}