package com.android.abc.fragments

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.android.abc.R
import com.android.abc.SplashScreenStateManager
import com.android.abc.dataStore


class LandingFragment : Fragment() {

    lateinit var splashScreenStateManager: SplashScreenStateManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        splashScreenStateManager = context?.dataStore?.let { SplashScreenStateManager(it) }!!


        splashScreenStateManager.state.asLiveData().observe(viewLifecycleOwner, {

            if (it){

                Handler().postDelayed({

                    findNavController().navigate(R.id.action_landing_to_clientDetails)

                }, 1000)

            } else {

                Handler().postDelayed({

                    findNavController().navigate(R.id.action_landing_to_dashboard)

                }, 1000)


            }

        })


        return inflater.inflate(R.layout.fragment_landing, container, false)



    }


}