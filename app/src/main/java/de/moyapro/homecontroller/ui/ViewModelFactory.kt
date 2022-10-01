package de.moyapro.homecontroller.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

object ViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        Log.d(ViewModelFactory::class.simpleName, "Create Viewmodel of type $modelClass")
        val viewModelConstructor = modelClass.constructors.single { it.parameterCount == 0 }
        return viewModelConstructor.newInstance() as T
    }
}
