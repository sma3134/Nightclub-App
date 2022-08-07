package group_17.nightclubapp.com.checkout

import android.content.Context
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import group_17.nightclubapp.com.R
import group_17.nightclubapp.com.menu.CartItem
import group_17.nightclubapp.com.menu.MenuItem
import group_17.nightclubapp.com.menu.MenuViewModel

class CheckoutArrayAdapter(private val mFragmentManager : FragmentActivity, private val context : Context, private val itemList : ArrayList<CartItem>) : BaseAdapter() {
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

        val menuViewModel = ViewModelProvider(mFragmentManager).get(MenuViewModel::class.java)

//        var currentQuantity = menuViewModel.totalQuantity.value

        val textName = view.findViewById<TextView>(R.id.itemName)
        val textPrice = view.findViewById<TextView>(R.id.totalItemPrice)
        val textQuantity = view.findViewById<EditText>(R.id.itemQuantity)
        val increaseQuantity = view.findViewById<Button>(R.id.increaseQuantity)
        val decreaseQuantity = view.findViewById<Button>(R.id.decreaseQuantity)

        textName.text = itemList[p0].itemName
        textPrice.text = "$" + (itemList[p0].itemPrice?.times(itemList[p0].itemQuantity!!)).toString()
        textQuantity.setText(itemList[p0].itemQuantity.toString())
        Log.d("checkout", itemList.toString())

        increaseQuantity.setOnClickListener {
            var newQuantity = Integer.parseInt(textQuantity.text.toString()).plus(1)
            var newTotal = itemList[p0].itemPrice?.times(newQuantity!!)
//            itemList[p0].itemQuantity = newQuantity
//            itemList[p0].itemPrice = newTotal
            var currentQuantity = menuViewModel.totalQuantity.value

            textQuantity.setText(newQuantity.toString())
            textPrice.text = "$" + String.format("%.2f", newTotal)


            itemList[p0].itemQuantity = newQuantity
            menuViewModel.setItemList(itemList)

            if (currentQuantity != null) {
                menuViewModel.setQuantity(currentQuantity + 1)
            }
        }

        decreaseQuantity.setOnClickListener {
            if (itemList[p0].itemQuantity!! > 1 && Integer.parseInt(textQuantity.text.toString()) > 1){
                var newQuantity = Integer.parseInt(textQuantity.text.toString()).minus(1)
                var newTotal = itemList[p0].itemPrice?.times(newQuantity!!)
                var currentQuantity = menuViewModel.totalQuantity.value


                textQuantity.setText(newQuantity.toString())
                textPrice.text = "$" + String.format("%.2f", newTotal)

                itemList[p0].itemQuantity = newQuantity

                menuViewModel.setItemList(itemList)

                if (currentQuantity != null) {
                    menuViewModel.setQuantity(currentQuantity - 1)
                }

            }

        }

        return view
    }

}