package de.moyapro.homecontroller.ui.main

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.moyapro.homecontroller.tv.TvActions
import de.moyapro.homecontroller.ui.MainButton
import de.moyapro.homecontroller.ui.OffButton
import de.moyapro.homecontroller.ui.SettingsButton
import de.moyapro.homecontroller.ui.controlls.ControllerView
import de.moyapro.homecontroller.ui.controlls.HdmiSelect
import de.moyapro.homecontroller.ui.start.StartView

@Composable
fun MainView(mainPresentationModel: State<MainPresentationModel>, tvActions: TvActions, mainActions: MainActions) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { topbar(mainPresentationModel, tvActions) },
        bottomBar = { bottomBar(mainPresentationModel, mainActions) },
        content = { innerPadding -> mainContent(innerPadding, mainPresentationModel, tvActions) },
    )
}

@Composable
fun mainContent(
    innerPadding: PaddingValues,
    mainPresentationModel: State<MainPresentationModel>,
    tvActions: TvActions,
) {
    when (mainPresentationModel.value.view) {
        ViewEnum.START -> StartView(tvActions = tvActions, Modifier.padding(innerPadding))
        ViewEnum.CONTROLLER -> ControllerView(mainPresentationModel, tvActions )
        else -> Text(text = "no view selected ${mainPresentationModel.value.view}")
    }
}

@Composable
fun topbar(mainPresentationModel: State<MainPresentationModel>, tvActions: TvActions) {
    val isController = mainPresentationModel.value.view == ViewEnum.CONTROLLER
    Row(Modifier.fillMaxWidth(),
        horizontalArrangement = if (isController) Arrangement.SpaceBetween else Arrangement.Start,
    ) {
        OffButton(offAction = tvActions.offAction, Modifier.padding(horizontal = 1.dp))
        Row(Modifier.animateContentSize()) {
            if (isController) {
                HdmiSelect(mainPresentationModel.value.hdmiStatus)
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



