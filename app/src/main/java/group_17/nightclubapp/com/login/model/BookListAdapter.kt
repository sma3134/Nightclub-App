package group_17.nightclubapp.com.login.model

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
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

        name.text = "Name: " + item.name
        phone.text = "Phone: " + item.phone
        table.text = "Table: " + item.table.toString()

        return view
    }
}

class BookItem(private var userName: String, private var userPhone: String, private var userTable: Int){
    val name = userName
    val phone = userPhone
    val table = userTable
}