package de.moyapro.homecontroller.ui.controller

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import de.moyapro.homecontroller.R
import de.moyapro.homecontroller.communication.tv.IRCC_CODE
import de.moyapro.homecontroller.communication.tv.TVCommand
import de.moyapro.homecontroller.communication.tv.TVCommandEnum
import de.moyapro.homecontroller.communication.tv.request
import de.moyapro.homecontroller.ui.settings.MySettingsActivity


@Suppress("UNUSED_PARAMETER")
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

    fun settings(v: View) {
        startActivity(Intent(this, MySettingsActivity::class.java))
    }

    fun increaseVolume(v: View) {
        changeVolumeRelative("+1")
    }

    fun decreaseVolume(v: View) {
        changeVolumeRelative("-1")
    }

    fun powerOff(v: View) {
        request(
            TVCommand(
                TVCommandEnum.POWER,
                "false",
                PreferenceManager.getDefaultSharedPreferences(this)
            )
        )
    }

    fun powerOn(v: View) {
        val successAction =
            { _: String -> startActivity(Intent(this, ControllerActivity::class.java)) }
        request(
            TVCommand(
                TVCommandEnum.POWER,
                "true",
                PreferenceManager.getDefaultSharedPreferences(this)
            ), successAction
        )
    }

    fun switchToHdmi1(v: View) {
        switchToHdmi(1)
    }

    fun switchToHdmi2(v: View) {
        switchToHdmi(2)
    }

    fun switchToHdmi3(v: View) {
        switchToHdmi(3)
    }

    fun switchToHdmi4(v: View) {
        switchToHdmi(4)
    }

    private fun changeVolumeRelative(change: String) {
        request(
            TVCommand(
                TVCommandEnum.VOLUME,
                change,
                PreferenceManager.getDefaultSharedPreferences(this)
            )
        )
    }

    private fun switchToHdmi(tvPort: Int) {
        request(
            TVCommand(
                TVCommandEnum.HDMI,
                tvPort.toString(),
                PreferenceManager.getDefaultSharedPreferences(this)
            )
        )
    }

    fun up(v: View) {
        request(
            TVCommand(
                TVCommandEnum.IRCC,
                IRCC_CODE.UP.code,
                PreferenceManager.getDefaultSharedPreferences(this)
            )
        )
    }

    fun left(v: View) {
        request(
            TVCommand(
                TVCommandEnum.IRCC,
                IRCC_CODE.LEFT.code,
                PreferenceManager.getDefaultSharedPreferences(this)
            )
        )
    }

    fun right(v: View) {
        request(
            TVCommand(
                TVCommandEnum.IRCC,
                IRCC_CODE.RIGHT.code,
                PreferenceManager.getDefaultSharedPreferences(this)
            )
        )
    }

    fun down(v: View) {
        request(
            TVCommand(
                TVCommandEnum.IRCC,
                IRCC_CODE.DOWN.code,
                PreferenceManager.getDefaultSharedPreferences(this)
            )
        )
    }

    fun center(v: View) {
        request(
            TVCommand(
                TVCommandEnum.IRCC,
                IRCC_CODE.CENTER.code,
                PreferenceManager.getDefaultSharedPreferences(this)
            )
        )
    }

    fun home(v: View) {
        request(
            TVCommand(
                TVCommandEnum.IRCC,
                IRCC_CODE.HOME.code,
                PreferenceManager.getDefaultSharedPreferences(this)
            )
        )
    }

    fun back(v: View) {
        request(
            TVCommand(
                TVCommandEnum.IRCC,
                IRCC_CODE.RETURN.code,
                PreferenceManager.getDefaultSharedPreferences(this)
            )
        )
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return when (keyCode) {
            KeyEvent.KEYCODE_VOLUME_DOWN -> {
                decreaseVolume(View(this.baseContext))
                true
            }
            KeyEvent.KEYCODE_VOLUME_UP -> {
                increaseVolume(View(this.baseContext))
                true
            }
            else -> false
        }
    }
}
