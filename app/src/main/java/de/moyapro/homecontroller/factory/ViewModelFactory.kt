package de.moyapro.homecontroller.factory

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.reflect.Constructor

object ViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        Log.d(ViewModelFactory::class.simpleName, "Create Viewmodel of type $modelClass")
        val viewModelConstructor: Constructor<T> = modelClass.constructors.single { it.parameterCount == 0 } as Constructor<T>
        return viewModelConstructor.newInstance()
    }
}
