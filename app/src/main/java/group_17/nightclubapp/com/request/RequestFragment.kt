package group_17.nightclubapp.com.request

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.annotation.CheckResult
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson
import group_17.nightclubapp.com.BuildConfig
import group_17.nightclubapp.com.R
import group_17.nightclubapp.com.map.MapsActivity
import group_17.nightclubapp.com.request.model.DAORequest
import group_17.nightclubapp.com.request.model.RequestDB
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.*


class RequestFragment : Fragment(){
    private lateinit var btnSubmitSong: Button
    private lateinit var btnClearSong: Button
    private lateinit var btnSubmitAnnouncement: Button
    private lateinit var btnClearAnnouncement: Button
    private lateinit var etSong: EditText
    private lateinit var etAnnouncement: EditText
    private lateinit var songListView: ListView
    private lateinit var arrayAdapter: SongListAdapter
    private lateinit var cardView: CardView
    private lateinit var linearLayout: LinearLayout
    private var currPlaceID: String? = null
    private var flag: Boolean = false

    private val client = OkHttpClient()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_request, container, false)
        val daoRequest = DAORequest()

        btnSubmitSong = view.findViewById(R.id.btn_submit_song)
        btnClearSong = view.findViewById(R.id.btn_clear_song)
        btnSubmitAnnouncement = view.findViewById(R.id.btn_submit_announcement)
        btnClearAnnouncement = view.findViewById(R.id.btn_clear_announcement)
        etSong = view.findViewById(R.id.et_song)
        etAnnouncement = view.findViewById(R.id.et_announcement)
        songListView = view.findViewById(R.id.song_list)
        cardView = view.findViewById(R.id.cv_song)
        linearLayout = view.findViewById(R.id.ll_request)

        linearLayout.setOnClickListener {
            it.hideKeyboard()
        }

        etSong.textChanges().debounce(400)
            .onEach {
                songRequest(etSong.text.toString(), requireActivity())
            }
            .launchIn(GlobalScope)


        val intent = activity?.intent
        currPlaceID = intent?.getStringExtra(MapsActivity.PLACE_ID_KEY)

        //submit to database
        btnSubmitSong.setOnClickListener {
            if (etSong.text.toString().isNotEmpty()) {
                val clubId = currPlaceID
                val req = RequestDB(etSong.text.toString(), RequestDB.SONG_REQUEST, clubId, Calendar.getInstance().timeInMillis)
                if (req.isValid()) {
                    daoRequest.add(req).addOnSuccessListener {
                        Toast.makeText(context, "Success, Song request sent to DJ", Toast.LENGTH_SHORT).show()
                    }.addOnFailureListener {
                        Toast.makeText(context, "Failed, song request not sent to DJ", Toast.LENGTH_SHORT).show()
                    }
                }
                flag = false
                etSong.text.clear()
            } else {
                Toast.makeText(context, "Failed, song entry cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }

        btnClearSong.setOnClickListener {
            etSong.text.clear()
            flag = false
        }

        btnSubmitAnnouncement.setOnClickListener {
            if (etSong.text.toString().isNotEmpty()) {
                val clubId = currPlaceID
                val req = RequestDB(
                    etAnnouncement.text.toString(),
                    RequestDB.ANNOUNCEMENT_REQUEST,
                    clubId,
                    Calendar.getInstance().timeInMillis
                )
                if (req.isValid()) {
                    daoRequest.add(req).addOnSuccessListener {
                        Toast.makeText(
                            context,
                            "Success, Announcement request sent to DJ",
                            Toast.LENGTH_SHORT
                        ).show()
                    }.addOnFailureListener {
                        Toast.makeText(
                            context,
                            "Failed, announcement request not Sent to DJ",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                etAnnouncement.text.clear()
                flag = false
            } else {
                Toast.makeText(
                    context,
                    "Failed, announcement request cannot be empty",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        btnClearAnnouncement.setOnClickListener {
            etAnnouncement.text.clear()
            flag = false
        }

        return view
    }

    @SuppressLint("SetTextI18n")
    private fun songRequest(param: String, context: Activity) {
        if (param.isNotEmpty()) {
            val request = Request.Builder()
                .url("https://deezerdevs-deezer.p.rapidapi.com/search?q=$param")
                .get()
                .addHeader("X-RapidAPI-Key", BuildConfig.RAPID_API_KEY)
                .addHeader("X-RapidAPI-Host", "deezerdevs-deezer.p.rapidapi.com")
                .build()

            val response = client.newCall(request).execute()
            val gson = Gson()
            val songs = gson.fromJson(response.body()?.string(), SongRequest::class.java)
            val handler = Handler(Looper.getMainLooper())
            handler.post{
                arrayAdapter = SongListAdapter(context, songs.data)
                songListView.adapter = arrayAdapter
                if (flag)
                    cardView.visibility = View.GONE
                else
                    cardView.visibility = View.VISIBLE
                songListView.setOnItemClickListener() { parent: AdapterView<*>, view: View, position: Int, id: Long ->
                    flag = true
                    etSong.setText("${songs.data?.get(position)?.title} by ${songs.data?.get(position)?.artist?.name}")
                    cardView.visibility = View.GONE
                }
            }
        } else {
            val handler = Handler(Looper.getMainLooper())
            handler.post{
                cardView.visibility = View.GONE
            }
        }
    }

    @ExperimentalCoroutinesApi
    @CheckResult
    fun EditText.textChanges(): Flow<CharSequence?> {
        return callbackFlow<CharSequence?> {
            val listener = object : TextWatcher {
                override fun afterTextChanged(s: Editable?) = Unit
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    trySend(s)
                }
            }
            addTextChangedListener(listener)
            awaitClose { removeTextChangedListener(listener) }
        }.onStart { emit(text) }
    }

    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}