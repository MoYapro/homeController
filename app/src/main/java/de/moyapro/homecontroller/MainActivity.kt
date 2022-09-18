package de.moyapro.homecontroller

import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import de.moyapro.homecontroller.communication.tv.SettingsKeys
import de.moyapro.homecontroller.communication.tv.model.ConnectionProperties
import de.moyapro.homecontroller.ui.ControllerViewModel
import de.moyapro.homecontroller.ui.ViewActions
import de.moyapro.homecontroller.ui.buildViewActions
import de.moyapro.homecontroller.ui.theme.HomeControllerTheme

class MainActivity : ComponentActivity() {

    private val PREFERENCES_FILE_NAME = "homeControllerSettingsFilename"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val preferences = getSharedPreferences(PREFERENCES_FILE_NAME, MODE_PRIVATE)
        val connectionProperties = buildConnectionPropertiesFrom(preferences)
        val actions: ViewActions = buildViewActions(connectionProperties)
        val viewModel = ControllerViewModel()
        setContent {
            HomeControllerTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background) {
                    if (viewModel.connected) ControllerContent(viewModel, actions)
                    else StartView(actions)
                }
            }
        }
    }

    @Composable
    private fun StartView(actions: ViewActions) {
        Scaffold(modifier = Modifier.fillMaxSize(),
            topBar = { OffButton(actions.offAction) },
            bottomBar = { SettingsButton() }
        ) {
            OnButton(actions.onAction)
        }
    }

    @Composable
    private fun OnButton(onAction: () -> Unit) {
        Button(onClick = onAction) {
            Text("on")
        }
    }

    private fun buildConnectionPropertiesFrom(preferences: SharedPreferences?): ConnectionProperties? {
        if (null == preferences) return null;

        val ip = preferences.getString(SettingsKeys.IP, "127.0.0.1")!!
        val password = preferences.getString(SettingsKeys.PASSWORD, "invalid")!!
        return ConnectionProperties("192.168.1.111", "Superteam17")
    }
}

@Composable
fun ControllerContent(viewModel: ControllerViewModel, actions: ViewActions) {
    Column() {
        TopRow(actions)
        CenterDiamond()
        BackHomeRow()
        VolumeControls(viewModel)
        SettingsRow()
    }
}

@Composable
private fun TopRow(actions: ViewActions) {
    Row() {
        OffButton(actions.onAction)
        HdmiSelect()
    }
}

@Composable
private fun OffButton(offAction: () -> Unit) {
    Button(onClick = offAction) { Text(text = "off") }
}

@Composable
private fun SettingsRow() {
    Row() {
        SettingsButton()
    }
}

@Composable
private fun SettingsButton() {
    Button(onClick = { /*TODO*/ }) {
        Text("settings")
    }
}

@Composable
private fun BackHomeRow() {
    Row() {
        Button(onClick = { /*TODO*/ }) { Text("back") }
        Button(onClick = { /*TODO*/ }) { Text("home") }
    }
}

@Composable
private fun VolumeControls(viewModel: ControllerViewModel) {
    Row() {
        Button(onClick = { /*TODO*/ }) { Text("volumn down") }
        Button(onClick = { /*TODO*/ }) { Text("volume up") }
    }
}

@Composable
private fun CenterDiamond() {
    Row() {
        Button(onClick = { /*TODO*/ }) {
            Text("up")
        }
    }
    Row() {
        Button(onClick = { /*TODO*/ }) {
            Text("left")
        }
        Button(onClick = { /*TODO*/ }) {
            Text("ok")
        }
        Button(onClick = { /*TODO*/ }) {
            Text("right")
        }
    }
    Row() {
        Button(onClick = { /*TODO*/ }) {
            Text("down")
        }
    }
}

@Composable
private fun HdmiSelect() {
    Column() {
        Row() {
            Button(onClick = { /*TODO*/ }) { Text("1") }
            Button(onClick = { /*TODO*/ }) { Text("2") }
        }
        Row() {
            Button(onClick = { /*TODO*/ }) { Text("3") }
            Button(onClick = { /*TODO*/ }) { Text("4") }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val viewModel = ControllerViewModel(connected = true)
    val actions = buildViewActions(ConnectionProperties("", ""))
    HomeControllerTheme {
        ControllerContent(viewModel, actions)
    }
}
