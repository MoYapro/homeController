package com.example.homecontroller

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.homecontroller.de.moyapro.homeController.IRCC_CODE
import com.example.homecontroller.de.moyapro.homeController.TVCommand
import com.example.homecontroller.de.moyapro.homeController.TVCommandEnum
import com.example.homecontroller.de.moyapro.homeController.request
import com.example.homecontroller.ui.main.MainFragment

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


    fun increaseVolume(v: View) {
        changeVolumeRelative("+1")
    }

    fun decreaseVolume(v: View) {
        changeVolumeRelative("-1")
    }

    fun powerOff(v: View) {
        request(TVCommand(TVCommandEnum.POWER, "false"))
    }

    fun powerOn(v: View) {
        request(TVCommand(TVCommandEnum.POWER, "true"))
    }

    fun switchToHdmi4(v: View) {
        switchToHdmi(4)
    }

    private fun changeVolumeRelative(change: String) {
        request(TVCommand(TVCommandEnum.VOLUME, change))
    }

    private fun switchToHdmi(port: Int) {
        request(TVCommand(TVCommandEnum.HDMI, port.toString()))
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
