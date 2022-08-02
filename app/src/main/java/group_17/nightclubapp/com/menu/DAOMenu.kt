package group_17.nightclubapp.com.menu

import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import group_17.nightclubapp.com.contact.model.Club

class DAOMenu {
    private var databaseReference: DatabaseReference

    init {
        val firebase = FirebaseDatabase.getInstance()
        databaseReference = firebase.getReference(Menu::class.java.simpleName)
    }

    fun add(menu: Menu): Task<Void> {
        return databaseReference.push().setValue(menu)
    }

    fun getMenu(clubId : String): Query {
        return databaseReference.orderByChild("clubId").equalTo(clubId)
    }


}
