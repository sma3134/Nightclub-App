package group_17.nightclubapp.com.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.ActionBar
import com.google.firebase.auth.FirebaseAuth
import group_17.nightclubapp.com.R
import group_17.nightclubapp.com.contact.model.Club
import group_17.nightclubapp.com.map.MapsActivity
import group_17.nightclubapp.com.setting.SettingsActivity
import kotlin.math.log

class LogIn : AppCompatActivity() {
    private lateinit var signInBtn: Button
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var view: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        //Set up settings button on the Action bar
        supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar!!.setCustomView(R.layout.action_bar)
        val parent = supportActionBar!!.customView.parent as androidx.appcompat.widget.Toolbar
        parent.setContentInsetsAbsolute(0, 0)
        view = supportActionBar!!.customView
        val clubName = view.findViewById<TextView>(R.id.clubName)
        val settings = view.findViewById<ImageButton>(R.id.settings)
        settings.visibility = View.INVISIBLE
        clubName.text = "Log in"
        setActionBarClickListeners()

        initializeViews()
    }

    private fun initializeViews() {
        signInBtn = findViewById(R.id.signInBtn)
        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        signInBtn.setOnClickListener { logInClicked() }
    }

    private fun logInClicked() {
        val email = emailEditText.text.toString().trim()
        val password = passwordEditText.text.toString().trim()
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter password and email", Toast.LENGTH_SHORT).show()
        } else {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        val loginIntent = Intent(this, ManageActivity::class.java)
                        when (email) {
                            "auranightclub@gmail.com" -> loginIntent.putExtra(
                                MapsActivity.PLACE_ID_KEY,
                                Club.AURA
                            )
                            "celebritiesnightclub@gmail.com" -> loginIntent.putExtra(
                                MapsActivity.PLACE_ID_KEY,
                                Club.CELEBRITIES
                            )
                            "barnonenightclub@gmail.com" -> loginIntent.putExtra(
                                MapsActivity.PLACE_ID_KEY,
                                Club.BARNONE
                            )
                            else -> {
                                finish()
                                Toast.makeText(
                                    this,
                                    "Disabled Account",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                        startActivity(loginIntent)
                    } else {
                        Toast.makeText(
                            this,
                            "Email and/or password do not match",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }

    private fun setActionBarClickListeners() {
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
}