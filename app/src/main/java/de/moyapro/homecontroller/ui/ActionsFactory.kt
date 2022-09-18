package de.moyapro.homecontroller.ui

import de.moyapro.homecontroller.communication.tv.TVCommand
import de.moyapro.homecontroller.communication.tv.TVCommandEnum
import de.moyapro.homecontroller.communication.tv.model.ConnectionProperties
import de.moyapro.homecontroller.communication.tv.request

fun buildViewActions(connectionProperties: ConnectionProperties?): ViewActions {
    if (null == connectionProperties) throw IllegalStateException("must have connection properties")
    return ViewActions(offAction = buildOffAction(connectionProperties),
        onAction = buildOnAction(connectionProperties))
}

fun buildOnAction(connectionProperties: ConnectionProperties): () -> Unit =
    { request(TVCommand(TVCommandEnum.POWER, "true", connectionProperties)) }


fun buildOffAction(connectionProperties: ConnectionProperties): () -> Unit =
    { request(TVCommand(TVCommandEnum.POWER, "false", connectionProperties)) }

