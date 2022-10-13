package de.moyapro.homecontroller.tv

import de.moyapro.homecontroller.communication.tv.TVCommand
import de.moyapro.homecontroller.communication.tv.TVCommandEnum
import de.moyapro.homecontroller.communication.tv.TvStatusEnum
import de.moyapro.homecontroller.communication.tv.model.ConnectionProperties
import de.moyapro.homecontroller.communication.tv.model.PowerStatusResponse
import de.moyapro.homecontroller.communication.tv.model.getPowerStatusValueFor
import de.moyapro.homecontroller.communication.tv.request
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

const val TAG = "TV_ACTIONS_FACTORY"
fun buildTvActions(
    connectionProperties: ConnectionProperties?,
    tvStateViewModel: TvStateViewModel,
): TvActions {
    if (null == connectionProperties) throw IllegalStateException("must have connection properties")
    return TvActions(
        onAction = buildOnAction(connectionProperties),
        offAction = buildOffAction(connectionProperties),
        updatePowerStatus = buildUpdatePowerStatusAction(connectionProperties, tvStateViewModel),
        setVolume = {},
        volumeUp = {},
        volumeDown = {},
        backAction = {},
        leftAction = {},
        okAction = {},
        rightAction = {},
        downAction = {},
        upAction = {},
        homeAction = {},
        setHdmiAction = {},
    )
}

fun buildUpdatePowerStatusAction(
    connectionProperties: ConnectionProperties,
    tvStateViewModel: TvStateViewModel,
): () -> Unit = {
    request(
        TVCommand(
            TvStatusEnum.POWER_STATUS,
            connectionProperties
        )
    ) { tvResponseString ->
        val responseValue = Json.decodeFromString<PowerStatusResponse>(tvResponseString)
        val newPowerStatus = getPowerStatusValueFor(responseValue.result.first().status)
        tvStateViewModel.setPowerStatus(newPowerStatus)
    }
}

fun buildOnAction(connectionProperties: ConnectionProperties): () -> Unit =
    { request(TVCommand(TVCommandEnum.POWER, "true", connectionProperties)) }


fun buildOffAction(connectionProperties: ConnectionProperties): () -> Unit =
    { request(TVCommand(TVCommandEnum.POWER, "false", connectionProperties)) }

