package com.example.homecontroller.ui.main.model

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