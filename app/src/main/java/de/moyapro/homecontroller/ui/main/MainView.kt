package de.moyapro.homecontroller.ui.main

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import de.moyapro.homecontroller.tv.TvActions
import de.moyapro.homecontroller.ui.MainButton
import de.moyapro.homecontroller.ui.OffButton
import de.moyapro.homecontroller.ui.SettingsButton
import de.moyapro.homecontroller.ui.controlls.hdmi.HdmiSelect
import de.moyapro.homecontroller.ui.settings.SettingsActions
import de.moyapro.homecontroller.ui.start.AppView

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun MainView(
    mainPresentationModel: State<MainPresentationModel>,
    tvActions: TvActions,
    mainActions: MainActions,
    settingsActions: SettingsActions,
) {
    Box(
        Modifier
            .fillMaxWidth()
            .background(Color.Red)
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = { topbar(mainPresentationModel, tvActions) },
            bottomBar = { bottomBar(mainPresentationModel, mainActions) },
            content = { innerPadding ->
                Column() {
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(innerPadding.calculateTopPadding())
                    )
                    Box {
                        mainContent(
                            mainPresentationModel,
                            tvActions,
                            settingsActions
                        )
                    }
                }
            },
        )
    }
}

@Composable
fun mainContent(
    mainPresentationModel: State<MainPresentationModel>,
    tvActions: TvActions,
    settingsActions: SettingsActions,
) {
   AppView(tvActions, Modifier.padding(innerPadding))
//    when (mainPresentationModel.value.view) {
//        ViewEnum.START -> StartView(tvActions = tvActions)
//        ViewEnum.CONTROLLER -> ControllerView(mainPresentationModel, tvActions)
//        ViewEnum.SETTINGS -> SettingsController(settingsActions)
//    }
}

@Composable
fun topbar(mainPresentationModel: State<MainPresentationModel>, tvActions: TvActions) {
    val showController = mainPresentationModel.value.view == ViewEnum.CONTROLLER
    Row(
        Modifier.fillMaxWidth(),
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



