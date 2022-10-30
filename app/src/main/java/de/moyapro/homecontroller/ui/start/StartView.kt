package de.moyapro.homecontroller.ui.start

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.moyapro.homecontroller.tv.TvActions
import de.moyapro.homecontroller.ui.OnButton

@Composable
fun StartView(tvActions: TvActions, modifier: Modifier = Modifier) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(25.dp)
    ) {
        OnButton(tvActions.onAction,
            modifier
                .fillMaxHeight(.9F)
                .fillMaxWidth())
    }
}
