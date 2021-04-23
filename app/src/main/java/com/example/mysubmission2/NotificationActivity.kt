package com.example.mysubmission2

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.mysubmission2.databinding.ActivityNotificationBinding

class NotificationActivity : AppCompatActivity() {

    private var _binding: ActivityNotificationBinding? = null
//    private val binding get() = _binding!!

    private lateinit var alaramReceiver: AlaramReceiver
    private lateinit var sharedPreferences: SharedPreferences

    companion object {
        const val SETTING_NOTIF ="SettingPref"
        private const val DAILY ="Daily"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)
        supportActionBar?.title = "Setting Notification"

        alaramReceiver = AlaramReceiver()
        sharedPreferences = getSharedPreferences(SETTING_NOTIF, Context.MODE_PRIVATE)

        // set switch
        _binding?.switchDaily?.isChecked = sharedPreferences.getBoolean(DAILY, false)
        _binding?.switchDaily?.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                alaramReceiver.setDailyAlarm(this, AlaramReceiver.DAILY_REMINDER, "Find new avorite user")
            } else {
                alaramReceiver.cancleAlarm(this)
            }
            saveChange(isChecked)
        }
    }

    private fun saveChange(checked: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(DAILY, checked)
        editor.apply()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }

}