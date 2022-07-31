package group_17.nightclubapp.com.book

import android.app.Application
import android.widget.ArrayAdapter
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import group_17.nightclubapp.com.R
import group_17.nightclubapp.com.book.model.DAOBook
import group_17.nightclubapp.com.contact.model.Club

class BookViewModel(application: Application) : AndroidViewModel(application) {

    val daoBook = DAOBook()

    lateinit var table_spinner_adapter: LiveData<ArrayAdapter<Int>>
    lateinit var people_spinner_adapter: LiveData<ArrayAdapter<Int>>
    val context = getApplication<Application>().applicationContext
    var cap = 0

    fun onDataChange(snapshot: DataSnapshot) {

    }

    fun get_table_spinner_list(){
        if(BookFragment.clubID=="ChIJP6CK2tZzhlQRA1kN4Xazsm8"){
            cap = Club.BARNONE_CAP
        }
        else if(BookFragment.clubID=="ChIJF5OUP9RzhlQRA7EQKpS-xeE"){
            cap = Club.AURA_CAP
        }
        else if(BookFragment.clubID=="ChIJDfEopdRzhlQRiueE4hVtcag"){
            cap = Club.CELEBRITIES_CAP
        }
        val date = BookFragment.yearSelected.toString() + ":" + BookFragment.monthSelected.toString() + ":" + BookFragment.daySelected.toString()
        daoBook.getBooks(BookFragment.clubID).addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach {

                }
            }

            override fun onCancelled(error: DatabaseError) {
                println(error)
            }
        })
    }
    /*

    fun get_table_spinner_list(){
        if(BookFragment.date_selected==false){
            val _data = MutableLiveData<ArrayAdapter<String>>().apply{
                value = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item,date_not_selected)
            }
            table_spinner_adapter = _data
        }
    }

    fun get_people_spinner_list(){
        if(BookFragment.table_selected==false){
            val _data = MutableLiveData<ArrayAdapter<String>>().apply{
                value = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item,table_not_selected)
            }
            people_spinner_adapter = _data
        }
    }
     */
}