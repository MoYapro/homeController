package de.moyapro.homecontroller.ui.main

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import de.moyapro.homecontroller.communication.tv.model.PowerStatus
import de.moyapro.homecontroller.tv.TvState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class MainController(private val tvState: TvState, private val mainViewModel: MainViewModel) {

    private val tag = MainController::class.simpleName

    @Composable
    fun render() {
        Log.i(tag, "render")
        val presentationModel = combineState(
            flow1 = tvState.powerStatus,
            flow2 = mainViewModel.currentView,
            transform = ::present
        ).collectAsState()
        MainView(presentationModel)
    }

    private fun present(powerStatus: PowerStatus, currentView: View): MainViewState {
        Log.i(tag, "present main")
        return MainViewState(when {
            powerStatus.status != "active" -> View.START
            powerStatus.status == "active" -> View.CONTROLLER
            else -> currentView
        }
        )
    }
}

data class MainViewState(val view: View)


fun <T1, T2, R> combineState(
    flow1: StateFlow<T1>,
    flow2: StateFlow<T2>,
    scope: CoroutineScope = GlobalScope,
    sharingStarted: SharingStarted = SharingStarted.Eagerly,
    transform: (T1, T2) -> R,
): StateFlow<R> = combine(flow1, flow2) { o1, o2 ->
    transform.invoke(o1, o2)
}.stateIn(scope, sharingStarted, transform.invoke(flow1.value, flow2.value))
