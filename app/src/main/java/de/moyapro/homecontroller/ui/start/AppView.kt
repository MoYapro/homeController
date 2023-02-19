package de.moyapro.homecontroller.ui.start

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.moyapro.homecontroller.tv.TvActions
import de.moyapro.homecontroller.ui.UpdateAppListButton

@Composable
fun AppView(tvActions: TvActions, modifier: Modifier = Modifier) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(25.dp)
    ) {
        UpdateAppListButton(tvActions.updateAppList,
            modifier)
    }
}
