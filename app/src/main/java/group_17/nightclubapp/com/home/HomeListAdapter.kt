package group_17.nightclubapp.com.home

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import group_17.nightclubapp.com.R

class HomeListAdapter(private val context: Context): BaseAdapter() {
    override fun getCount(): Int {
        TODO("Not yet implemented")
    }

    override fun getItem(position: Int): Any {
        TODO("Not yet implemented")
    }

    override fun getItemId(position: Int): Long {
        TODO("Not yet implemented")
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = View.inflate(context, R.layout.homelistview_layout, null)

        return view
    }
}