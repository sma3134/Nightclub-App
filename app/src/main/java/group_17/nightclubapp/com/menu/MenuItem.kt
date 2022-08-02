package group_17.nightclubapp.com.menu


data class MenuItem(    var itemId: Long? = 0,
                        var itemName: String? = "",
                        var itemPrice: Double? = 0.0,
                        var ingredients: MutableList<String>? = mutableListOf(),
                        var imageURI: String? = ""){

//  ItemId will be hardcoded for now, but retrieved from firebase later

//  uri string needs to be converted to Uri using Uri.parse()
}