package de.moyapro.homecontroller.ui.controlls

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.moyapro.homecontroller.tv.TvActions
import de.moyapro.homecontroller.ui.*
import de.moyapro.homecontroller.ui.controlls.volume.VolumeControls
import de.moyapro.homecontroller.ui.main.MainPresentationModel


@Composable
fun ControllerView(mainPresentationModel: State<MainPresentationModel>, tvActions: TvActions) {
    Column(modifier = Modifier.fillMaxSize(), Arrangement.Top) {
        VolumeControls(modifier = Modifier
            .fillMaxHeight(.2F)
            .padding(5.dp),
            mainPresentationModel.value.volume,
            tvActions)
        BackHomeRow(modifier = Modifier.fillMaxHeight(.15F), tvActions)
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
        .fillMaxHeight()
        .padding(horizontal = 5.dp
        ),
        verticalArrangement = Arrangement.SpaceEvenly) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(.33333F)
            .padding(vertical = 5.dp),
            horizontalArrangement = Arrangement.Center) {
            UpButton(tvActions.upAction)
        }
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            LeftButton(tvActions.leftAction)
            OkButton(tvActions.okAction)
            RightButton(tvActions.rightAction)
        }
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            DownButton(tvActions.downAction)
        }
    }
}
