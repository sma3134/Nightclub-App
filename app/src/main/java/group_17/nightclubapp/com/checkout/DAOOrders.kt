package group_17.nightclubapp.com.checkout

import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import group_17.nightclubapp.com.menu.Order

class DAOOrders {

    private var databaseReference: DatabaseReference

    init {
        val firebase = FirebaseDatabase.getInstance()
        databaseReference = firebase.getReference(Order::class.java.simpleName)
    }

    fun add(): DatabaseReference {
//        return databaseReference.push().setValue(order)
        return databaseReference.push()
    }

    fun update(orderId : String): DatabaseReference {
        return databaseReference.child(orderId)
    }


    fun getOrders(clubId : String): Query {
//      Check if this is the right way to fetch
        return databaseReference.orderByKey()
    }
}