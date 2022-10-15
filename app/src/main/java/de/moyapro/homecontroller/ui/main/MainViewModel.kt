package de.moyapro.homecontroller.ui.main

import androidx.lifecycle.ViewModel
import de.moyapro.homecontroller.ui.main.ViewEnum.SETTINGS
import de.moyapro.homecontroller.ui.main.ViewEnum.START
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel : ViewModel() {

    private var _selectedView: MutableStateFlow<ViewEnum> = MutableStateFlow(SETTINGS)
    val selectedView: StateFlow<ViewEnum> = _selectedView

    fun selectView(newValue: ViewEnum) {
        _selectedView.value = newValue
    }
}
