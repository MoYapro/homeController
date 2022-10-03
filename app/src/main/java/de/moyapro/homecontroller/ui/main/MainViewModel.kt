package de.moyapro.homecontroller.ui.main

import androidx.lifecycle.ViewModel
import de.moyapro.homecontroller.ui.main.View.START
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel : ViewModel() {

    private var _currentView: MutableStateFlow<View> = MutableStateFlow(START)
    val currentView: StateFlow<View> = _currentView

    fun setPowerStatus(newValue: View) {
        _currentView.value = newValue
    }
}
