package de.moyapro.homecontroller

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager

fun registerBroadcastReceiver(
    context: Context,
    receiver: BroadcastReceiver = createDummyLoggingReceiver()
): BroadcastReceiver {
    LocalBroadcastManager.getInstance(context)
        .registerReceiver(receiver, IntentFilter("de.moyapro.tv.status.power"))
    return receiver
}

fun createDummyLoggingReceiver(): BroadcastReceiver {
    return object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            Log.i("Broadcast Logging Dummy", intent.getBooleanExtra("status", false).toString())
        }
    }
}