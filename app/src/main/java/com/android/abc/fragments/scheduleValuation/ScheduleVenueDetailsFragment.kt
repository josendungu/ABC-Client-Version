package com.android.abc.fragments.scheduleValuation

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
import com.android.abc.data.models.Client
import com.android.abc.data.models.ScheduleDetails
import com.android.abc.data.viewmodel.ClientDetailsViewModel
import com.android.abc.data.viewmodel.SharedViewModel
import com.android.abc.databinding.FragmentScheduleCarDetailsBinding
import com.android.abc.databinding.FragmentScheduleVenueDetailsBinding


class ScheduleVenueDetailsFragment : Fragment() {

    private var _binding: FragmentScheduleVenueDetailsBinding? = null
    private val binding get() = _binding!!

    private val mClientDetailsViewModel: ClientDetailsViewModel by viewModels()
    private val mSharedViewModel: SharedViewModel by viewModels()
    private val args by navArgs<ScheduleVenueDetailsFragmentArgs>()

    private lateinit var scheduleDetails: ScheduleDetails

    private lateinit var client: Client


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.mClientDetails = mClientDetailsViewModel
        binding.selectedPlateText.text = scheduleDetails.plateNumber

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentScheduleVenueDetailsBinding.inflate(inflater, container, false)
        scheduleDetails = args.scheduleDetails
        client = args.clientDetails

        mClientDetailsViewModel.setClientData(client)

        binding.buttonCancel.setOnClickListener {
            val action = ScheduleVenueDetailsFragmentDirections.actionScheduleVenueDetailsToDashboard(client)
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