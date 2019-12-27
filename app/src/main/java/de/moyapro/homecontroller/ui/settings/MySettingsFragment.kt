package de.moyapro.homecontroller.ui.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import de.moyapro.homecontroller.R

class MySettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
    }
}