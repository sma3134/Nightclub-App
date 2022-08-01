package group_17.nightclubapp.com.menu

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import group_17.nightclubapp.com.R
import group_17.nightclubapp.com.checkout.CheckoutDialogFragment
import group_17.nightclubapp.com.map.MapsActivity

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MenuFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MenuFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    private lateinit var menuAdapter: MenuArrayAdapter
    private lateinit var mListView : ListView
    private lateinit var menuItems : MutableList<MenuItem>
    private lateinit var menuViewModel: MenuViewModel
    private lateinit var quantityText : TextView
    private lateinit var imageView : ImageView
    private lateinit var checkoutButton : Button

    var currClubId : String? = ""
    var daoMenu : DAOMenu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        daoMenu = DAOMenu()
        val view = inflater.inflate(R.layout.fragment_menu, container, false)

        menuViewModel = ViewModelProvider(requireActivity()).get(MenuViewModel::class.java)

        val intent = activity?.intent
        currClubId = intent?.getStringExtra(MapsActivity.PLACE_ID_KEY)
        Log.d("Clubid", currClubId!!)

//        Log.d("mutablelive",menuViewModel.totalQuantity.toString())

        mListView = view.findViewById(R.id.listMenu)
        quantityText = view.findViewById(R.id.totalText)
        checkoutButton = view.findViewById(R.id.checkoutBtn)
        imageView = view.findViewById(R.id.cartImage)
        menuItems = mutableListOf()

        initMenu()

        menuViewModel.totalQuantity.observe(viewLifecycleOwner, Observer{ newQuantity ->
//            Log.d("mutablelive", newQuantity.toString())
            quantityText.text = newQuantity.toString()
        })

        menuViewModel.itemList.observe(viewLifecycleOwner, Observer{newList ->
            Log.d("mutablelist", newList.toString())
            Log.d("mutablelist", menuViewModel.itemList.value.toString())
        })


        checkoutButton.setOnClickListener {

            if (menuViewModel.itemList.value == null){
                val toast = Toast.makeText(requireContext(), "Add items to your cart!", Toast.LENGTH_LONG)
                toast.show()
                Log.d("toastie", "Hi")
            }else {

//                val items = menuViewModel.itemList.value
//                val items = mutableListOf<CartItem>()
//
//                for (item in menuViewModel.itemList.value!!){
//                    items.add(item)
//                }

                val items = menuViewModel.itemList.value!!

                Log.d("checkout", items.toString())

                val checkoutDialog = CheckoutDialogFragment.newInstance(clubId = currClubId!!)
                checkoutDialog.show(parentFragmentManager, "checkout")

            }


        }

        imageView.setOnClickListener {

            if (menuViewModel.itemList.value == null) {
                val toast =
                    Toast.makeText(requireContext(), "Add items to your cart!", Toast.LENGTH_LONG)
                toast.show()
            }else{
                val items = mutableListOf<CartItem>()

                for (item in menuViewModel.itemList.value!!){
                    items.add(item)
                }

                val checkoutDialog = CheckoutDialogFragment.newInstance(clubId = currClubId!!)
                checkoutDialog.show(parentFragmentManager, "checkout")
            }


        }

        return view

    }

//  Init menu using the current clubId -> fetch the menu with associated Id
    private fun initMenu(){
        daoMenu!!.getMenu(currClubId!!).addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot != null){
//                    Log.d("ItemList", snapshot.toString())

//                  Have to change the nested structure of menu to get rid of these for loops

                    for (club in snapshot.children){
                        for(item in club.children){
                            for(final in item.children){
//                                Log.d("ItemList", final.toString())
                                val item = final.getValue(MenuItem::class.java)
//                                Log.d("ItemList", item.toString())
                                if (item != null) {
                                    menuItems.add(item)
                                }
                            }
                        }
                    }
//                    Log.d("ItemList", menuItems.toString())
                    menuAdapter = MenuArrayAdapter(parentFragmentManager, requireActivity(), menuItems)
                    mListView.adapter = menuAdapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MenuFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MenuFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
