package de.moyapro.homecontroller.tv

import android.util.Log
import de.moyapro.homecontroller.communication.tv.model.HdmiStatus
import de.moyapro.homecontroller.communication.tv.model.HdmiStatusResponse
import de.moyapro.homecontroller.communication.tv.model.PowerStatusEnum
import de.moyapro.homecontroller.config.getConfiguredJson
import de.moyapro.homecontroller.ui.controlls.volume.VolumeConstants
import kotlinx.serialization.decodeFromString

const val MOCK_TAG = "TV_MOCK_ACTIONS_FACTORY"
fun buildMockTvActions(
    tvStateViewModel: TvStateViewModel,
): TvActions {
    return TvActions(
        onAction = buildOnAction(tvStateViewModel),
        offAction = buildOffAction(tvStateViewModel),
        updatePowerStatus = buildUpdatePowerStatusAction(tvStateViewModel),
        setVolume = buildSetVolumeAction(tvStateViewModel),
        volumeUp = buildVolumeUpAction(tvStateViewModel),
        volumeDown = buildVolumeDownAction(tvStateViewModel),
        backAction = {},
        leftAction = {},
        okAction = {},
        rightAction = {},
        downAction = {},
        upAction = {},
        homeAction = {},
        setHdmiAction = {},
        updateVolumeStatus = {},
        updateHdmiStatus = buildUpdateHdmiStatusAction(tvStateViewModel),
        updateAppList = {},
    )
}

fun buildSetVolumeAction(tvStateViewModel: TvStateViewModel): (volume: Volume) -> Unit =
    { newVolume -> tvStateViewModel.setVolume(newVolume) }

fun buildVolumeUpAction(tvStateViewModel: TvStateViewModel): () -> Unit = {
    Log.i(TAG, "up")
    if (tvStateViewModel.tvState.value.volume < VolumeConstants.MAX_VOLUME) {
        tvStateViewModel.setVolume(tvStateViewModel.tvState.value.volume + Volume(1))
    }
}

fun buildVolumeDownAction(tvStateViewModel: TvStateViewModel): () -> Unit = {
    Log.i(TAG, "down")
    if (tvStateViewModel.tvState.value.volume > VolumeConstants.MIN_VOLUME) {
        tvStateViewModel.setVolume(tvStateViewModel.tvState.value.volume - Volume(1))
    }
}

fun buildUpdatePowerStatusAction(unused: TvStateViewModel): () -> Unit =
    { /* nothing to do because status is already in state */ }

fun buildUpdateHdmiStatusAction(unused: TvStateViewModel): () -> Unit = {
    unused.setHdmiStatus(mockHdmiData())
}

fun buildOnAction(tvStateViewModel: TvStateViewModel): () -> Unit =
    { tvStateViewModel.setPowerStatus(PowerStatusEnum.ON) }


fun buildOffAction(tvStateViewModel: TvStateViewModel): () -> Unit =
    { tvStateViewModel.setPowerStatus(PowerStatusEnum.STANDBY) }

fun mockHdmiData(): List<HdmiStatus> {
    val hdmiStatusResponse: HdmiStatusResponse = getConfiguredJson().decodeFromString("""{
    "result": [
        [
            {
                "uri": "extInput:hdmi?port=1",
                "title": "HDMI 1",
                "connection": false,
                "label": "",
                "icon": "meta:hdmi",
                "status": "false"
            },
            {
                "uri": "extInput:hdmi?port=2",
                "title": "HDMI 2",
                "connection": false,
                "label": "",
                "icon": "meta:hdmi",
                "status": "false"
            },
            {
                "uri": "extInput:hdmi?port=3",
                "title": "HDMI 3/ARC",
                "connection": false,
                "label": "",
                "icon": "meta:hdmi",
                "status": "false"
            },
            {
                "uri": "extInput:hdmi?port=4",
                "title": "HDMI 4",
                "connection": false,
                "label": "",
                "icon": "meta:hdmi",
                "status": "false"
            },
            {
                "uri": "extInput:composite?port=1",
                "title": "AV",
                "connection": false,
                "label": "",
                "icon": "meta:composite",
                "status": ""
            },
            {
                "uri": "extInput:widi?port=1",
                "title": "Bildschirm spiegeln",
                "connection": true,
                "label": "",
                "icon": "meta:wifidisplay",
                "status": ""
            }
        ]
    ],
    "id": 105
}""")
    return hdmiStatusResponse.result.first()
}
