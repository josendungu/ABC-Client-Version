package com.android.abc.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.android.abc.R
import com.android.abc.data.viewmodel.ClientDetailsViewModel
import com.android.abc.data.viewmodel.StateManagerViewModel
import com.android.abc.databinding.FragmentLandingBinding


class LandingFragment : Fragment() {

    private var _binding: FragmentLandingBinding? = null
    private val binding get() = _binding!!

    private val mStateManagerViewModel: StateManagerViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentLandingBinding.inflate(inflater, container, false)
        binding.mStateViewModel = mStateManagerViewModel
        binding.lifecycleOwner = this

        mStateManagerViewModel.getSplashScreenState().observe(viewLifecycleOwner, { state ->

            mStateManagerViewModel.statePresent = MutableLiveData(state)

            if (state) {

                findNavController().navigate(R.id.action_landing_to_dashboard)

            }

        })


        binding.setUp.setOnClickListener {

            findNavController().navigate(R.id.action_landing_to_clientDetails)

        }


        return binding.root
    }


}