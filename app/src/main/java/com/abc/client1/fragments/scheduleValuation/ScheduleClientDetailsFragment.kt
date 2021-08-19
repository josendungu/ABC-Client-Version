package com.abc.client1.fragments.scheduleValuation

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.abc.client1.activity.DrawerLocker
import com.abc.client1.data.models.Client
import com.abc.client1.data.models.ScheduleDetails
import com.abc.client1.data.viewmodel.ClientDetailsViewModel
import com.abc.client1.data.viewmodel.SharedViewModel
import com.abc.client1.databinding.FragmentScheduleClientDetailsBinding
import java.lang.Exception


class ScheduleClientDetailsFragment : Fragment() {

    private var _binding: FragmentScheduleClientDetailsBinding? = null
    private val binding get() = _binding!!

    private val mClientDetailsViewModel: ClientDetailsViewModel by viewModels()
    private val mSharedViewModel: SharedViewModel by viewModels()

    private val args by navArgs<ScheduleClientDetailsFragmentArgs>()

    private lateinit var scheduleDetails: ScheduleDetails

    private lateinit var client: Client

    private lateinit var drawerLocker: DrawerLocker

    override fun onAttach(context: Context) {
        super.onAttach(context)

        try {
            drawerLocker = (activity as DrawerLocker)
        } catch (e: Exception){
            throw ClassCastException(activity.toString() + " must implement interface")
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

        _binding = FragmentScheduleClientDetailsBinding.inflate(inflater, container, false)
        scheduleDetails = args.scheduleDetails
        client = args.clientDetails
        mClientDetailsViewModel.setClientData(client)

        drawerLocker.lockDrawer()


        binding.buttonNext.setOnClickListener {

            setAllErrorNull()

            val surname = binding.editTextSurname.text.toString()
            val firstName = binding.editTextFirstName.text.toString()
            val lastName = binding.editTextLastName.text.toString()
            val id = binding.editTextId.text.toString()
            val email = binding.editTextEmail.text.toString()
            val phoneNumber = binding.editTextNumber.text.toString()


            if (
                mSharedViewModel.validateSurname(surname, binding.editTextSurname) &&
                mSharedViewModel.validateFirstName(firstName, binding.editTextFirstName) &&
                mSharedViewModel.validateLastName(lastName, binding.editTextLastName) &&
                mSharedViewModel.validatePhone(phoneNumber, binding.editTextNumber) &&
                mSharedViewModel.validateEmail(email, binding.editTextEmail) &&
                mSharedViewModel.validateId(id, binding.editTextId)
            ) {

                scheduleDetails.firstName = firstName
                scheduleDetails.lastName = lastName
                scheduleDetails.surname = surname
                scheduleDetails.phoneNumber = phoneNumber
                scheduleDetails.email = email
                scheduleDetails.id = id

                val action =
                    ScheduleClientDetailsFragmentDirections.actionScheduleClientDetailsToScheduleVenueDetails(
                        scheduleDetails, client
                    )
                findNavController().navigate(action)
            }
        }

        binding.buttonCancel.setOnClickListener {
            val action = ScheduleClientDetailsFragmentDirections.actionScheduleClientDetailsToDashboard()
            findNavController().navigate(action)
        }

        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setAllErrorNull() {
        binding.editTextId.error = null
        binding.editTextNumber.error = null
        binding.editTextEmail.error = null
        binding.editTextLastName.error = null
        binding.editTextFirstName.error = null
        binding.editTextSurname.error = null
    }

}