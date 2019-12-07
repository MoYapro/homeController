package de.moyapro.homecontroller

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import de.moyapro.homecontroller.de.moyapro.homeController.TVCommand
import de.moyapro.homecontroller.de.moyapro.homeController.TVCommandEnum
import de.moyapro.homecontroller.de.moyapro.homeController.request
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
    }

    fun powerOn(v: View) {
        val successAction =
            { _: String -> startActivity(Intent(this, ControllerActivity::class.java)) }
        request(TVCommand(TVCommandEnum.POWER, "true"), successAction)
    }
}
