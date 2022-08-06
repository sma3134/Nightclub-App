package group_17.nightclubapp.com.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import group_17.nightclubapp.com.R
import group_17.nightclubapp.com.checkout.DAOOrders
import group_17.nightclubapp.com.login.model.ManageMenuActivityViewModel
import group_17.nightclubapp.com.login.model.OrderListAdapter
import group_17.nightclubapp.com.map.MapsActivity
import group_17.nightclubapp.com.menu.Order
import group_17.nightclubapp.com.setting.SettingsActivity

class ManageMenuActivity : AppCompatActivity(){
    private lateinit var view: View
    private lateinit var daoOrders: DAOOrders
    private lateinit var orderViewModel: ManageMenuActivityViewModel
    private lateinit var orderAdapter : OrderListAdapter
    private lateinit var listView : ListView

    var orderList : MutableList<Order> = mutableListOf()


    var clubId : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_menu)

        //Setting up action bar
        supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar!!.setCustomView(R.layout.action_bar)
        val parent = supportActionBar!!.customView.parent as androidx.appcompat.widget.Toolbar
        parent.setContentInsetsAbsolute(0, 0)
        view= supportActionBar!!.customView
        val clubName = view.findViewById<TextView>(R.id.clubName)
        val settings = view.findViewById<ImageButton>(R.id.settings)
        settings.visibility = View.INVISIBLE
        clubName.text="Orders"
        setActionBarClickListeners()

        clubId = intent.getStringExtra(MapsActivity.PLACE_ID_KEY)!!

        listView = findViewById(R.id.orderList)
        orderViewModel = ViewModelProvider(this).get(ManageMenuActivityViewModel::class.java)
        daoOrders = DAOOrders()
        orderAdapter = OrderListAdapter(this, orderList)
        listView.adapter = orderAdapter


        if (clubId != null) {
            daoOrders.getOrders(clubId).addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    orderList.clear()
                    for (child in snapshot.children) {
                        val order = child.getValue(Order::class.java)


                        if (order != null && order.clubId == clubId && order.complete == false){
                            orderList.add(order)
//                            Log.d("Orders", order.toString())
                        }
                    }
                    orderViewModel.setOrderList(orderList)


                }

                override fun onCancelled(error: DatabaseError) {

                }

            })
        }



        orderViewModel.orderList.observe(this, Observer{ newOrders ->
            orderAdapter.notifyDataSetChanged()
            orderAdapter.replace(newOrders)

        })

    }

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