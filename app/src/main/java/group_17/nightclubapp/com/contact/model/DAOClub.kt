package group_17.nightclubapp.com.contact.model

import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query

class DAOClub {
    private var databaseReference: DatabaseReference

    init {
        val firebase = FirebaseDatabase.getInstance()
        databaseReference = firebase.getReference(Club::class.java.simpleName)
    }

    fun add(club: Club): Task<Void> {
        return databaseReference.push().setValue(club)
    }

    fun getClubs(): Query {
        return databaseReference.orderByKey()
    }


}

