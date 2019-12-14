package de.moyapro.homecontroller.ui.controller

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import de.moyapro.homecontroller.IRCC_CODE
import de.moyapro.homecontroller.R
import de.moyapro.homecontroller.de.moyapro.homeController.TVCommand
import de.moyapro.homecontroller.de.moyapro.homeController.TVCommandEnum
import de.moyapro.homecontroller.de.moyapro.homeController.request


class ControllerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (findViewById<View>(R.id.container) != null && savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.container, ControllerFragment.newInstance())
                .commit()
        }
    }


    fun increaseVolume(v: View) {
        changeVolumeRelative("+1")
    }

    fun decreaseVolume(v: View) {
        changeVolumeRelative("-1")
    }

    fun powerOff(v: View) {
        val successAction = { _: String -> finish() }
        request(TVCommand(TVCommandEnum.POWER, "false"), successAction)
    }

    fun powerOn(v: View) {
        val successAction =
            { _: String -> startActivity(Intent(this, ControllerActivity::class.java)) }
        request(TVCommand(TVCommandEnum.POWER, "true"), successAction)
    }

    fun switchToHdmi4(v: View) {
        switchToHdmi(4)
    }

    private fun changeVolumeRelative(change: String) {
        request(TVCommand(TVCommandEnum.VOLUME, change))
    }

    private fun switchToHdmi(tvPort: Int) {
        request(TVCommand(TVCommandEnum.HDMI, tvPort.toString()))
    }

    fun up(v: View) {
        request(TVCommand(TVCommandEnum.IRCC, IRCC_CODE.UP.code))
    }

    fun left(v: View) {
        request(TVCommand(TVCommandEnum.IRCC, IRCC_CODE.LEFT.code))
    }

    fun right(v: View) {
        request(TVCommand(TVCommandEnum.IRCC, IRCC_CODE.RIGHT.code))
    }

    fun down(v: View) {
        request(TVCommand(TVCommandEnum.IRCC, IRCC_CODE.DOWN.code))
    }

    fun center(v: View) {
        request(TVCommand(TVCommandEnum.IRCC, IRCC_CODE.CENTER.code))
    }

}