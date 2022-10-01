package de.moyapro.homecontroller.ui

import android.util.Log
import de.moyapro.homecontroller.communication.tv.TVCommand
import de.moyapro.homecontroller.communication.tv.TVCommandEnum
import de.moyapro.homecontroller.communication.tv.TvStatusEnum
import de.moyapro.homecontroller.communication.tv.model.ConnectionProperties
import de.moyapro.homecontroller.communication.tv.model.PowerStatusResponse
import de.moyapro.homecontroller.communication.tv.request
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

const val TAG = "ACTIONS"
fun buildViewActions(
    connectionProperties: ConnectionProperties?,
    viewModel: ControllerViewModel,
): ViewActions {
    if (null == connectionProperties) throw IllegalStateException("must have connection properties")
    return ViewActions(
        onAction = buildOnAction(connectionProperties),
        offAction = buildOffAction(connectionProperties),
        updatePowerStatus = buildUpdatePowerStatusAction(connectionProperties, viewModel)
    )
}

fun buildUpdatePowerStatusAction(
    connectionProperties: ConnectionProperties,
    viewModel: ControllerViewModel,
): () -> Unit = {
    request(
        TVCommand(
            TvStatusEnum.POWER_STATUS,
            connectionProperties
        )
    ) { tvResponseString ->
        val newValue = Json.decodeFromString<PowerStatusResponse>(tvResponseString)
        val x = if (newValue.result.firstOrNull()?.status == "active") "active" else "standby"
        Log.d(TAG, "new power status: $newValue from $x")
        viewModel.setPowerStatus(x)
    }
}

fun buildOnAction(connectionProperties: ConnectionProperties): () -> Unit =
    { request(TVCommand(TVCommandEnum.POWER, "true", connectionProperties)) }


fun buildOffAction(connectionProperties: ConnectionProperties): () -> Unit =
    { request(TVCommand(TVCommandEnum.POWER, "false", connectionProperties)) }

