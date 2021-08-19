package com.abc.schedule.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.abc.schedule.R
import com.abc.schedule.activity.DrawerLocker
import com.abc.schedule.data.models.Client
import com.abc.schedule.data.viewmodel.StateManagerViewModel
import com.abc.schedule.databinding.FragmentLandingBinding
import java.lang.Exception


class LandingFragment : Fragment() {

    private var _binding: FragmentLandingBinding? = null
    private val binding get() = _binding!!

    private val mStateManagerViewModel: StateManagerViewModel by viewModels()

    private lateinit var client: Client

    private lateinit var drawerLocker: DrawerLocker


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


        mStateManagerViewModel.getSplashScreenState().observe(viewLifecycleOwner, Observer {

            mStateManagerViewModel.statePresent = MutableLiveData(it)

            if (it) {

                findNavController().navigate(R.id.action_landing_to_dashboard)

            }

        })



        binding.setUp.setOnClickListener {

            findNavController().navigate(R.id.action_landing_to_clientDetails)

        }


        return binding.root
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}