package de.moyapro.homecontroller.tv

import android.util.Log
import de.moyapro.homecontroller.communication.tv.*
import de.moyapro.homecontroller.communication.tv.model.*
import de.moyapro.homecontroller.config.getConfiguredJson
import de.moyapro.homecontroller.ui.controlls.volume.VolumeConstants
import kotlinx.serialization.decodeFromString

const val TAG = "TV_ACTIONS_FACTORY"
fun buildTvActions(
    connectionProperties: ConnectionProperties?,
    tvStateViewModel: TvStateViewModel,
): TvActions {
    if (null == connectionProperties) return buildMockTvActions(tvStateViewModel)
    return TvActions(
        onAction = buildOnAction(connectionProperties),
        offAction = buildOffAction(connectionProperties),
        updatePowerStatus = buildUpdatePowerStatusAction(connectionProperties, tvStateViewModel),
        updateVolumeStatus = buildUpdateVolumeStatusAction(connectionProperties, tvStateViewModel),
        updateHdmiStatus = buildUpdateHdmiStatusAction(connectionProperties, tvStateViewModel),
        setVolume = buildSetVolumeAction(connectionProperties),
        volumeUp = buildVolumeUpAction(connectionProperties, tvStateViewModel),
        volumeDown = buildVolumeDownAction(connectionProperties, tvStateViewModel),
        backAction = buildBackAction(connectionProperties),
        leftAction = buildLeftAction(connectionProperties),
        okAction = buildOkAction(connectionProperties),
        rightAction = buildRightAction(connectionProperties),
        downAction = buildDownAction(connectionProperties),
        upAction = buildUpAction(connectionProperties),
        homeAction = buildHomeAction(connectionProperties),
        setHdmiAction = buildSetHdmiAction(connectionProperties),
    )
}

fun buildSetHdmiAction(connectionProperties: ConnectionProperties): (String) -> Unit =
    { hdmiInputUri: String ->
        request(TVCommand(TVCommandEnum.HDMI, hdmiInputUri, connectionProperties))
    }

fun buildBackAction(connectionProperties: ConnectionProperties): () -> Unit = {
    request(TVCommand(TVCommandEnum.IRCC, IRCC_CODE.RETURN.code, connectionProperties))
}

fun buildHomeAction(connectionProperties: ConnectionProperties): () -> Unit = {
    request(TVCommand(TVCommandEnum.IRCC, IRCC_CODE.HOME.code, connectionProperties))
}

fun buildUpAction(connectionProperties: ConnectionProperties): () -> Unit = {
    request(TVCommand(TVCommandEnum.IRCC, IRCC_CODE.UP.code, connectionProperties))
}

fun buildDownAction(connectionProperties: ConnectionProperties): () -> Unit = {
    request(TVCommand(TVCommandEnum.IRCC, IRCC_CODE.DOWN.code, connectionProperties))
}

fun buildRightAction(connectionProperties: ConnectionProperties): () -> Unit = {
    request(TVCommand(TVCommandEnum.IRCC, IRCC_CODE.RIGHT.code, connectionProperties))
}

fun buildOkAction(connectionProperties: ConnectionProperties): () -> Unit = {
    request(TVCommand(TVCommandEnum.IRCC, IRCC_CODE.CENTER.code, connectionProperties))
}

fun buildLeftAction(connectionProperties: ConnectionProperties): () -> Unit = {
    request(TVCommand(TVCommandEnum.IRCC, IRCC_CODE.LEFT.code, connectionProperties))
}

fun buildVolumeUpAction(
    connectionProperties: ConnectionProperties,
    tvStateViewModel: TvStateViewModel,
): () -> Unit =
    {
        if (tvStateViewModel.tvState.value.volume < VolumeConstants.MAX_VOLUME) {
            val newVolume = tvStateViewModel.tvState.value.volume + Volume(1)
            tvStateViewModel.setVolume(newVolume)
            request(TVCommand(TVCommandEnum.VOLUME,
                newVolume.value.toString(),
                connectionProperties))
        }
    }

fun buildVolumeDownAction(
    connectionProperties: ConnectionProperties,
    tvStateViewModel: TvStateViewModel,
): () -> Unit =
    {
        if (tvStateViewModel.tvState.value.volume > VolumeConstants.MIN_VOLUME) {
            val newVolume = tvStateViewModel.tvState.value.volume - Volume(1)
            tvStateViewModel.setVolume(newVolume)
            request(TVCommand(TVCommandEnum.VOLUME,
                newVolume.value.toString(),
                connectionProperties))
        }
    }

fun buildSetVolumeAction(connectionProperties: ConnectionProperties): (Volume) -> Unit =
    { volume ->
        request(TVCommand(TVCommandEnum.VOLUME, volume.value.toString(), connectionProperties))
    }


fun buildUpdatePowerStatusAction(
    connectionProperties: ConnectionProperties,
    tvStateViewModel: TvStateViewModel,
): () -> Unit = {
    request(
        TVCommand(
            TvStatusEnum.POWER_STATUS,
            connectionProperties
        ),
        failAction = { error ->
            Log.i(de.moyapro.homecontroller.communication.tv.TAG,
                "Could not connect to tv with ip ${connectionProperties.ip}")
            tvStateViewModel.setPowerStatus(PowerStatusEnum.OFF)
        }
    ) { tvResponseString ->
        try {

            val responseValue =
                getConfiguredJson().decodeFromString<PowerStatusResponse>(tvResponseString)
            val newPowerStatus = getPowerStatusValueFor(responseValue.result.first().status)
            tvStateViewModel.setPowerStatus(newPowerStatus)
        } catch (ex: java.lang.Exception) {
            Log.e(TAG, "could not parse power status. response was $tvResponseString")
        }
    }
}

fun buildUpdateHdmiStatusAction(
    connectionProperties: ConnectionProperties,
    tvStateViewModel: TvStateViewModel,
): () -> Unit = {
    request(
        TVCommand(
            TvStatusEnum.HDMI_STATUS,
            connectionProperties
        )
    ) { hdmiStatusResponseString ->
        Log.i("hdmistatus", hdmiStatusResponseString)
        val responseValue: HdmiStatusResponse =
            getConfiguredJson().decodeFromString(hdmiStatusResponseString)
        tvStateViewModel.setHdmiStatus(responseValue.result.firstOrNull() ?: emptyList())
    }
}

fun buildUpdateVolumeStatusAction(
    connectionProperties: ConnectionProperties,
    tvStateViewModel: TvStateViewModel,
): () -> Unit = {
    request(
        TVCommand(
            TvStatusEnum.VOLUME_STATUS,
            connectionProperties
        )
    ) { tvResponseString ->
        if (tvResponseString.contains("Display Is Turned off")) return@request
        val responseValue =
            getConfiguredJson().decodeFromString<VolumeInformationResponse>(tvResponseString)
        val newVolume =
            Volume(responseValue.result.first().first().volume)
        if (null != newVolume) {
            tvStateViewModel.setVolume(newVolume)
        } else {
            Log.e(de.moyapro.homecontroller.communication.tv.TAG,
                "Could not get new volume from TV but got $responseValue")
        }
    }
}

fun buildOnAction(connectionProperties: ConnectionProperties): () -> Unit =
    { request(TVCommand(TVCommandEnum.POWER, "true", connectionProperties)) }


fun buildOffAction(connectionProperties: ConnectionProperties): () -> Unit =
    { request(TVCommand(TVCommandEnum.POWER, "false", connectionProperties)) }

