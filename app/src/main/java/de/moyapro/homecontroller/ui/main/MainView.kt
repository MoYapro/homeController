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
import de.moyapro.homecontroller.ui.controller.HdmiSelect
import de.moyapro.homecontroller.ui.start.StartView

@Composable
fun MainView(mainViewState: State<MainViewState>, tvActions: TvActions, mainActions: MainActions) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { topbar(mainViewState, tvActions) },
        bottomBar = { bottomBar(mainViewState, mainActions) },
        content = { innerPadding -> mainContent(innerPadding, mainViewState, tvActions) },
    )
}

@Composable
fun mainContent(
    innerPadding: PaddingValues,
    mainViewState: State<MainViewState>,
    tvActions: TvActions,
) {
    when (mainViewState.value.view) {
        View.START -> StartView(tvActions = tvActions, Modifier.padding(innerPadding))
        else -> Text(text = "no view selected ${mainViewState.value.view}")
    }
}

@Composable
fun topbar(mainViewState: State<MainViewState>, tvActions: TvActions) {
    val isController = mainViewState.value.view == View.CONTROLLER
    Row(Modifier.fillMaxWidth(),
        horizontalArrangement = if (isController) Arrangement.SpaceEvenly else Arrangement.Start
    ) {
        OffButton(offAction = tvActions.offAction, Modifier.padding(horizontal = 1.dp))
        Row(Modifier.animateContentSize()) {
            if (isController) {
                HdmiSelect()
            }
        }
    }
}

@Composable
fun bottomBar(mainViewState: State<MainViewState>, mainActions: MainActions) {
    if (mainViewState.value.view == View.SETTINGS) {
        MainButton(Modifier.fillMaxWidth(), mainActions.openStart)
    } else {
        SettingsButton(Modifier.fillMaxWidth(), mainActions.openSettings)
    }
}



