package group_17.nightclubapp.com.checkout

import android.content.Context
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import group_17.nightclubapp.com.R
import group_17.nightclubapp.com.menu.CartItem
import group_17.nightclubapp.com.menu.MenuItem

class CheckoutArrayAdapter(private val context : Context, private val itemList : MutableList<CartItem>) : BaseAdapter() {
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
        val view = View.inflate(context, R.layout.checkout_view_item, null)

        val textName = view.findViewById<TextView>(R.id.itemName)
        val textPrice = view.findViewById<TextView>(R.id.totalItemPrice)
        val textQuantity = view.findViewById<EditText>(R.id.itemQuantity)

        textName.text = itemList[p0].itemName
        textPrice.text = "$" + (itemList[p0].itemPrice?.times(itemList[p0].itemQuantity!!)).toString()
        textQuantity.setText(itemList[p0].itemQuantity.toString())
//        Log.d("checkout", (itemList[p0].itemPrice?.times(itemList[p0].itemQuantity!!)).toString())



        return view
    }

}