package de.moyapro.homecontroller

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.SettingsInputHdmi
import androidx.compose.material.icons.outlined.Tv
import androidx.compose.material.icons.outlined.TvOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.moyapro.homecontroller.communication.tv.SettingsKeys
import de.moyapro.homecontroller.communication.tv.model.ConnectionProperties
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
        buildConnectionPropertiesFrom(getSharedPreferences(PREFERENCES_FILE_NAME, MODE_PRIVATE))
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
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    Column(modifier = Modifier.fillMaxSize()) {
                        Row() {
                            debugMenu(viewModel)
                        }
                        if (powerStatus.value.status == "active") {
                            ControllerContent(viewModel, actions)
                        } else StartView(actions)
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
        return
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
            bottomBar = { SettingsButton() }) {
            OnButton(actions.onAction)
        }
    }

    @Composable
    private fun OnButton(onAction: () -> Unit) {
        Button(
            modifier = Modifier.fillMaxSize(),
            onClick = onAction) {
            Icon(Icons.Outlined.Tv, contentDescription = "on")
        }
    }

    private fun buildConnectionPropertiesFrom(preferences: SharedPreferences?): ConnectionProperties? {
        if (null == preferences) return null

        val ip = preferences.getString(SettingsKeys.IP, "127.0.0.1")!!
        val password = preferences.getString(SettingsKeys.PASSWORD, "invalid")!!
        return ConnectionProperties("192.168.1.111", "Superteam17")
    }
}

private val defaultButtonModifier: Modifier = Modifier.height(50.dp)


@Composable
private fun debugMenu(viewModel: ControllerViewModel) {
    Button(modifier = defaultButtonModifier, onClick = { viewModel.setPowerStatus("active") }) {
        Text("activate")
    }
    Button(modifier = defaultButtonModifier, onClick = { viewModel.setPowerStatus("standby") }) {
        Text("Go to sleep")
    }
}

@Composable
fun ControllerContent(viewModel: ControllerViewModel, actions: ViewActions) {
    Column(modifier = Modifier.fillMaxSize(), Arrangement.Top) {
        TopRow(modifier = Modifier.fillMaxHeight(.17F), actions)
        CenterDiamond(modifier = Modifier.fillMaxHeight(.5F))
        BackHomeRow(modifier = Modifier.fillMaxHeight(.2F))
        VolumeControls(modifier = Modifier.fillMaxHeight(.4F), viewModel)
        SettingsRow()
    }
}

@Composable
private fun TopRow(modifier: Modifier, actions: ViewActions) {
    Row(modifier = modifier.fillMaxSize(), horizontalArrangement = Arrangement.SpaceBetween) {
        OffButton(actions.onAction)
        HdmiSelect()
    }
}

@Composable
private fun OffButton(offAction: () -> Unit) {
    Button(onClick = offAction) {
        Icon(Icons.Outlined.TvOff, contentDescription = "off")
    }
}

@Composable
private fun SettingsRow() {
    Row(modifier = Modifier.fillMaxWidth()) {
        SettingsButton()
    }
}

@Composable
private fun SettingsButton() {
    Button(modifier = defaultButtonModifier, onClick = { /*TODO*/ }) {
        Icon(Icons.Filled.Settings, contentDescription = "settings")
    }
}

@Composable
private fun BackHomeRow(modifier: Modifier) {
    Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
        Button(modifier = defaultButtonModifier.fillMaxWidth(.5F), onClick = { /*TODO*/ }) {
            Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
        }
        Button(modifier = defaultButtonModifier.fillMaxWidth(),
            onClick = { /*TODO*/ }) {
            Icon(Icons.Filled.Home, contentDescription = "Home")
        }
    }
}

@Composable
private fun VolumeControls(modifier: Modifier, viewModel: ControllerViewModel) {
    Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
        Button(modifier = defaultButtonModifier.fillMaxWidth(.5F),
            onClick = { /*TODO*/ }) {
            Icon(Icons.Filled.VolumeDown, contentDescription = "volume down")
        }
        Button(modifier = defaultButtonModifier.fillMaxWidth(),
            onClick = { /*TODO*/ }) {
            Icon(Icons.Filled.VolumeUp, contentDescription = "volume up")
        }
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
            Button(modifier = Modifier
                .fillMaxSize()
                .background(Color.Cyan),
                onClick = { /*TODO*/ }) {
                Icon(Icons.Filled.KeyboardArrowUp, contentDescription = "Up")
            }
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            Button(modifier = Modifier
                .fillMaxWidth(.33F)
                .fillMaxHeight(.5F),
                onClick = { /*TODO*/ }) {
                Icon(Icons.Filled.KeyboardArrowLeft, contentDescription = "Left")
            }
            Button(modifier = Modifier
                .fillMaxWidth(.5F)
                .fillMaxHeight(.5F),
                onClick = { /*TODO*/ }) {
                Text("OK")
            }
            Button(modifier = Modifier
                .fillMaxWidth(1F)
                .fillMaxHeight(.5F),
                onClick = { /*TODO*/ }) {
                Icon(Icons.Filled.KeyboardArrowRight, contentDescription = "Right")
            }
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Button(modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(), onClick = { /*TODO*/ }) {
                Icon(Icons.Filled.KeyboardArrowDown, contentDescription = "Down")
            }
        }
    }
}

@Composable
private fun HdmiSelect() {
    Column(modifier = Modifier
        .fillMaxWidth(.5F)
        .fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceEvenly) {
        Row(modifier = Modifier.fillMaxWidth(), Arrangement.SpaceEvenly) {
            Button(modifier = defaultButtonModifier.fillMaxWidth(.5F),
                onClick = { /*TODO*/ }) {
                Icon(Icons.Outlined.SettingsInputHdmi, contentDescription = "H D M I 1")
                Text("1")
            }
            Button(modifier = defaultButtonModifier.fillMaxWidth(),
                onClick = { /*TODO*/ }) {
                Icon(Icons.Outlined.SettingsInputHdmi, contentDescription = "H D M I 2")
                Text("2")
            }
        }
        Row(modifier = Modifier.fillMaxWidth(), Arrangement.SpaceEvenly) {
            Button(modifier = defaultButtonModifier.fillMaxWidth(.5F),
                onClick = { /*TODO*/ }) {
                Icon(Icons.Outlined.SettingsInputHdmi, contentDescription = "H D M I 3")
                Text("3")
            }
            Button(modifier = defaultButtonModifier.fillMaxWidth(),
                onClick = { /*TODO*/ }) {
                Icon(Icons.Outlined.SettingsInputHdmi, contentDescription = "H D M I 4")
                Text("4")
            }
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
