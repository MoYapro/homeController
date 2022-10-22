package de.moyapro.homecontroller.ui.main

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.moyapro.homecontroller.tv.TvActions
import de.moyapro.homecontroller.ui.MainButton
import de.moyapro.homecontroller.ui.OffButton
import de.moyapro.homecontroller.ui.SettingsButton
import de.moyapro.homecontroller.ui.controlls.ControllerView
import de.moyapro.homecontroller.ui.controlls.hdmi.HdmiSelect
import de.moyapro.homecontroller.ui.settings.SettingsActions
import de.moyapro.homecontroller.ui.settings.SettingsController
import de.moyapro.homecontroller.ui.start.StartView

@Composable
fun MainView(mainPresentationModel: State<MainPresentationModel>, tvActions: TvActions, mainActions: MainActions, settingsActions: SettingsActions) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { topbar(mainPresentationModel, tvActions) },
        bottomBar = { bottomBar(mainPresentationModel, mainActions) },
        content = { innerPadding -> mainContent(innerPadding, mainPresentationModel, tvActions, settingsActions) },
    )
}

@Composable
fun mainContent(
    innerPadding: PaddingValues,
    mainPresentationModel: State<MainPresentationModel>,
    tvActions: TvActions,
    settingsActions: SettingsActions,
) {
    when (mainPresentationModel.value.view) {
        ViewEnum.START -> StartView(tvActions = tvActions, Modifier.padding(innerPadding))
        ViewEnum.CONTROLLER -> ControllerView(mainPresentationModel, tvActions )
        ViewEnum.SETTINGS -> SettingsController(settingsActions)
    }
}

@Composable
fun topbar(mainPresentationModel: State<MainPresentationModel>, tvActions: TvActions) {
    val showController = mainPresentationModel.value.view == ViewEnum.CONTROLLER
    Row(Modifier.fillMaxWidth(),
        horizontalArrangement = if (showController) Arrangement.SpaceBetween else Arrangement.SpaceBetween,
    ) {
        Row(modifier = Modifier.fillMaxWidth(.01F)) {}
        OffButton(offAction = tvActions.offAction, Modifier.padding(horizontal = 1.dp))
        Row(Modifier.animateContentSize()) {
            if (showController) {
                HdmiSelect(mainPresentationModel.value.hdmiStatus, tvActions.setHdmiAction)
            }
        }
    }
}

@Composable
fun bottomBar(mainPresentationModel: State<MainPresentationModel>, mainActions: MainActions) {
    if (mainPresentationModel.value.view == ViewEnum.SETTINGS) {
        MainButton(Modifier.fillMaxWidth(), mainActions.openStart)
    } else {
        SettingsButton(Modifier.fillMaxWidth(), mainActions.openSettings)
    }
}



