package group_17.nightclubapp.com.login.model

import android.app.AlertDialog
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.view.marginTop
import group_17.nightclubapp.com.R
import group_17.nightclubapp.com.checkout.DAOOrders
import group_17.nightclubapp.com.menu.Order
import java.text.SimpleDateFormat
import java.util.*

class OrderListAdapter(private val context : Context, private var itemList : MutableList<Order>) : BaseAdapter() {

    private lateinit var cardView : CardView

    override fun getCount(): Int {
        return itemList.size
    }

    override fun getItem(p0: Int): Any {
        return itemList[p0]
    }

    override fun getItemId(p0: Int): Long {
        return itemList[p0].orderTime!!
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
//      need to build the card view
        val view = View.inflate(context, R.layout.activity_menu_view_item, null)
        val textOrderTime = view.findViewById<TextView>(R.id.orderTime)
        val textComplete = view.findViewById<TextView>(R.id.orderStatus)





        textComplete.text = "Order Status: Not Yet Fulfilled"
        cardView = view.findViewById(R.id.orderCardView)
        val orderTime = itemList[p0].orderTime    // Convert this shit to string
        val dateFormat = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        textOrderTime.text = "Order Placed: " + dateFormat.format(orderTime)
        for(item in itemList[p0].orderItems){
            val textView = TextView(view.context)
            val linearLayout = view.findViewById<LinearLayout>(R.id.descriptionLayout)
            val textViewParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, )

            textView.layoutParams = textViewParams
            textView.textSize= 15f

            val order = item.key + " - " + item.value.toString()

            textView.text = order

            linearLayout.addView(textView)
        }



//      Open a dialog that allows user to click complete
        view.setOnClickListener{
            val dialog = AlertDialog.Builder(context)

            dialog.apply{
                setTitle("Order Completed?")
                setNegativeButton("Complete Order") {_, _ ->
                    val order = Order(clubId = itemList[p0].clubId, orderId = itemList[p0].orderId, orderQuantity = itemList[p0].orderQuantity,
                        orderTotal = itemList[p0].orderTotal, orderItems = itemList[p0].orderItems, complete = true, orderTime = itemList[p0].orderTime  )

                    val ref = DAOOrders().update(itemList[p0].orderId!!)
                    ref.setValue(order)

                    Toast.makeText(context, "Order# " + itemList[p0].orderId + " Completed", Toast.LENGTH_LONG).show()

                }
                setNeutralButton("Cancel") {_ , _ ->

                }
            }.create().show()
        }

        return view
    }

    fun replace(newOrderList : MutableList<Order>){
        itemList = newOrderList
    }

}