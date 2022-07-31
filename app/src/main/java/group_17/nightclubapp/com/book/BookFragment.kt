package group_17.nightclubapp.com.book

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TimePicker
import androidx.lifecycle.ViewModelProvider
import group_17.nightclubapp.com.R
import group_17.nightclubapp.com.map.MapsActivity
import java.util.*

class BookFragment : Fragment() {

    private lateinit var bookViewModel: BookViewModel
    private lateinit var datePicker: DatePickerDialog

    private lateinit var nameInput: EditText
    private lateinit var emailInput: EditText
    private lateinit var phoneInput: EditText
    private lateinit var tableSpin: Spinner
    private lateinit var peopleSpin: Spinner
    private lateinit var dateBtn: Button
    private lateinit var timeBtn: Button
    private lateinit var cancelBtn: Button
    private lateinit var submitBtn: Button
    companion object {
        var table_selected = false
        var clubID: String? = null

        var yearSelected: Int? = null
        var monthSelected: Int? = null
        var daySelected: Int? = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_book, container, false)
        val cal = Calendar.getInstance()

        tableSpin = root.findViewById(R.id.spinner_table_book)
        peopleSpin = root.findViewById(R.id.spinner_people_book)
        nameInput = root.findViewById(R.id.input_name_book)
        emailInput = root.findViewById(R.id.input_email_book)
        phoneInput = root.findViewById(R.id.input_phone_book)
        dateBtn = root.findViewById(R.id.btn_date_select)
        timeBtn = root.findViewById(R.id.btn_time_select)
        cancelBtn = root.findViewById(R.id.btn_cancel_book)
        submitBtn = root.findViewById(R.id.btn_submit_book)

        yearSelected = cal.get(Calendar.YEAR)
        monthSelected = cal.get(Calendar.MONTH)
        daySelected = cal.get(Calendar.DAY_OF_MONTH)
        var hourSelected: Int? = null
        var minuteSelected: Int? = null
        var tableSelected: Int? = null
        var peopleSelected: Int? = null

        val intent = activity?.intent
        clubID = intent?.getStringExtra(MapsActivity.PLACE_ID_KEY)


        dateBtn.setOnClickListener {
            val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                yearSelected = year
                monthSelected = month
                daySelected = dayOfMonth
            }
            datePicker = DatePickerDialog(requireActivity(), dateSetListener, yearSelected!!,monthSelected!!,daySelected!!)
            datePicker.show()
        }

        timeBtn.setOnClickListener {
            val timeBuilder = AlertDialog.Builder(requireActivity())
            val timePickerView = layoutInflater.inflate(R.layout.timepicker,null)
            val timePicker = timePickerView.findViewById<TimePicker>(R.id.timep)
            if(hourSelected!=null && minuteSelected!=null){
                timePicker.hour = hourSelected!!
                timePicker.minute = minuteSelected!!
            }
            timePicker.setOnTimeChangedListener { view, hourOfDay, minute ->
                hourSelected = hourOfDay
                minuteSelected = minute
            }
            timeBuilder.setOnCancelListener {
                hourSelected = null
                minuteSelected = null
            }
            val timeSaved = DialogInterface.OnClickListener { dialog, which -> }
            val timeCanceled = DialogInterface.OnClickListener { dialog, which ->
                hourSelected = null
                minuteSelected = null
            }
            timeBuilder.setView(timePicker)
            timeBuilder.setPositiveButton("OK",timeSaved)
            timeBuilder.setNegativeButton("CANCEL",timeCanceled)
            timeBuilder.show()
        }


        bookViewModel = ViewModelProvider(this).get(BookViewModel::class.java)
        /*
        bookViewModel.get_people_spinner_list()
        bookViewModel.get_table_spinner_list()
        bookViewModel.table_spinner_adapter.observe(requireActivity()){
            tableSpin.adapter = it
        }
        bookViewModel.people_spinner_adapter.observe(requireActivity()){
            peopleSpin.adapter = it
        }
         */



        return root
    }
}