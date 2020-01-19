package de.moyapro.homecontroller.ui.controller

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.KeyEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import de.moyapro.homecontroller.R
import de.moyapro.homecontroller.communication.tv.*
import de.moyapro.homecontroller.ui.settings.MySettingsActivity
import kotlinx.serialization.ImplicitReflectionSerializer


@Suppress("UNUSED_PARAMETER")
class ControllerActivity : AppCompatActivity() {
    private lateinit var currentFragment: Fragment
    private val powerStatusUpdateRunner = AsyncTaskRunner()

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i(this.javaClass.simpleName, "on create")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (findViewById<View>(R.id.container) != null && savedInstanceState == null) {
            currentFragment = ControllerFragment()
            supportFragmentManager.beginTransaction()
                .add(R.id.container, currentFragment)
                .commit()
        }
        Log.i(this.javaClass.simpleName, "create controller")
        powerStatusUpdateRunner.execute(this::updateTvPowerStatusCommand)
    }

    private fun updateTvPowerStatusCommand() {
        request(
            TVCommand(
                TvStatusEnum.POWER_STATUS,
                PreferenceManager.getDefaultSharedPreferences(this)
            )
        ) { tvResponseString: String ->
            this.updateStatus(tvResponseString)
        }
    }

    @UseExperimental(ImplicitReflectionSerializer::class)
    fun updateStatus(
        tvResponseString: String
    ) {
        Log.d(this.javaClass.simpleName, "Set power status to new value: $tvResponseString")
//        when (Json.parse<PowerStatusResponse>(tvResponseString).hasPower()) {
        when (Math.random() < .5) {
            true -> showControlls()
            false -> showPowerOffState()
        }
    }

    private fun showPowerOffState() {
        if (currentFragment !is MainFragment)
            replaceFragment(MainFragment())
    }

    private fun showControlls() {
        if (currentFragment !is ControllerFragment)
            replaceFragment(ControllerFragment())
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
                decreaseVolume(View(this.baseContext));
                true
            }
            KeyEvent.KEYCODE_VOLUME_UP -> {
                increaseVolume(View(this.baseContext));
                true
            }
            else -> false
        }
    }


    internal class AsyncTaskRunner : AsyncTask<() -> Unit, Unit, Unit>() {
        override fun doInBackground(vararg params: () -> Unit) {
            while (true) {
                Log.d("AsyncTaskRunner", "run ${System.currentTimeMillis()}")
                params.forEach { it() }
                Thread.sleep(250L)
            }
        }
    }

    private fun replaceFragment(newFragment: Fragment) {
        if (currentFragment.javaClass != newFragment.javaClass) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, newFragment)
                .addToBackStack(null)
                .commit()
            currentFragment = newFragment
        }
    }
}
