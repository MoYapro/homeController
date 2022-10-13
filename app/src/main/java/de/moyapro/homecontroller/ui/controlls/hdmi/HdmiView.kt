package de.moyapro.homecontroller.ui.controlls.hdmi

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.moyapro.homecontroller.communication.tv.model.HdmiStatus
import de.moyapro.homecontroller.tv.TvActions
import de.moyapro.homecontroller.ui.HdmiButton

@Composable
fun HdmiSelect(hdmiStatus: List<HdmiStatus>, setHdmiAction: (String) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(1.1F),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        hdmiStatus
            .filter { it.uri.contains("extInput:hdmi") }
            .forEachIndexed { index, status ->
                val relativeSize: Float = (1.1F / (hdmiStatus.size - index))
                HdmiButton(Modifier
                    .fillMaxWidth(relativeSize),
                    status,
                    setHdmiAction
                )
            }
    }
}