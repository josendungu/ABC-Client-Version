package com.android.abc.fragments.scheduleValuation

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.android.abc.R
import com.android.abc.activity.DrawerLocker
import com.android.abc.data.models.Client
import com.android.abc.data.models.ScheduleDetails
import com.android.abc.data.viewmodel.ClientDetailsViewModel
import com.android.abc.data.viewmodel.SharedViewModel
import com.android.abc.databinding.FragmentScheduleCarDetailsBinding
import com.android.abc.databinding.FragmentScheduleVenueDetailsBinding
import java.lang.Exception


class ScheduleVenueDetailsFragment : Fragment() {

    private var _binding: FragmentScheduleVenueDetailsBinding? = null
    private val binding get() = _binding!!

    private val mClientDetailsViewModel: ClientDetailsViewModel by viewModels()
    private val mSharedViewModel: SharedViewModel by viewModels()
    private val args by navArgs<ScheduleVenueDetailsFragmentArgs>()

    private lateinit var scheduleDetails: ScheduleDetails

    private lateinit var client: Client

    private lateinit var drawerLocker: DrawerLocker

    override fun onAttach(context: Context) {
        super.onAttach(context)

        try {
            drawerLocker = (activity as DrawerLocker)
        } catch (e: Exception){
            throw ClassCastException(activity.toString() + " must implement MyInterface")
        }

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.mClientDetails = mClientDetailsViewModel
        binding.selectedPlateText.text = scheduleDetails.plateNumber

    }

    override fun onStop() {
        super.onStop()
        drawerLocker.unlockDrawer()

    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentScheduleVenueDetailsBinding.inflate(inflater, container, false)
        scheduleDetails = args.scheduleDetails
        client = args.clientDetails

        drawerLocker.lockDrawer()

        mClientDetailsViewModel.setClientData(client)

        binding.buttonCancel.setOnClickListener {
            val action = ScheduleVenueDetailsFragmentDirections.actionScheduleVenueDetailsToDashboard()
            findNavController().navigate(action)
        }

        binding.buttonNext.setOnClickListener {

            setAllErrorNull()

            val county = binding.editTextCounty.text.toString()
            val town = binding.editTextTown.text.toString()
            val locationDetails = binding.editTextLocationDetails.text.toString()


            if (
                mSharedViewModel.validateCounty(county, binding.editTextCounty) &&
                mSharedViewModel.validateTown(town, binding.editTextTown)
            ){

                scheduleDetails.county = county
                scheduleDetails.town = town
                scheduleDetails.locationDetails = locationDetails

                val action = ScheduleVenueDetailsFragmentDirections.actionScheduleVenueDetailsToScheduleDay(scheduleDetails, client)
                findNavController().navigate(action)
            }

        }

        return binding.root
    }

    private fun setAllErrorNull() {
        binding.editTextCounty.error = null
        binding.editTextTown.error = null
        binding.editTextLocationDetails.error = null
    }


}