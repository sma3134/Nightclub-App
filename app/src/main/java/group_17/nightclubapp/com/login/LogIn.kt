package group_17.nightclubapp.com.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import group_17.nightclubapp.com.R

class LogIn : AppCompatActivity() {
    private lateinit var signInBtn: Button
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)
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
                        Toast.makeText(this, "launch new activity here", Toast.LENGTH_SHORT).show()
                        //launch new activity with email so we know which club to show
                    }
                    else {
                        Toast.makeText(this, "Email and/or password do not match", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}