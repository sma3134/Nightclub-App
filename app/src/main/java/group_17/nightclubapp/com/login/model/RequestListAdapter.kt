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

        val requestLineTV = view.findViewById(R.id.dj_request_text) as TextView
        val dateLineTV = view.findViewById(R.id.dj_request_date_text) as TextView

        requestLineTV.text = requestList[position].getRequestText()
        dateLineTV.text = requestList[position].getDateText()

        return view
    }

    fun replace(newRequestList: List<RequestDB>){
        requestList = newRequestList
    }

}