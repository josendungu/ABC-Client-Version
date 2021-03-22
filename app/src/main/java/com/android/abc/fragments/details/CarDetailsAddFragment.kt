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
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.abc.R
import com.android.abc.data.viewmodel.ClientDetailsViewModel
import com.android.abc.databinding.FragmentCarDetailsAddBinding
import com.android.abc.splashDataStore
import com.android.abc.utils.ClientDetailsManager
import com.android.abc.utils.SplashScreenStateManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch


class CarDetailsAddFragment : Fragment() {

    private var _binding: FragmentCarDetailsAddBinding? = null
    private val binding get() = _binding!!

    private val adapter by lazy { CarRecyclerAdapter() }

    private val tempPlateList: MutableList<String> = ArrayList()
    private val args by navArgs<CarDetailsAddFragmentArgs>()

    private val mClientDetailsViewModel: ClientDetailsViewModel by viewModels()
    private val mSplashScreenStateManager: SplashScreenStateManager by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCarDetailsAddBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this

        mClientDetailsViewModel.setClientData(args.clientDetails)


        binding.buttonAddPlate.setOnClickListener {

            val plate = binding.editTextPlate.text.toString()

            if (plate.isEmpty()) {
                binding.editTextPlate.error = "Please enter the plate number"
            } else {

                if (tempPlateList.contains(plate)) {
                    binding.editTextPlate.error = "The plate number already exists!"
                } else {
                    adapter.setData(tempPlateList)
                    tempPlateList.add(plate)
                }

            }

        }

        setRecyclerView()

        binding.buttonSubmit.setOnClickListener {

            binding.editTextPlate.error = null

            val size = tempPlateList.size

            if (size <= 0) {
                binding.editTextPlate.error = "Please add at least one plate number"
            } else {
                mClientDetailsViewModel.plates = tempPlateList

                //coroutine
                CoroutineScope(IO).launch {
                    mClientDetailsViewModel.saveClientDetails()
                    mSplashScreenStateManager.saveState(true)
                }
                findNavController().navigate(R.id.action_carDetailsAdd_to_dashboard)
            }
        }

        return binding.root
    }

    private fun setRecyclerView() {
        binding.carRecycler.adapter = adapter
        binding.carRecycler.layoutManager = LinearLayoutManager(requireActivity())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}