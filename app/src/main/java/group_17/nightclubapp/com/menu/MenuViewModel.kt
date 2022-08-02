package group_17.nightclubapp.com.menu

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.io.Serializable

class MenuViewModel : ViewModel() {

    private val _totalQuantity = MutableLiveData<Int>()
    val totalQuantity = _totalQuantity

    private val _totalPrice = MutableLiveData<Double>()
    val totalPrice = _totalPrice

//  problematic -> make it a map
//    MutableList
    private val _itemList = MutableLiveData<MutableList<CartItem>>()
    val itemList = _itemList

    fun setPrice(price : Double){
        _totalPrice.value = price
    }

    fun setQuantity(quantity : Int){
        _totalQuantity.value = quantity
    }
// MutableList
    fun setItemList(newList : MutableList<CartItem>){
        _itemList.value = newList
    }





}