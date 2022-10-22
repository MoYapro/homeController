package de.moyapro.homecontroller.ui.main

import de.moyapro.homecontroller.communication.tv.model.PowerStatusEnum
import de.moyapro.homecontroller.tv.TvState

object MainPresenter {

    private val tag = MainPresenter::class.simpleName

    fun present(tvState: TvState, selectedView: ViewEnum): MainPresentationModel {
        val isPowerOn = PowerStatusEnum.ON == tvState.powerStatus
        return MainPresentationModel(
            view = presentView(selectedView, isPowerOn),
            volume = tvState.volume,
            hdmiStatus = tvState.hdmiStatus
        )
    }

    private fun presentView(
        selectedView: ViewEnum,
        isPowerOn: Boolean,
    ) = when {
        selectedView == ViewEnum.SETTINGS -> ViewEnum.SETTINGS
        isPowerOn -> ViewEnum.CONTROLLER
        else -> ViewEnum.START
    }

}
