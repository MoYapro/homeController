package de.moyapro.homecontroller.ui.main

import de.moyapro.homecontroller.communication.tv.model.HdmiStatus
import de.moyapro.homecontroller.tv.Volume

data class MainPresentationModel(
    val view: ViewEnum,
    val volume: Volume,
    val hdmiStatus: List<HdmiStatus> = emptyList(),
)

