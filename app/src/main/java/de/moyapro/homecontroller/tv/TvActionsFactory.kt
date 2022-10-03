package de.moyapro.homecontroller.tv

import android.util.Log
import de.moyapro.homecontroller.communication.tv.TVCommand
import de.moyapro.homecontroller.communication.tv.TVCommandEnum
import de.moyapro.homecontroller.communication.tv.TvStatusEnum
import de.moyapro.homecontroller.communication.tv.model.ConnectionProperties
import de.moyapro.homecontroller.communication.tv.model.PowerStatusResponse
import de.moyapro.homecontroller.communication.tv.request
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

const val TAG = "TV_ACTIONS_FACTORY"
fun buildTvActions(
    connectionProperties: ConnectionProperties?,
    tvState: TvState,
): TvActions {
    if (null == connectionProperties) throw IllegalStateException("must have connection properties")
    return TvActions(
        onAction = buildOnAction(connectionProperties),
        offAction = buildOffAction(connectionProperties),
        updatePowerStatus = buildUpdatePowerStatusAction(connectionProperties, tvState)
    )
}

fun buildUpdatePowerStatusAction(
    connectionProperties: ConnectionProperties,
    tvState: TvState,
): () -> Unit = {
    request(
        TVCommand(
            TvStatusEnum.POWER_STATUS,
            connectionProperties
        )
    ) { tvResponseString ->
        val newValue = Json.decodeFromString<PowerStatusResponse>(tvResponseString)
        val newPowerStatus =
            if (Math.random() > .5) {
                if (newValue.result.firstOrNull()?.status == "active") "active" else "standby"
            } else {
                "standby"
            }


        Log.d(TAG, "new power status: $newValue from $newPowerStatus")
        tvState.setPowerStatus(newPowerStatus)
    }
}

fun buildOnAction(connectionProperties: ConnectionProperties): () -> Unit =
    { request(TVCommand(TVCommandEnum.POWER, "true", connectionProperties)) }


fun buildOffAction(connectionProperties: ConnectionProperties): () -> Unit =
    { request(TVCommand(TVCommandEnum.POWER, "false", connectionProperties)) }

