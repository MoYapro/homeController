package de.moyapro.homecontroller.ui.settings

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import de.moyapro.homecontroller.R

class MySettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (findViewById<View>(R.id.container) != null && savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.container, MySettingsFragment())
                .commit()
        }
    }
}