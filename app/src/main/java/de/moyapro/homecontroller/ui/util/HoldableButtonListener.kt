package de.moyapro.homecontroller.ui.util

import android.os.AsyncTask
import android.util.Log
import android.view.MotionEvent
import android.view.View


class HoldableButtonListener(private val action: Function<Unit>) : View.OnTouchListener,
    View.OnClickListener {
    private var runner: AsyncTaskRunner? = null



    override fun onTouch(v: View, event: MotionEvent?): Boolean {
        Log.d("onTouch", "start")
        if (event?.action == MotionEvent.ACTION_DOWN) {
            runner = AsyncTaskRunner()
            runner?.execute(action)
            return true
        } else if (event?.action == MotionEvent.ACTION_UP) {
            runner?.cancel(true)
            return true
        }
        return false
    }

    override fun onClick(v: View) {
        onTouch(v, null)
    }
}


internal class AsyncTaskRunner : AsyncTask<Function<Unit>, Unit, Unit>() {
    override fun doInBackground(vararg params: Function<Unit>) {
        while (true) {
            Log.d("AsyncTaskRunner", "run ${System.currentTimeMillis()}")
            params.forEach { it.apply { } }
            Thread.sleep(250L)
        }
    }
}