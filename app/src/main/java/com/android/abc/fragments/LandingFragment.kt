package com.android.abc.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import com.android.abc.R
import com.android.abc.activity.DrawerLocker
import com.android.abc.data.models.Client
import com.android.abc.data.viewmodel.ClientDetailsViewModel
import com.android.abc.data.viewmodel.StateManagerViewModel
import com.android.abc.databinding.FragmentLandingBinding
import java.lang.Exception


class LandingFragment : Fragment() {

    private var _binding: FragmentLandingBinding? = null
    private val binding get() = _binding!!

    private val mStateManagerViewModel: StateManagerViewModel by viewModels()
    private val mClientDetailsViewModel: ClientDetailsViewModel by viewModels()

    private lateinit var client: Client

    private lateinit var drawerLocker: DrawerLocker

    override fun onStart() {
        super.onStart()

        mClientDetailsViewModel.fetchClientData().observe(viewLifecycleOwner, {
            client = it
        })
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        try {
            drawerLocker = (activity as DrawerLocker)
        } catch (e: Exception){
            throw ClassCastException(activity.toString() + " must implement MyInterface");
        }

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentLandingBinding.inflate(inflater, container, false)
        binding.mStateViewModel = mStateManagerViewModel
        binding.lifecycleOwner = this

        drawerLocker.lockDrawer()


        mStateManagerViewModel.getSplashScreenState().observe(viewLifecycleOwner, { state ->

            mStateManagerViewModel.statePresent = MutableLiveData(state)

            if (state) {

                val action = LandingFragmentDirections.actionLandingToDashboard(client)
                findNavController().navigate(action)

            }

        })



        binding.setUp.setOnClickListener {

            findNavController().navigate(R.id.action_landing_to_clientDetails)

        }


        return binding.root
    }

    override fun onStop() {
        super.onStop()
        drawerLocker.unlockDrawer()

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}