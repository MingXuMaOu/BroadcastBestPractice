package com.example.broadcastbestpractice

import android.app.ProgressDialog.show
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity: AppCompatActivity() {

    lateinit var receiver: ForceOfflineReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityCollector.addActivity(this)

    }

    override fun onResume() {
        super.onResume()
        val intentFilter = IntentFilter()
        intentFilter.addAction("com.example.broadcast.Force_OFFLINE")
        receiver = ForceOfflineReceiver()
        registerReceiver(receiver,intentFilter)
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(receiver)
    }

    override fun onDestroy() {
        super.onDestroy()
        ActivityCollector.removeActivity(this)
    }

    inner class ForceOfflineReceiver: BroadcastReceiver(){
        override fun onReceive(p0: Context?, p1: Intent?) {
            if (p0 != null) {
                AlertDialog.Builder(p0).apply {
                    setTitle("Waring")
                    setMessage("You are forced to be offline")
                    setCancelable(false)
                    setPositiveButton("OK") { _, _ ->
                        ActivityCollector.finishAll()
                        val i = Intent(context, LoginActivity::class.java)
                        startActivity(i)
                    }
                    show()
                }

            }
        }

    }
}