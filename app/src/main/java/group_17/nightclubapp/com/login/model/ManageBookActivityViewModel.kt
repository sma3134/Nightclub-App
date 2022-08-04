package group_17.nightclubapp.com.login.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import group_17.nightclubapp.com.request.model.BookDB

class ManageBookActivityViewModel: ViewModel() {
    var bookList = MutableLiveData<ArrayList<BookItem>>()
    fun update(snapshot: DataSnapshot, clubID: String, date: String){
        val tempList = ArrayList<BookItem>()
        snapshot.children.forEach {
            val req = it.getValue(BookDB::class.java)
            if (req?.clubID == clubID && req?.date == date) {
                val newItem = BookItem(req.name!!, req.phone!!, req.table!!)
                tempList.add(newItem)
            }
        }
        bookList.value = tempList
    }
}