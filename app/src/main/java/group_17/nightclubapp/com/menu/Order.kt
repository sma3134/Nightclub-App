package group_17.nightclubapp.com.menu

data class Order(    val clubId : String? = "",
                     val orderId : String?="",
                     val orderQuantity : Int? = 0,
                     val orderTotal : Double? = 0.0,
//  orderlist{itemId, quantity} or {itemName, quantity}
                     val orderItems : HashMap<String, Int> = hashMapOf(),
                     var complete : Boolean? = false,
                     var orderTime : Long? = 0L) {


}