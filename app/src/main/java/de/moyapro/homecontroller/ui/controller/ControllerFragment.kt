package de.moyapro.homecontroller.ui.controller

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import de.moyapro.homecontroller.R
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerVolumeChangeListener()
    }


//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProviders.of(this).get(ControllerViewModel::class.java)
//        val binding: ControllerFragmentBinding =
//            DataBindingUtil.setContentView(this.requireActivity(), R.layout.controller_fragment)
//        binding.viewmodel = viewModel
//        binding.lifecycleOwner = this // <-- this enables MutableLiveData to be update on your UI
//        viewModel.updateVolume("15")
////        registerVolumeChangeListener(viewModel)
//    }

    private fun registerVolumeChangeListener(viewModel: ControllerViewModel = ControllerViewModel()) {
        val volumeControllBar: SeekBar? = view?.findViewById(R.id.seekBar2)
        volumeControllBar?.setOnSeekBarChangeListener(VolumeChangeListener(viewModel))

        if (null == volumeControllBar) {
            Log.e("ControllerFragment", "Could not add listener to volume controll bar")
        } else {
            Log.d("ControllerFragment", "Register listener to volume controll bar")
        }
    }


}
