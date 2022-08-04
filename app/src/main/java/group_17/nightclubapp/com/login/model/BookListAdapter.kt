package group_17.nightclubapp.com.login.model

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.cardview.widget.CardView
import group_17.nightclubapp.com.R

class BookListAdapter(private val context: Context): BaseAdapter() {
    private var bookList = ArrayList<BookItem>()

    override fun getCount(): Int {
        return bookList.size
    }

    override fun getItem(position: Int): Any {
        return bookList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun update(newList: ArrayList<BookItem>){
        bookList = newList
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View = View.inflate(context, R.layout.book_layout_adapter_item,null)
        val name = view.findViewById<TextView>(R.id.book_name_text)
        val phone = view.findViewById<TextView>(R.id.book_phone_text)
        val table = view.findViewById<TextView>(R.id.book_table_text)
        val item = bookList[position]
        val cardview = view.findViewById(R.id.bookCardview) as CardView

        name.text = "Name: " + item.name
        phone.text = "Phone: " + item.phone
        table.text = "Table: " + item.table.toString()
        val colorBooked = context.getColor(R.color.green_card_view)
        val colorSelected = context.getColor(R.color.blue_card_view)

        if(item.name!="" && item.phone!="" && item.flag == 0) {
            cardview.setCardBackgroundColor(colorBooked)
        }
        if(item.name!="" && item.phone!="" && item.flag == 1) {
            cardview.setCardBackgroundColor(colorSelected)
        }

        return view
    }
}

class BookItem(private var userName: String, private var userPhone: String, private var userTable: Int, private var DBID: String){
    val name = userName
    val phone = userPhone
    val table = userTable
    var flag = 0
    val ID = DBID
}