package com.android.abc.fragments.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.android.abc.data.models.Client
import com.android.abc.data.viewmodel.ClientDetailsViewModel
import com.android.abc.data.viewmodel.SharedViewModel
import com.android.abc.databinding.FragmentClientDetailsAddBinding


class ClientDetailsAddFragment : Fragment() {

    private var _binding: FragmentClientDetailsAddBinding? = null
    private val binding get() = _binding!!

    private val mClientDetailsViewModel: ClientDetailsViewModel by viewModels()
    private val mSharedViewModel: SharedViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentClientDetailsAddBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this

        binding.buttonClientNext.setOnClickListener {

            setAllErrorNull()

            val surname = binding.editTextSurname.text.toString()
            val firstName = binding.editTextFirstName.text.toString()
            val lastName = binding.editTextLastName.text.toString()
            val county = binding.editTextCounty.text.toString()
            val town = binding.editTextTown.text.toString()
            val id = mSharedViewModel.convertIntToString(binding.editTextId.text.toString())
            val phoneNumber = mSharedViewModel.convertIntToString(binding.editTextNumber.text.toString())
            val email = binding.editTextEmail.text.toString()
            val plates: MutableList<String> = ArrayList()

            val client = Client(surname, firstName, lastName, phoneNumber, email, county, town, id, plates)
            mClientDetailsViewModel.setClientData(client)


            if (
                mSharedViewModel.validateSurname(surname, binding.editTextSurname) &&
                mSharedViewModel.validateFirstName(firstName, binding.editTextFirstName) &&
                mSharedViewModel.validateLastName(lastName, binding.editTextLastName) &&
                mSharedViewModel.validateCounty(county, binding.editTextCounty) &&
                mSharedViewModel.validateTown(town, binding.editTextTown) &&
                mSharedViewModel.validatePhone(phoneNumber.toString(), binding.editTextNumber) &&
                mSharedViewModel.validateEmail(email, binding.editTextEmail) &&
                mSharedViewModel.validateId(id.toString(), binding.editTextId)
            ) {

                val action =
                    ClientDetailsAddFragmentDirections.actionClientDetailsToCarDetails(client)
                findNavController().navigate(action)
            }

        }



        return binding.root
    }

    private fun setAllErrorNull() {
        binding.editTextTown.error = null
        binding.editTextCounty.error = null
        binding.editTextId.error = null
        binding.editTextNumber.error = null
        binding.editTextEmail.error = null
        binding.editTextLastName.error = null
        binding.editTextFirstName.error = null
        binding.editTextSurname.error = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }




}