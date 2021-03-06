package com.abc.schedule.fragments.details

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.abc.schedule.R
import com.abc.schedule.activity.DrawerLocker
import com.abc.schedule.data.viewmodel.ClientDetailsViewModel
import com.abc.schedule.data.viewmodel.StateManagerViewModel
import com.abc.schedule.databinding.FragmentCarDetailsAddBinding
import com.google.android.material.snackbar.Snackbar
import java.lang.Exception


class CarDetailsAddFragment : Fragment() {

    private var _binding: FragmentCarDetailsAddBinding? = null
    private val binding get() = _binding!!

    private val adapter by lazy { CarRecyclerAdapter() }

    private lateinit var tempPlateList: MutableList<String>
    private val args by navArgs<CarDetailsAddFragmentArgs>()

    private val mClientDetailsViewModel: ClientDetailsViewModel by viewModels()
    private val mStateManager: StateManagerViewModel by viewModels()

    private var toSchedule: Boolean = false

    private lateinit var drawerLocker: DrawerLocker

    override fun onAttach(context: Context) {
        super.onAttach(context)

        try {
            drawerLocker = (activity as DrawerLocker)
        } catch (e: Exception){
            throw ClassCastException(activity.toString() + " must implement MyInterface")
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCarDetailsAddBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this



        mClientDetailsViewModel.setClientData(args.clientDetails)
        toSchedule = args.backToSchedule

        tempPlateList = mClientDetailsViewModel.plates!!

        if (toSchedule){
            adapter.setData(tempPlateList)
        }

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
                    binding.editTextPlate.text = null
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
                mClientDetailsViewModel.saveClientDetails()
                mStateManager.saveState(true)

                if (toSchedule){
                    val action = CarDetailsAddFragmentDirections.actionCarDetailsAddFragmentToScheduleCarDetailsFragment(mClientDetailsViewModel.getClient(),true)
                    findNavController().navigate(action)
                } else {
                    val snackBar = Snackbar.make(binding.snackBarContainer, R.string.client_added, Snackbar.LENGTH_INDEFINITE)
                    snackBar.setAction("OK") {
                        findNavController().navigate(R.id.action_carDetailsAdd_to_dashboard)
                    }
                    snackBar.show()
                }

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