package group_17.nightclubapp.com.request.model

import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query

class DAORequest {
    private var databaseReference: DatabaseReference

    init {
        val firebase = FirebaseDatabase.getInstance()
        databaseReference = firebase.getReference(Request::class.java.simpleName)
    }

    fun add(request: Request): Task<Void> {
        return databaseReference.push().setValue(request)
    }

    fun getRequests(): Query {
        return databaseReference.orderByKey()
    }
}