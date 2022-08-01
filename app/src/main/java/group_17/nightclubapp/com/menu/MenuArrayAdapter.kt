package group_17.nightclubapp.com.menu

import android.content.Context
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import group_17.nightclubapp.com.R

class MenuArrayAdapter(private val mFragmentManager : FragmentManager, private val context : Context, private val itemList : List<MenuItem>) : BaseAdapter() {
    override fun getCount(): Int {
        return itemList.size
    }

    override fun getItem(p0: Int): Any {
        return itemList[p0]
    }

    override fun getItemId(p0: Int): Long {
        return itemList[p0].itemId!!
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val view = View.inflate(context, R.layout.menu_view_item, null)

        val textName = view.findViewById<TextView>(R.id.itemName)
        val textPrice = view.findViewById<TextView>(R.id.itemPrice)
        val textDescription = view.findViewById<TextView>(R.id.itemDescription)
        val imageView = view.findViewById<ImageView>(R.id.itemImage)

//        Log.d("ItemAdapter", itemList[p0].toString())

        textName.text = itemList[p0].itemName
        textPrice.text = "$" + itemList[p0].itemPrice.toString()
        textDescription.text = itemList[p0].ingredients!!.joinToString(",")

        view.setOnClickListener{
            val dialog = MenuDialogFragment.newInstance(itemId=itemList[p0].itemId!!, itemPrice=itemList[p0].itemPrice!!, itemName=itemList[p0].itemName!!)
            dialog.show(mFragmentManager, "dialog")
        }

        return view
    }
}