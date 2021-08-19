package com.abc.client1.fragments

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData

class BindingAdapter {

    companion object{

        @BindingAdapter("android:statePresent")
        @JvmStatic
        fun statePresent(view: View, statePresent:MutableLiveData<Boolean>){
            when(statePresent.value){
                true -> view.visibility = View.INVISIBLE
                false -> view.visibility = View.VISIBLE
            }
        }

    }
}