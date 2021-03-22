package com.android.abc.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.android.abc.splashDataStore
import com.android.abc.utils.SplashScreenStateManager

class StateManagerViewModel(application: Application): AndroidViewModel(application) {

    private val splashScreenStateManager = application.baseContext.splashDataStore.let { SplashScreenStateManager(it) }

    var statePresent: MutableLiveData<Boolean> = MutableLiveData(false)

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


    suspend fun saveState(boolean: Boolean){

        splashScreenStateManager.saveState(boolean)

    }
}