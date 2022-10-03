package de.moyapro.homecontroller

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import de.moyapro.homecontroller.communication.tv.model.ConnectionProperties
import de.moyapro.homecontroller.factory.ViewModelFactory
import de.moyapro.homecontroller.tv.TvActions
import de.moyapro.homecontroller.tv.TvState
import de.moyapro.homecontroller.tv.buildTvActions
import de.moyapro.homecontroller.ui.main.MainController
import de.moyapro.homecontroller.ui.main.MainViewModel
import de.moyapro.homecontroller.ui.settings.PREFERENCES_FILE_NAME
import de.moyapro.homecontroller.ui.settings.buildConnectionPropertiesFrom
import de.moyapro.homecontroller.ui.theme.HomeControllerTheme
import kotlinx.coroutines.*
import kotlin.time.Duration.Companion.seconds
import kotlin.time.ExperimentalTime

class MainActivity : ComponentActivity() {
    private val tag = MainActivity::class.simpleName

    private val mainViewModel: MainViewModel by viewModels { ViewModelFactory }
    private val tvState: TvState by viewModels { ViewModelFactory }
    private val connectionProperties by lazy {
        buildConnectionPropertiesFrom(getSharedPreferences(PREFERENCES_FILE_NAME, MODE_PRIVATE))
    }
    private val actions: TvActions by lazy {
        buildTvActions(connectionProperties, tvState)
    }
    private var job: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startBackgroundRefresh(actions.updatePowerStatus)
        val mainController = MainController(tvState, mainViewModel)
        setContent {
            HomeControllerTheme {
                mainController.render()
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
        Log.d(tag, "start background refresh")
        job = GlobalScope.launch(Dispatchers.IO) {
            while (this.isActive) {
                updatePowerStatus()
                delay(1.5.seconds)
            }
        }
    }

    fun stopBackgroundRefresh() {
        Log.d(tag, "stop background refresh")
        job?.cancel()
        job = null
    }


}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val viewModel = MainViewModel()
    val tvState = TvState()
    val actions = buildTvActions(ConnectionProperties("", ""), TvState())
    HomeControllerTheme {
        MainController(TvState(), viewModel).render()
    }
}
