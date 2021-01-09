package de.moyapro.homecontroller.ui.util

import android.graphics.Rect
import android.os.Handler
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener

class HoldableButtonListener(
    private val initialInterval: Int = 1000,
    private val normalInterval: Int = 150
) : OnTouchListener {
    private var touchedViewArea: Rect = Rect()
    private lateinit var touchedView: View
    private val handler = Handler()
    private val handlerRunnable: Runnable = object : Runnable {
        override fun run() {
            if (touchedView.isPressed) {
                handler.postDelayed(this, normalInterval.toLong())
                touchedView.performClick()
            } else {
                handler.removeCallbacks(this)
                touchedView.isPressed = false
            }
        }
    }

    override fun onTouch(view: View, event: MotionEvent): Boolean {
        return when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                handler.postDelayed(handlerRunnable, initialInterval.toLong())
                touchedView = view
                touchedViewArea = Rect(view.left, view.top, view.right, view.bottom)
                touchedView.isPressed = true
                touchedView.performClick()
                true
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                stopRepeat()
                true
            }
            MotionEvent.ACTION_MOVE -> {
                if (isOutside(view, event)) {
                    stopRepeat()
                }
                false
            }
            else -> {
                Log.e(this.javaClass.simpleName, "Unknown touch event: $event")
                false
            }
        }
    }

    private fun isOutside(view: View, event: MotionEvent) =
        !touchedViewArea.contains(
            (view.left + event.x).toInt(),
            (view.top + event.y).toInt()
        )

    private fun stopRepeat() {
        handler.removeCallbacks(handlerRunnable)
        touchedView.isPressed = false
    }

    init {
        require(!(initialInterval < 0 || normalInterval < 0)) { "negative interval" }
    }
}
