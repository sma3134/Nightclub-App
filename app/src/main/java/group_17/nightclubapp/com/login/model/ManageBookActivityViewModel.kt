package group_17.nightclubapp.com.login.model

import androidx.cardview.widget.CardView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import group_17.nightclubapp.com.contact.model.Club
import group_17.nightclubapp.com.request.model.BookDB

class ManageBookActivityViewModel: ViewModel() {
    var bookList = MutableLiveData<ArrayList<BookItem>>()
    var count = 0
    var tableSelected = -1
    var selectedCard: CardView? = null
    fun update(snapshot: DataSnapshot, clubID: String, date: String){
        val tempList = ArrayList<BookItem>()
        var cap = 0
        count = 0
        if(clubID=="ChIJP6CK2tZzhlQRA1kN4Xazsm8"){
            cap = Club.BARNONE_CAP
        }
        else if(clubID=="ChIJF5OUP9RzhlQRA7EQKpS-xeE"){
            cap = Club.AURA_CAP
        }
        else if(clubID=="ChIJDfEopdRzhlQRiueE4hVtcag"){
            cap = Club.CELEBRITIES_CAP
        }
        for(i in 0 until cap){
            tempList.add(BookItem("","",i+1,""))
        }
        snapshot.children.forEach {
            val req = it.getValue(BookDB::class.java)
            if (req?.clubID == clubID && req?.date == date) {
                val newItem = BookItem(req.name!!, req.phone!!, req.table!!, req.pushID!!)
                //tempList.add(newItem)
                if(req.table!! == tableSelected){
                    newItem.flag = 1
                }
                tempList[req.table!!-1] = newItem
                count++
            }
        }
        bookList.value = tempList
    }
}