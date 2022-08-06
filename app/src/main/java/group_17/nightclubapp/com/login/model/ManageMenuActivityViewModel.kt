package group_17.nightclubapp.com.login.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import group_17.nightclubapp.com.menu.CartItem
import group_17.nightclubapp.com.menu.Order

class ManageMenuActivityViewModel : ViewModel(){

//    private val _orderList = MutableLiveData<MutableList<Order>>()
//    val orderList = _orderList
//
//    fun setOrderList(newOrder : MutableList<Order>){
//        _orderList.value = newOrder
//    }

    private val _orderList = MutableLiveData<MutableList<Order>>()
    val orderList = _orderList

    fun setOrderList(newOrder : MutableList<Order>){
        _orderList.value = newOrder
    }

}