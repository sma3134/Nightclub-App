package group_17.nightclubapp.com.login.model

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import group_17.nightclubapp.com.R
import group_17.nightclubapp.com.request.model.RequestDB

class RequestListAdapter(private val context: Context, private var requestList: List<RequestDB>) : BaseAdapter(){

    override fun getItem(position: Int): Any {
        return requestList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return requestList.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = View.inflate(context, R.layout.request_layout_adapter_item,null)

        val firstLineTv = view.findViewById(R.id.first_line_text) as TextView
        val secondLineTv = view.findViewById(R.id.second_line_text) as TextView

        firstLineTv.text = requestList[position].request
        secondLineTv.text = requestList[position].date.toString()

        return view
    }

    fun replace(newRequestList: List<RequestDB>){
        requestList = newRequestList
    }

}