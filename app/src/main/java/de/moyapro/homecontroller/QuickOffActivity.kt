package de.moyapro.homecontroller

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import com.github.kittinunf.fuel.core.FuelError
import de.moyapro.homecontroller.tv.buildOffAction
import de.moyapro.homecontroller.ui.settings.PREFERENCES_FILE_NAME
import de.moyapro.homecontroller.ui.settings.buildConnectionPropertiesFrom


class QuickOffActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val preferences = getSharedPreferences(PREFERENCES_FILE_NAME, MODE_PRIVATE)
        val connectionProperties = buildConnectionPropertiesFrom(preferences)
        if (null == connectionProperties) quitWithMessage("Need to setup TV connection settings")
        val offAction =
            buildOffAction(connectionProperties!!, failAction = this::handleNetworkError)
        offAction()
        finish()
    }

    private fun quitWithMessage(message: String) {
        showErrorToast(message)
        finish()
    }

    private fun showErrorToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun handleNetworkError(error: FuelError) {
        quitWithMessage("""Could not connect to TV
            |$error""".trimMargin())
    }

}
