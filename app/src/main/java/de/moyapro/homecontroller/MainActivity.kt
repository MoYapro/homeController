package de.moyapro.homecontroller

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.lifecycleScope
import de.moyapro.homecontroller.factory.ViewModelFactory
import de.moyapro.homecontroller.tv.TvActions
import de.moyapro.homecontroller.tv.TvStateViewModel
import de.moyapro.homecontroller.tv.buildMockTvActions
import de.moyapro.homecontroller.tv.buildTvActions
import de.moyapro.homecontroller.ui.main.*
import de.moyapro.homecontroller.ui.settings.PREFERENCES_FILE_NAME
import de.moyapro.homecontroller.ui.settings.buildConnectionPropertiesFrom
import de.moyapro.homecontroller.ui.theme.HomeControllerTheme
import de.moyapro.homecontroller.util.Switches
import de.moyapro.homecontroller.util.combineFlows
import kotlinx.coroutines.*
import kotlin.time.Duration.Companion.seconds
import kotlin.time.ExperimentalTime

class MainActivity : ComponentActivity() {
    private val tag = MainActivity::class.simpleName
    private val mainViewModel: MainViewModel by viewModels { ViewModelFactory }
    private val tvStateViewModel: TvStateViewModel by viewModels { ViewModelFactory }
    private val connectionProperties by lazy {
        buildConnectionPropertiesFrom(getSharedPreferences(PREFERENCES_FILE_NAME, MODE_PRIVATE))
    }
    private val tvActions: TvActions by lazy {
        if (Switches.DEBUG) buildMockTvActions(tvStateViewModel)
        else buildTvActions(connectionProperties, tvStateViewModel)
    }

    private val mainActions: MainActions by lazy {
        buildMainActions(mainViewModel)
    }

    private fun buildMainActions(mainViewModel: MainViewModel): MainActions {
        return MainActions(
            openSettings = { mainViewModel.selectView(ViewEnum.SETTINGS) },
            openStart = { mainViewModel.selectView(ViewEnum.START) }
        )
    }

    private var job: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startBackgroundRefresh(tvActions.updatePowerStatus)
        setContent {
            HomeControllerTheme {
                MainContent(tvActions, mainActions)
            }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return when (keyCode) {
            KeyEvent.KEYCODE_VOLUME_DOWN -> {
                tvActions.volumeDown()
                true
            }
            KeyEvent.KEYCODE_VOLUME_UP -> {
                tvActions.volumeUp()
                true
            }
            else -> false
        }
    }

    @Composable
    fun MainContent(tvActions: TvActions, mainActions: MainActions) {
        val presentationModel: State<MainPresentationModel> = combineFlows(
            flow1 = tvStateViewModel.tvState,
            flow2 = mainViewModel.selectedView,
            transform = MainPresenter::present
        ).collectAsState()
        MainView(presentationModel, tvActions, mainActions)
    }

    override fun onStart() {
        super.onStart()
        startBackgroundRefresh(tvActions.updatePowerStatus)
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
        job = lifecycleScope.launch(Dispatchers.IO) {
            while (this.isActive) {
                updatePowerStatus()
                delay(1.5.seconds)
            }
        }
    }

    private fun stopBackgroundRefresh() {
        Log.d(tag, "stop background refresh")
        job?.cancel()
        job = null
    }
}
