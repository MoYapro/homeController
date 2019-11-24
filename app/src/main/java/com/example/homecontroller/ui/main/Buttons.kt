package com.example.homecontroller.ui.main

//
//import android.content.Context
//import android.view.View
//import android.widget.Button
//import android.widget.RelativeLayout
//
// fun buildIncreaseVolumeButton(context: Context?): Button {
//    return buildDefaultButton("+", ::increaseVolume, context)
//}
//
// fun buildDecreaseVolumeButton(context: Context?): Button {
//    return buildDefaultButton("-", ::decreaseVolume, context)
//}
//
// fun buildPowerOffButton(context: Context?): Button {
//    return buildDefaultButton("AUS", ::powerOff, context)
//}
//
// fun buildPowerOnButton(context: Context?): Button {
//    return buildDefaultButton("AN", ::powerOn, context)
//}
//
// fun buildHdmi4Button(context: Context?): Button {
//    return buildDefaultButton("HDMI4", ::switchToHdmi4, context)
//}
//
// fun buildCenterButton(context: Context?): Button {
//    return buildDefaultButton("◍", ::center, context)
//}
//
// fun buildDownButton(context: Context?): Button {
//    return buildDefaultButton("⇩", ::down, context)
//}
//
// fun buildLeftButton(context: Context?): Button {
//    return buildDefaultButton("⇦", ::left, context)
//}
//
// fun buildUpButton(context: Context?): Button {
//    return buildDefaultButton("⇧", ::up, context)
//}
//
// fun buildRightButton(context: Context?): Button {
//    return buildDefaultButton("⇨", ::right, context)
//}
//
// fun buildDefaultButton(label: String, action: (View) -> Unit, context: Context?): Button {
//    val button = Button(context)
//    button.text = label
//    button.setOnClickListener(action)
//    val lp = RelativeLayout.LayoutParams(
//        RelativeLayout.LayoutParams.WRAP_CONTENT,
//        RelativeLayout.LayoutParams.WRAP_CONTENT
//    )
//    lp.addRule(RelativeLayout.CENTER_IN_PARENT)
//
//    button.layoutParams = lp
//    return button
//}