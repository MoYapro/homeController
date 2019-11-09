package com.example.homecontroller.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders


class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return buildLayout().rootView
    }

    private fun buildLayout(): RelativeLayout {
        val layout = RelativeLayout(this.context)
        layout.addView(buildButton())
        return layout
    }

    private fun buildButton(): Button {
        val button = Button(this.context)
        button.text = "Button"

        val lp = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.WRAP_CONTENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT
        )
        lp.addRule(RelativeLayout.CENTER_IN_PARENT)

        // Setting the parameters on the TextView
        button.layoutParams = lp
        return button
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
