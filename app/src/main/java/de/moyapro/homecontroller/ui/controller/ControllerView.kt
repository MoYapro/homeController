package de.moyapro.homecontroller.ui.controller

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.moyapro.homecontroller.tv.TvActions
import de.moyapro.homecontroller.ui.*
import de.moyapro.homecontroller.ui.settings.SettingsRow


@Composable
fun ControllerView(viewModel: ControllerViewModel, actions: TvActions) {
    Column(modifier = Modifier.fillMaxSize(), Arrangement.Top) {
        TopRow(modifier = Modifier.fillMaxHeight(.17F), actions)
        CenterDiamond(modifier = Modifier.fillMaxHeight(.5F))
        BackHomeRow(modifier = Modifier.fillMaxHeight(.2F))
        VolumeControls(modifier = Modifier.fillMaxHeight(.4F), viewModel)
    }
}

@Composable
private fun TopRow(modifier: Modifier, actions: TvActions) {
    Row(modifier = modifier.fillMaxSize(), horizontalArrangement = Arrangement.SpaceBetween) {
        OffButton(actions.onAction)
        HdmiSelect()
    }
}


@Composable
private fun BackHomeRow(modifier: Modifier) {
    Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
        BackButton()
        HomeButton()
    }
}


@Composable
private fun VolumeControls(modifier: Modifier, viewModel: ControllerViewModel) {
    Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
        VolumeDownButton()
        VolumeUpButton()
    }
}


@Composable
private fun CenterDiamond(modifier: Modifier) {
    Column(modifier = modifier
        .fillMaxWidth()
        .fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceEvenly) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(.33333F),
            horizontalArrangement = Arrangement.Center) {
            UpButton()
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            LeftButton()
            OkButton()
            RightButton()
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            DownButton()
        }
    }
}


@Composable
fun HdmiSelect() {
    Hdmi1Button(Modifier.padding(horizontal = 1.dp).fillMaxWidth(.25F))
    Hdmi2Button(Modifier.padding(horizontal = 1.dp).fillMaxWidth(.33333F))
    Hdmi3Button(Modifier.padding(horizontal = 1.dp).fillMaxWidth(.5F))
    Hdmi4Button(Modifier.padding(horizontal = 1.dp).fillMaxWidth())
}
