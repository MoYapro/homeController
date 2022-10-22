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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.pointer.pointerInteropFilter
import kotlinx.coroutines.delay
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalComposeUiApi::class, ExperimentalTime::class)
@Composable
fun RepeatingButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    onRelease: () -> Unit = {},
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    elevation: ButtonElevation? = ButtonDefaults.elevation(),
    shape: Shape = MaterialTheme.shapes.medium,
    border: BorderStroke? = null,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    maxDelay: Duration = 500.milliseconds,
    minDelay: Duration = 10.milliseconds,
    delayDecayFactor: Double = .1,
    content: @Composable RowScope.() -> Unit,
) {
    val currentClickListener by rememberUpdatedState(onClick)
    var pressed by remember { mutableStateOf(false) }

    Button(
        modifier = modifier
            .pointerInteropFilter { event ->
                pressed = when (event.action) {
                    MotionEvent.ACTION_DOWN -> true
                    MotionEvent.ACTION_MOVE -> pressed
                    MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                        onRelease()
                        false
                    }
                    else -> {
                        Log.i(TAG, "MotionEvent $event")
                        pressed// ignore other events an don't change pressed status
                    }

                }
                true
            },
        onClick = {},
        enabled = enabled,
        interactionSource = interactionSource,
        elevation = elevation,
        shape = shape,
        border = border,
        colors = ButtonDefaults.buttonColors(
            contentColor = if (pressed) Color.Green
            else colors.contentColor(enabled = enabled).value),
        contentPadding = contentPadding,
        content = content
    )

    LaunchedEffect(pressed, enabled) {
        var currentDelay = maxDelay

        while (enabled && pressed) {
            currentClickListener()
            delay(currentDelay)
            currentDelay =
                (currentDelay - (currentDelay * delayDecayFactor)).coerceAtLeast(minDelay)
        }
    }
}

const val TAG: String = "Repeatable button"
