package de.moyapro.homecontroller.ui.general

import androidx.fragment.app.Fragment

abstract class RunningFragment : Fragment() {
    var isRunning = false
    override fun onResume() {
        super.onResume()
        isRunning = true
    }

    override fun onStop() {
        super.onStop()
        isRunning = false
    }

    override fun onPause() {
        super.onPause()
        isRunning = false;
    }
}