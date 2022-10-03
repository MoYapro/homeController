package de.moyapro.homecontroller.ui.main

import de.moyapro.homecontroller.communication.tv.model.PowerStatus

object MainPresenter {

    private val tag = MainPresenter::class.simpleName

    fun present(powerStatus: PowerStatus, selectedView: View): MainViewState {
        val isPowerOn = powerStatus.status == "active"
        return MainViewState(
            view = when {
            selectedView == View.SETTINGS -> View.SETTINGS
            isPowerOn -> View.CONTROLLER
            else -> View.START
        }
        )
    }

}
