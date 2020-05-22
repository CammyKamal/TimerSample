package com.alarmmanagersample

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.widget.Toast


class AlarmBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val sharedPreferences: SharedPreferences = context!!.getSharedPreferences("MyData",Context.MODE_PRIVATE)
        sharedPreferences.edit().putBoolean("isTimeOut",true).apply()
        Toast.makeText(context,"Alram receiverd",Toast.LENGTH_LONG).show()
    }
}