package de.moyapro.homecontroller.ui.main.databinding

import androidx.databinding.ObservableField

class TvState(
    val powerOn: ObservableField<Boolean> = ObservableField(),
    val helloWorld: ObservableField<String> = ObservableField()
) {

    init {
        powerOn.set(true)
        helloWorld.set("hello")
    }
}