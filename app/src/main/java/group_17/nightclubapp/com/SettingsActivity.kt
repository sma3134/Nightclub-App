package group_17.nightclubapp.com

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceScreen

lateinit var sharedPreference:SharedPreferences

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.settings, SettingsFragment())
                .commit()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        sharedPreference = this.getSharedPreferences("SETTING", MODE_PRIVATE)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    class SettingsFragment : PreferenceFragmentCompat() {
        private lateinit var preferenceMenu: PreferenceScreen
        var counter = 0
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)
            var showManage = sharedPreference.getBoolean("MANAGE", false)
            val editor = sharedPreference.edit()
            preferenceMenu = preferenceScreen
            val version = preferenceMenu.findPreference<Preference>("version")
            preferenceMenu.findPreference<Preference>("manage")?.isVisible= showManage

            version!!.onPreferenceClickListener = Preference.OnPreferenceClickListener {
                if (counter < 6) {
                    counter += 1
                } else {
                    showManage=!showManage
                    editor.putBoolean("MANAGE", showManage)
                    editor.apply()
                    preferenceMenu.findPreference<Preference>("manage")?.isVisible= showManage
                    counter=0
                }
                true
            }
        }

    }
}

