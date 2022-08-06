package group_17.nightclubapp.com.checkout

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import group_17.nightclubapp.com.R
import group_17.nightclubapp.com.menu.CartItem
import group_17.nightclubapp.com.menu.MenuDialogFragment
import group_17.nightclubapp.com.menu.MenuViewModel
import group_17.nightclubapp.com.menu.Order
import java.util.*

class CheckoutDialogFragment : DialogFragment() {

    private lateinit var checkoutList : ListView
    private lateinit var checkoutListAdapter : CheckoutArrayAdapter
    private lateinit var menuViewModel : MenuViewModel
    private lateinit var daoOrders: DAOOrders
    lateinit var itemList : MutableList<CartItem>
    var clubId : String? = ""

    companion object{
        fun newInstance(clubId : String): CheckoutDialogFragment {
            val dialog = CheckoutDialogFragment()
            val args = Bundle().apply {
                putString("clubId", clubId)
            }
            dialog.arguments = args
            return dialog
        }
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val view = requireActivity().layoutInflater.inflate(R.layout.checkout_dialog, null)

        clubId = arguments?.getString("clubId")

        menuViewModel = ViewModelProvider(requireActivity()).get(MenuViewModel::class.java)
        daoOrders = DAOOrders()

        itemList = mutableListOf()
        itemList = menuViewModel.itemList.value!!

        checkoutList = view.findViewById(R.id.checkoutList)

        checkoutListAdapter = CheckoutArrayAdapter(requireActivity(), requireActivity(), itemList)
        checkoutList.adapter = checkoutListAdapter

        checkoutList = view.findViewById(R.id.checkoutList)

        if (clubId != null) {
            Log.d("checkout", clubId!!)
        }

        val builder = AlertDialog.Builder(requireContext()).setView(view)
            .setNegativeButton("Checkout"){_,_ ->

                createOrder()

            }
            .setNeutralButton("Cancel"){_, _ ->}

        return builder.create()
    }

//  [itemId, itemId]
//  itemId -> {quantity: Int, total: Double}

//  for now make it so that we only have total quantity and price

    fun createOrder(){
        val currentItems = hashMapOf<String, Int>()

        for (item in itemList){
            currentItems[item.itemName!!] = item.itemQuantity!!
        }

        menuViewModel.setQuantity(0)
        menuViewModel.setItemList(arrayListOf())
        menuViewModel.setPrice(0.0)

        val ref = daoOrders.add()
        val orderId = ref.key

        val order = Order(clubId = clubId, orderId = orderId, orderQuantity = menuViewModel.totalQuantity.value,
            orderTotal = menuViewModel.totalPrice.value, orderItems = currentItems, complete = false, orderTime = Calendar.getInstance().timeInMillis  )

        Toast.makeText(requireActivity(), "Your Order ID: $orderId", Toast.LENGTH_LONG).show()

        ref.setValue(order)

    }

}
