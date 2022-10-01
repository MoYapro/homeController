package de.moyapro.homecontroller

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import de.moyapro.homecontroller.communication.tv.SettingsKeys
import de.moyapro.homecontroller.communication.tv.model.ConnectionProperties
import de.moyapro.homecontroller.communication.tv.model.PowerStatus
import de.moyapro.homecontroller.ui.ControllerViewModel
import de.moyapro.homecontroller.ui.ViewActions
import de.moyapro.homecontroller.ui.ViewModelFactory
import de.moyapro.homecontroller.ui.buildViewActions
import de.moyapro.homecontroller.ui.theme.HomeControllerTheme
import kotlinx.coroutines.*
import kotlin.time.Duration.Companion.seconds
import kotlin.time.ExperimentalTime

class MainActivity : ComponentActivity() {
    val TAG = "main"
    private val PREFERENCES_FILE_NAME = "homeControllerSettingsFilename"
    private val viewModel: ControllerViewModel by viewModels { ViewModelFactory }
    private val connectionProperties by lazy {
        buildConnectionPropertiesFrom(
            getSharedPreferences(PREFERENCES_FILE_NAME, MODE_PRIVATE))
    }
    private val actions: ViewActions by lazy {
        buildViewActions(connectionProperties, viewModel)
    }
    private var job: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startBackgroundRefresh(actions.updatePowerStatus)

        setContent {
            val powerStatus = viewModel.powerStatus.collectAsState()
            HomeControllerTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background) {
                    Column() {

                    Text("xxx" + powerStatus.value.status)
                    Button(onClick = { viewModel.setPowerStatus("active") }) {
                        Text("activate")
                    }
                    Button(onClick = { viewModel.setPowerStatus("standby") }) {
                        Text("Go to sleep")
                    }
//                    if (viewModel.hasPower()) ControllerContent(viewModel, actions)
//                    else StartView(actions)
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        startBackgroundRefresh(actions.updatePowerStatus)
    }

    override fun onStop() {
        super.onStop()
        stopBackgroundRefresh()
    }

    @OptIn(ExperimentalTime::class)
    fun startBackgroundRefresh(
        updatePowerStatus: () -> Unit,
    ) {
        if (true == job?.isActive) return

        stopBackgroundRefresh()
        Log.d(TAG, "start background refresh")
        job = GlobalScope.launch(Dispatchers.IO) {
            while (this.isActive) {
                updatePowerStatus()
                delay(1.5.seconds)
            }
        }
    }

    fun stopBackgroundRefresh() {
        Log.d(TAG, "stop background refresh")
        job?.cancel()
        job = null
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
        if (null == preferences) return null

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
    val viewModel = ControllerViewModel()
    val actions = buildViewActions(ConnectionProperties("", ""), viewModel)
    HomeControllerTheme {
        ControllerContent(viewModel, actions)
    }
}
