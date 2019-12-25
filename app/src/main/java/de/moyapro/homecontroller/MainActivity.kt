package de.moyapro.homecontroller

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import de.moyapro.homecontroller.ui.controller.ControllerActivity
import de.moyapro.homecontroller.ui.main.MainFragment


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (findViewById<View>(R.id.container) != null && savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.container, MainFragment.newInstance())
                .commit()
        }
        registerBroadcastReceiver(this);

    }

    fun powerOn(v: View) {
        val successAction =
            { _: String -> startActivity(Intent(this, ControllerActivity::class.java)) }
        request(TVCommand(TVCommandEnum.POWER, "true"), successAction)
    }

    fun checkTvPowerStatus(v: View) {
        val intent = Intent("de.moyapro.tv.status.power");
        intent.putExtra("status", true);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
}
