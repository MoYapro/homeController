package de.moyapro.homecontroller.ui.main

import androidx.lifecycle.ViewModel
import de.moyapro.homecontroller.ui.main.View.START
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel : ViewModel() {

    private var _selectedView: MutableStateFlow<View> = MutableStateFlow(START)
    val selectedView: StateFlow<View> = _selectedView

    fun selectView(newValue: View) {
        _selectedView.value = newValue
    }
}
