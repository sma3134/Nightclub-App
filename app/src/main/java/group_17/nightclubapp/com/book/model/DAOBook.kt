package group_17.nightclubapp.com.book.model

import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import group_17.nightclubapp.com.request.model.BookDB

class DAOBook {
    private var databaseReference: DatabaseReference

    init {
        val firebase = FirebaseDatabase.getInstance()
        databaseReference = firebase.getReference(BookDB::class.java.simpleName)
    }

    fun add(request: BookDB): Task<Void> {
        return databaseReference.push().setValue(request)
    }

    fun getBooks(clubID: String?): Query {
        return databaseReference.orderByChild("clubID").equalTo(clubID)
    }
}