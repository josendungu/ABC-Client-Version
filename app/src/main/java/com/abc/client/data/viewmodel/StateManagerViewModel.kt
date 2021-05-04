package com.abc.client.data.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.abc.client.utils.SplashScreenStateManager
import com.abc.client.utils.splashDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StateManagerViewModel(application: Application): AndroidViewModel(application) {

    private val splashScreenStateManager = application.baseContext.splashDataStore.let { SplashScreenStateManager(it) }

    var statePresent: MutableLiveData<Boolean> = MutableLiveData()

    fun getSplashScreenState(): LiveData<Boolean>{

        splashScreenStateManager.state.asLiveData().observeForever {
            if (it == null){
                statePresent.value = false
            }else{
                statePresent.value = it
            }
        }

        return statePresent

    }


    fun saveState(boolean: Boolean){

        viewModelScope.launch(Dispatchers.IO) {
            splashScreenStateManager.saveState(boolean)
        }


    }
}