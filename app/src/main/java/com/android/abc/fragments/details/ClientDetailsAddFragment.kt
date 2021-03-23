package com.android.abc.fragments.details

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.fragment.findNavController
import com.android.abc.R
import com.android.abc.data.models.Client
import com.android.abc.data.viewmodel.ClientDetailsViewModel
import com.android.abc.databinding.FragmentClientDetailsAddBinding


class ClientDetailsAddFragment : Fragment() {

    private var _binding: FragmentClientDetailsAddBinding? = null
    private val binding get() = _binding!!

    private val mClientDetailsViewModel: ClientDetailsViewModel by viewModels()


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

            val idText = binding.editTextId.text.toString()
            val id = if (idText == ""){
                null
            } else {
                Integer.parseInt(idText)
            }

            val phoneNumberText = binding.editTextNumber.text.toString()
            val phoneNumber = if (phoneNumberText == ""){
                null
            } else {
                Integer.parseInt(phoneNumberText)
            }

            val email = binding.editTextEmail.text.toString()
            val plates: MutableList<String> = ArrayList()

            val client = Client(surname, firstName, lastName, phoneNumber, email, county, town, id, plates)
            mClientDetailsViewModel.setClientData(client)


            if (
                mClientDetailsViewModel.validateSurname(binding.editTextSurname) &&
                mClientDetailsViewModel.validateFirstName(binding.editTextFirstName) &&
                mClientDetailsViewModel.validateLastName(binding.editTextLastName) &&
                mClientDetailsViewModel.validateCounty(binding.editTextCounty) &&
                mClientDetailsViewModel.validateTown(binding.editTextTown) &&
                mClientDetailsViewModel.validatePhone(binding.editTextNumber) &&
                mClientDetailsViewModel.validateEmail(binding.editTextEmail) &&
                mClientDetailsViewModel.validateId(binding.editTextId)
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