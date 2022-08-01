package group_17.nightclubapp.com.book

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import group_17.nightclubapp.com.R
import group_17.nightclubapp.com.book.model.DAOBook
import group_17.nightclubapp.com.contact.model.Club
import group_17.nightclubapp.com.map.MapsActivity
import group_17.nightclubapp.com.request.model.BookDB
import java.util.*
import kotlin.collections.ArrayList

class BookFragment : Fragment(), ValueEventListener {

    private lateinit var datePicker: DatePickerDialog

    private lateinit var nameInput: EditText
    private lateinit var emailInput: EditText
    private lateinit var phoneInput: EditText
    private lateinit var tableSpin: Spinner
    private lateinit var peopleSpin: Spinner
    private lateinit var dateBtn: Button
    private lateinit var timeBtn: Button
    private lateinit var submitBtn: Button
    private lateinit var tableList: ArrayList<Int>
    private var clubID: String? = null
    private var yearSelected = -1
    private var monthSelected = -1
    private var daySelected = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_book, container, false)
        val cal = Calendar.getInstance()
        val daoBook = DAOBook() //firebase reference

        //layout elements
        tableSpin = root.findViewById(R.id.spinner_table_book)
        peopleSpin = root.findViewById(R.id.spinner_people_book)
        nameInput = root.findViewById(R.id.input_name_book)
        emailInput = root.findViewById(R.id.input_email_book)
        phoneInput = root.findViewById(R.id.input_phone_book)
        dateBtn = root.findViewById(R.id.btn_date_select)
        timeBtn = root.findViewById(R.id.btn_time_select)
        submitBtn = root.findViewById(R.id.btn_submit_book)

        //data to be stored
        yearSelected = cal.get(Calendar.YEAR)
        monthSelected = cal.get(Calendar.MONTH)
        daySelected = cal.get(Calendar.DAY_OF_MONTH)
        var hourSelected: Int? = null
        var minuteSelected: Int? = null
        var tableSelected: Int? = null
        var peopleSelected: Int? = null

        //people spinner list [1~10]
        val peopleArray: Array<Int> = Array(10){index->index+1}
        val peopleSpinnerAdapter = ArrayAdapter(requireContext(),android.R.layout.simple_spinner_dropdown_item,peopleArray)
        peopleSpin.adapter = peopleSpinnerAdapter

        val intent = activity?.intent
        clubID = intent?.getStringExtra(MapsActivity.PLACE_ID_KEY)

        submitBtn.setOnClickListener {
            var name:String? = nameInput.text.toString()
            if(name==""){
                name = null
            }
            var email:String? = emailInput.text.toString()
            if(email==""){
                email = null
            }
            var phone:String? = phoneInput.text.toString()
            if(phone==""){
                phone = null
            }
            var date:String? = yearSelected.toString() + "-" + monthSelected.toString() + "-" + daySelected.toString()
            var time:String? = hourSelected.toString() + ":" + minuteSelected.toString()
            if(hourSelected==null || minuteSelected==null){
                time = null
            }

            val req = BookDB(name,email,phone,date,time,tableSelected,peopleSelected, clubID)
            if(req.isValid()){
                daoBook.add(req).addOnSuccessListener {
                    Toast.makeText(context, "Success, you booked a table", Toast.LENGTH_SHORT).show()
                }.addOnFailureListener {
                    Toast.makeText(context, "Failed, please try again", Toast.LENGTH_SHORT).show()
                }
            }
            else{
                Toast.makeText(requireContext(), "Please fill the blank/date/time", Toast.LENGTH_SHORT).show()
            }
        }

        //date picker dialog fragment
        dateBtn.setOnClickListener {
            val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                yearSelected = year
                monthSelected = month
                daySelected = dayOfMonth
                val data = daoBook.getBooks(clubID).get()
                data.addOnCompleteListener {
                    spinnerUpdate(it.result)
                }
            }
            datePicker = DatePickerDialog(requireActivity(), dateSetListener, yearSelected!!,monthSelected!!,daySelected!!)
            datePicker.show()
        }

        //time picker dialog fragment
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


        //get table spinner list according to the selected date
        daoBook.getBooks(clubID).addValueEventListener(this)

        tableSpin.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                tableSelected = tableList[0]
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                tableSelected = tableList[position]
            }
        }

        peopleSpin.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                peopleSelected = 1
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                peopleSelected = position+1
            }
        }
        return root
    }

    fun spinnerUpdate(snapshot: DataSnapshot){
        var cap = 0
        if(clubID=="ChIJP6CK2tZzhlQRA1kN4Xazsm8"){
            cap = Club.BARNONE_CAP
        }
        else if(clubID=="ChIJF5OUP9RzhlQRA7EQKpS-xeE"){
            cap = Club.AURA_CAP
        }
        else if(clubID=="ChIJDfEopdRzhlQRiueE4hVtcag"){
            cap = Club.CELEBRITIES_CAP
        }

        val table_array: Array<Int> = Array(cap){index->1}
        val date = yearSelected.toString() + "-" + monthSelected.toString() + "-" + daySelected.toString()

        snapshot.children.forEach {
            val req = it.getValue(BookDB::class.java)
            if (req?.clubID == clubID && req?.date == date) {
                table_array[req.table!!-1] = 0
            }
        }

        val table_Array = ArrayList<Int>()
        for (i in 0 until table_array.size){
            if(table_array[i]==1){
                table_Array.add(i+1)
            }
        }

        tableList = table_Array
        tableSpin.adapter = ArrayAdapter(requireContext(),android.R.layout.simple_spinner_dropdown_item,table_Array)
    }

    override fun onDataChange(snapshot: DataSnapshot) {
        spinnerUpdate(snapshot)
    }
    override fun onCancelled(error: DatabaseError) {
        println(error)
    }
}