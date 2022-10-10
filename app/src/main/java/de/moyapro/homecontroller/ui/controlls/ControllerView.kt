package de.moyapro.homecontroller.ui.controlls

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.moyapro.homecontroller.communication.tv.model.HdmiStatus
import de.moyapro.homecontroller.tv.TvActions
import de.moyapro.homecontroller.ui.*
import de.moyapro.homecontroller.ui.controlls.volume.VolumeControls
import de.moyapro.homecontroller.ui.main.MainPresentationModel


@Composable
fun ControllerView(mainPresentationModel: State<MainPresentationModel>, tvActions: TvActions) {
    Column(modifier = Modifier.fillMaxSize(), Arrangement.Top) {
        VolumeControls(modifier = Modifier.fillMaxHeight(.4F),
            mainPresentationModel.value.volume,
            tvActions)
        BackHomeRow(modifier = Modifier.fillMaxHeight(.2F), tvActions)
        CenterDiamond(modifier = Modifier.fillMaxHeight(.8F), tvActions)
    }
}

@Composable
private fun BackHomeRow(modifier: Modifier, tvActions: TvActions) {
    Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
        BackButton(tvActions.backAction)
        HomeButton(tvActions.homeAction)
    }
}


@Composable
private fun CenterDiamond(modifier: Modifier, tvActions: TvActions) {
    Column(modifier = modifier
        .fillMaxWidth()
        .fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceEvenly) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(.33333F),
            horizontalArrangement = Arrangement.Center) {
            UpButton(tvActions.upAction)
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            LeftButton(tvActions.leftAction)
            OkButton(tvActions.okAction)
            RightButton(tvActions.rightAction)
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            DownButton(tvActions.downAction)
        }
    }
}


@Composable
fun HdmiSelect(hdmiStatus: List<HdmiStatus>) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        hdmiStatus
            .filter { it.uri.contains("extInput:hdmi") }
            .forEachIndexed { index, status ->
                val relativeSize: Float = (1.1F / (hdmiStatus.size - index))
                HdmiButton(Modifier
                    .padding(horizontal = 1.dp)
                    .fillMaxWidth(relativeSize),
                    status,
                    {}
                )
            }
    }
}
