package de.moyapro.homecontroller.ui

import android.util.Log
import android.view.MotionEvent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.pointer.pointerInteropFilter
import de.moyapro.homecontroller.tv.TAG
import kotlinx.coroutines.delay
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.ExperimentalTime

/**
 * Thanks to Carter Hudson
 * https://www.droidcon.com/2021/10/29/creating-a-repeating-button-with-jetpack-compose/
 */
@OptIn(ExperimentalComposeUiApi::class, ExperimentalTime::class)
@Composable
fun RepeatingButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    onRelease: () -> Unit,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    elevation: ButtonElevation? = ButtonDefaults.elevation(),
    shape: Shape = MaterialTheme.shapes.small,
    border: BorderStroke? = null,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    maxDelay: Duration = 10.milliseconds,
    minDelay: Duration = 5.milliseconds,
    delayDecayFactor: Double = .15,
    content: @Composable RowScope.() -> Unit,
) {

    var pressed by remember { mutableStateOf(false) }
    Button(modifier = modifier.pointerInteropFilter {
        pressed = when (it.action) {
            MotionEvent.ACTION_DOWN -> true
            MotionEvent.ACTION_UP -> {
                onRelease()
                false
            }
            else -> false
        }
        true
    },
        onClick = {},
        enabled = enabled,
        interactionSource = interactionSource,
        elevation = elevation,
        shape = shape,
        border = border,
        colors = colors,
        contentPadding = contentPadding,
        content = content)

    LaunchedEffect(pressed, enabled) {
        var currentDelay = maxDelay
        while (enabled && pressed) {
            onClick()
            delay(currentDelay)
            currentDelay =
                (currentDelay - (currentDelay * delayDecayFactor)).coerceAtLeast(minDelay)
        }
    }
}
