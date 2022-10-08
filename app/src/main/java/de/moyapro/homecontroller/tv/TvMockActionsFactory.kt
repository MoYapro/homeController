package de.moyapro.homecontroller.tv

import de.moyapro.homecontroller.communication.tv.model.PowerStatusEnum

const val MOCK_TAG = "TV_MOCK_ACTIONS_FACTORY"
fun buildMockTvActions(
    tvStateViewModel: TvStateViewModel,
): TvActions {
    return TvActions(
        onAction = buildOnAction(tvStateViewModel),
        offAction = buildOffAction(tvStateViewModel),
        updatePowerStatus = buildUpdatePowerStatusAction(tvStateViewModel)
    )
}

fun buildUpdatePowerStatusAction(unused: TvStateViewModel): () -> Unit =
    { /* nothing to do because status is already in state */ }

fun buildOnAction(tvStateViewModel: TvStateViewModel): () -> Unit =
    { tvStateViewModel.setPowerStatus(PowerStatusEnum.ON) }


fun buildOffAction(tvStateViewModel: TvStateViewModel): () -> Unit =
    { tvStateViewModel.setPowerStatus(PowerStatusEnum.STANDBY) }

