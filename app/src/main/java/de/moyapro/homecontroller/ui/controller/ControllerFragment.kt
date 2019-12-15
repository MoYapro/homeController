package de.moyapro.homecontroller.ui.controller

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import de.moyapro.homecontroller.R
import de.moyapro.homecontroller.databinding.ControllerFragmentBinding
import de.moyapro.homecontroller.ui.databinding.ControllerViewModel


class ControllerFragment : Fragment() {

    companion object {
        fun newInstance() = ControllerFragment()
    }

    private lateinit var viewModel: ControllerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.i(this.javaClass.simpleName, "create fragment controller")
        return inflater.inflate(R.layout.controller_fragment, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ControllerViewModel::class.java)
        registerVolumeChangeListener(viewModel)
        val binding: ControllerFragmentBinding =
            DataBindingUtil.setContentView(this.requireActivity(), R.layout.controller_fragment)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this // <-- this enables MutableLiveData to be update on your UI
        viewModel.updateVolume("15")
//        viewModel.getLiveData().observe(this, object : Observer<String?>() {
//            fun onChanged(s: String) {
//                Log.e("TAG", "--onChanged--$s")
//            }
//        })

    }


    private fun registerVolumeChangeListener(viewModel: ControllerViewModel) {
        val volumeControllBar: SeekBar? = getView()?.findViewById(R.id.seekBar2)
        volumeControllBar?.setOnSeekBarChangeListener(VolumeChangeListener(viewModel))

        if (null == volumeControllBar) {
            Log.e("ControllerFragment", "Could not add listener to volume controll bar")
        }
    }


}
