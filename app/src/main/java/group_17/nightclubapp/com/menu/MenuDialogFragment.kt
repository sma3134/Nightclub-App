package group_17.nightclubapp.com.menu

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import group_17.nightclubapp.com.R
import java.util.concurrent.CopyOnWriteArrayList

class MenuDialogFragment : DialogFragment() {

    private lateinit var editText :EditText
    private lateinit var increaseButton : Button
    private lateinit var decreaseButton : Button
    private lateinit var totalText : TextView
    private lateinit var menuViewModel : MenuViewModel

    companion object{
        fun newInstance(itemId : Long, itemPrice : Double, itemName : String): MenuDialogFragment {
            val dialog = MenuDialogFragment()
            val args = Bundle().apply {
                putLong("id", itemId)
                putDouble("price", itemPrice)
                putString("name", itemName)
            }
            dialog.arguments = args
            return dialog
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

//      item name, item price
//      quantity -> + and - button to increase quantity or edit text to change quantity manually
//      calculate total price
//      all updated in viewmodel

        val view = requireActivity().layoutInflater.inflate(R.layout.menu_dialog, null)
        val price = arguments?.getDouble("price")
        val name = arguments?.getString("name")
        val id = arguments?.getLong("id")
        editText = view.findViewById(R.id.dialogEdit)
        increaseButton = view.findViewById(R.id.dialogIncrease)
        decreaseButton = view.findViewById(R.id.dialogDecrease)
        totalText = view.findViewById(R.id.totalText)
        totalText.text = "$" + price.toString()
//requireActivity()
        menuViewModel = ViewModelProvider(requireActivity()).get(MenuViewModel::class.java)

        var currentQuantity = menuViewModel.totalQuantity.value
        var currentAmount = menuViewModel.totalPrice.value

        var currentCart = menuViewModel.itemList.value

        if(currentAmount == null && currentQuantity == null){
            currentAmount = 0.0
            currentQuantity = 0
        }

        if (currentCart == null){
            currentCart = arrayListOf()
        }

        val iterator = currentCart.listIterator(0)

        Log.d("mutablelive",currentQuantity.toString())

        val builder = AlertDialog.Builder(requireContext())
            .setView(view)
            .setNegativeButton("Add to cart") { _, _ ->
//                do something with sharedviewmodel here

                currentQuantity = currentQuantity!! + Integer.parseInt(editText.text.toString())
                currentAmount = currentAmount!! + Integer.parseInt(editText.text.toString()) * price!!


//              TODO: java.util.ConcurrentModificationException in this iterator when adding too many items
//              WILL CRASH IF YOU TRY TO ADD 3 items lol

                var index = 0
                var newQuantity = 0


                var item: CartItem? = null

                for(cartItem in iterator) {
//                  problem
                    if(cartItem.itemName == name){
//                        cartItem.itemQuantity!!
//                        cartItem.itemQuantity = cartItem.itemQuantity!! + Integer.parseInt(editText.text.toString())
                        newQuantity = cartItem.itemQuantity!!+ Integer.parseInt(editText.text.toString())
                    }else{
                        item = CartItem(itemName = name, itemPrice = price, itemQuantity = Integer.parseInt(editText.text.toString()), itemId = id)
//                        currentCart.add(item)
                        index++
                    }

                }

                if (item != null) {
                    currentCart.add(item)
                    Log.d("checkout", "now here")
                }

                if (newQuantity != 0){
                    currentCart[index].itemQuantity = newQuantity
                }


                if(currentCart.size == 0 ){
                    val item = CartItem(itemName = name, itemPrice = price, itemQuantity = Integer.parseInt(editText.text.toString()),itemId = id)
                    currentCart.add(item)
                    Log.d("checkout", currentCart.toString())
                }


                menuViewModel.setQuantity(currentQuantity!!)
                menuViewModel.setPrice(currentAmount!!)
                menuViewModel.setItemList(currentCart)

            }
            .setNeutralButton("Cancel") { _, _ ->

            }



        increaseButton.setOnClickListener {
            editText.setText((Integer.parseInt(editText.text.toString()) + 1).toString())
        }

        decreaseButton.setOnClickListener {
            if((Integer.parseInt(editText.text.toString())) > 0){
                editText.setText((Integer.parseInt(editText.text.toString()) - 1).toString())
            }

        }

        return builder.create()
    }


}