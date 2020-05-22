package com.alarmmanagersample

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Class to initiate a Alarm after specified time which will invoke a broadcast and hide a view
 */
class MainActivity : AppCompatActivity() {
    lateinit var prefs: SharedPreferences
    lateinit var view: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE)
        view = findViewById<TextView>(R.id.view_tv)

        if (prefs.getBoolean("isTimeOut", false)) {
            view.visibility = View.GONE
        }

        // Intent to start the Broadcast Receiver
        val broadcastIntent = Intent(this, AlarmBroadcastReceiver::class.java)

        // The Pending Intent to pass in AlarmManager
        val pIntent = PendingIntent.getBroadcast(
                this,
                0,
                broadcastIntent,
                0
        )

        // Setting up AlarmManager
        val alarmMgr = getSystemService(Context.ALARM_SERVICE) as AlarmManager


        // schedule the alarm whn button is pressed
        // Button click to set the alarm
        btn_set_alarm.setOnClickListener {

            // Set an alarm to trigger 5 second after the button is pressed
            alarmMgr.set(
                    AlarmManager.RTC_WAKEUP,
                    System.currentTimeMillis() + 5000,
                    pIntent)

            // show a message that the alarm was set
            Toast.makeText(
                    this@MainActivity,
                    "Alarm was set, please wait.",
                    Toast.LENGTH_LONG
            ).show()
        }
        keepCheckingInBg()
    }

    fun keepCheckingInBg() {
        Handler().postDelayed({
            if (prefs.getBoolean("isTimeOut", false)) {
                runOnUiThread {
                    view.visibility = View.GONE
                }
            } else {
                keepCheckingInBg()
            }
        }, 20)
    }


}
