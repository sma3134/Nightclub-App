package group_17.nightclubapp.com.login

import android.app.DatePickerDialog
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.ActionBar
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import group_17.nightclubapp.com.R
import group_17.nightclubapp.com.book.model.DAOBook
import group_17.nightclubapp.com.login.model.BookListAdapter
import group_17.nightclubapp.com.login.model.ManageBookActivityViewModel
import group_17.nightclubapp.com.map.MapsActivity
import group_17.nightclubapp.com.setting.SettingsActivity
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class ManageBookActivity : AppCompatActivity(), ValueEventListener {
    private lateinit var view: View
    private lateinit var bookViewModel: ManageBookActivityViewModel
    private var clubID: String = ""
    private val cal = Calendar.getInstance()

    private lateinit var bookAdapter: BookListAdapter
    private lateinit var bookListView: ListView

    private lateinit var datePicker: DatePickerDialog

    private var yearSelected = cal.get(Calendar.YEAR)
    private var monthSelected = cal.get(Calendar.MONTH)
    private var daySelected = cal.get(Calendar.DAY_OF_MONTH)

    private var date = yearSelected.toString() + "-" + monthSelected.toString() + "-" + daySelected.toString()

    private var yearSelectedTemp = -1
    private var monthSelectedTemp = -1
    private var daySelectedTemp = -1

    var dialogFgmt = -1
    val daoBook = DAOBook()

    private lateinit var menuItem: MenuItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_book)

        //Setting up action bar
        supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar!!.setCustomView(R.layout.action_bar)
        val parent = supportActionBar!!.customView.parent as androidx.appcompat.widget.Toolbar
        parent.setContentInsetsAbsolute(0, 0)
        view= supportActionBar!!.customView
        val clubName = view.findViewById<TextView>(R.id.clubName)
        val settings = view.findViewById<ImageButton>(R.id.settings)
        settings.visibility = View.INVISIBLE
        clubName.text="Book"
        setActionBarClickListeners()

        if(savedInstanceState!=null){
            dialogFgmt = savedInstanceState.getInt("dialogFgmt",-1)
            yearSelected = savedInstanceState.getInt("yearSelected",cal.get(Calendar.YEAR))
            monthSelected = savedInstanceState.getInt("monthSelected",cal.get(Calendar.MONTH))
            daySelected = savedInstanceState.getInt("daySelected",cal.get(Calendar.DAY_OF_MONTH))

            yearSelectedTemp = savedInstanceState.getInt("yearSelectedTemp",-1)
            monthSelectedTemp = savedInstanceState.getInt("monthSelectedTemp",-1)
            daySelectedTemp = savedInstanceState.getInt("daySelectedTemp",-1)
        }
        date = yearSelected.toString() + "-" + monthSelected.toString() + "-" + daySelected.toString()

        val dateSelect = findViewById<Button>(R.id.book_manage_date_selection)

        bookViewModel = ViewModelProvider(this).get(ManageBookActivityViewModel::class.java)
        clubID = intent.getStringExtra(MapsActivity.PLACE_ID_KEY)!!

        bookListView = findViewById(R.id.bookListview)
        bookAdapter = BookListAdapter(this)
        bookListView.adapter = bookAdapter

        bookViewModel.bookList.observe(this){
            bookAdapter.update(it)
            if(bookViewModel.count == 0){
                Toast.makeText(this, "Booking is empty for today", Toast.LENGTH_SHORT).show()
            }
            bookAdapter.notifyDataSetChanged()
        }


        daoBook.getBooks(clubID).addValueEventListener(this)

        dateSelect.setOnClickListener {
            val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                yearSelected = year
                monthSelected = month
                daySelected = dayOfMonth
                yearSelectedTemp = -1
                monthSelectedTemp = -1
                daySelectedTemp = -1
                date = yearSelected.toString() + "-" + monthSelected.toString() + "-" + daySelected.toString()
                bookViewModel.selectedCard = null
                bookViewModel.tableSelected = -1
                menuItem.setEnabled(false)
                menuItem.setVisible(false)
                val data = daoBook.getBooks(clubID).get()
                data.addOnCompleteListener {
                    bookViewModel.update(it.result,clubID,date)
                }

                dialogFgmt = -1
            }
            datePicker = DatePickerDialog(
                this,
                dateSetListener,
                yearSelected,
                monthSelected,
                daySelected)
            if(dialogFgmt == 0 && yearSelectedTemp!=-1 && monthSelectedTemp!=-1 && daySelectedTemp!=-1) {
                datePicker = DatePickerDialog(
                    this,
                    dateSetListener,
                    yearSelectedTemp,
                    monthSelectedTemp,
                    daySelectedTemp
                )
            }
            datePicker.datePicker.setOnDateChangedListener { view, year, monthOfYear, dayOfMonth ->
                yearSelectedTemp = year
                monthSelectedTemp = monthOfYear
                daySelectedTemp = dayOfMonth
            }
            datePicker.setOnCancelListener {
                dialogFgmt = -1
                yearSelectedTemp = -1
                monthSelectedTemp = -1
                daySelectedTemp = -1
            }
            dialogFgmt = 0
            datePicker.show()
            bookAdapter.notifyDataSetChanged()
        }

        when(dialogFgmt){
            0->{dateSelect.performClick()}
        }


        bookListView.setOnItemClickListener { parent, view, position, id ->
            val cardview = view.findViewById(R.id.bookCardview) as CardView
            val name = bookViewModel.bookList.value!![position].name
            val phone = bookViewModel.bookList.value!![position].phone
            val table = bookViewModel.bookList.value!![position].table
            val flag =  bookViewModel.bookList.value!![position].flag
            val tableSelected = bookViewModel.tableSelected
            val selectedCard = bookViewModel.selectedCard

            if(name!="" && phone!="" && flag == 0){
                bookViewModel.bookList.value!![position].flag = 1
                if(tableSelected!=-1 && selectedCard!=null){
                    bookViewModel.bookList.value!![tableSelected-1].flag = 0
                }
                bookViewModel.tableSelected = table
                bookViewModel.selectedCard = cardview
                menuItem.setEnabled(true)
                menuItem.setVisible(true)
                bookAdapter.notifyDataSetChanged()
            }
            else if(name!="" && phone!="" && flag == 1){
                bookViewModel.bookList.value!![position].flag = 0
                bookViewModel.tableSelected = -1
                bookViewModel.selectedCard = null
                menuItem.setEnabled(false)
                menuItem.setVisible(false)
                bookAdapter.notifyDataSetChanged()
            }


        }
    }

    private fun setActionBarClickListeners(){
        val intentSettings = Intent(this, SettingsActivity::class.java)
        val settingsButton = view.findViewById<ImageView>(R.id.settings)
        val backButton = view.findViewById<ImageView>(R.id.back)

        settingsButton.setOnClickListener() {
            startActivity(intentSettings)
        }
        backButton.setOnClickListener {
            finish()
        }
    }

    override fun onDataChange(snapshot: DataSnapshot) {
        bookViewModel.update(snapshot,clubID,date)
    }
    override fun onCancelled(error: DatabaseError) {
        println(error)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("dialogFgmt",dialogFgmt)
        outState.putInt("yearSelected",yearSelected)
        outState.putInt("monthSelected",monthSelected)
        outState.putInt("daySelected",daySelected)
        outState.putInt("yearSelectedTemp",yearSelectedTemp)
        outState.putInt("monthSelectedTemp",monthSelectedTemp)
        outState.putInt("daySelectedTemp",daySelectedTemp)
    }

    override fun onPause() {
        super.onPause()
        if(dialogFgmt == 0) {
            datePicker.dismiss()
        }
        daoBook.databaseReference.removeEventListener(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        menuItem = menu!!.getItem(0)
        if(bookViewModel.tableSelected==-1){
            menuItem.setVisible(false)
            menuItem.setEnabled(false)
        }
        else{
            menuItem.setVisible(true)
            menuItem.setEnabled(true)
        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        menuItem.setEnabled(false)
        menuItem.setVisible(false)
        daoBook.delBook(bookViewModel.bookList.value!![bookViewModel.tableSelected-1].ID)
        bookViewModel.selectedCard = null
        bookViewModel.tableSelected = -1
        return super.onOptionsItemSelected(item)
    }
}