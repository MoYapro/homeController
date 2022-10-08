package de.moyapro.homecontroller.ui.main

import de.moyapro.homecontroller.communication.tv.model.PowerStatusEnum
import de.moyapro.homecontroller.tv.TvState

object MainPresenter {

    private val tag = MainPresenter::class.simpleName

    fun present(tvState: TvState, selectedView: View): MainPresentationModel {
        val isPowerOn = PowerStatusEnum.ON == tvState.powerStatus
        return MainPresentationModel(
            view = when {
                selectedView == View.SETTINGS -> View.SETTINGS
                isPowerOn -> View.CONTROLLER
                else -> View.START
            }
        )
    }

}
