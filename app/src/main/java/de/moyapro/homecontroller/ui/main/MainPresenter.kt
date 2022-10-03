package de.moyapro.homecontroller.ui.main

import android.util.Log
import de.moyapro.homecontroller.communication.tv.model.PowerStatus

object MainPresenter {

    private val tag = MainPresenter::class.simpleName

    fun present(powerStatus: PowerStatus, currentView: View): MainViewState {
        Log.i(tag, "present main")
        return MainViewState(when {
            powerStatus.status != "active" -> View.START
            powerStatus.status == "active" -> View.CONTROLLER
            else -> currentView
        }
        )
    }

}
