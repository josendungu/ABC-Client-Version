package com.android.abc.fragments.scheduleValuation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.abc.R
import com.android.abc.data.viewmodel.ClientDetailsViewModel
import com.android.abc.databinding.FragmentScheduleCarDetailsBinding
import com.android.abc.utils.SplashScreenStateManager


class ScheduleCarDetailsFragment : Fragment() {

    private var _binding: FragmentScheduleCarDetailsBinding? = null
    private val binding get() = _binding!!

    private val adapter = CarSelectAdapter()

    private val mClientDetailsViewModel: ClientDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScheduleCarDetailsBinding.inflate(inflater, container, false)

        mClientDetailsViewModel.fetchClientData().observe(viewLifecycleOwner, {
            adapter.plateList = it.plates
        })

        setUpRecyclerView()

        return binding.root
    }

    private fun setUpRecyclerView() {
        binding.carRecycler.adapter = adapter
        binding.carRecycler.layoutManager = LinearLayoutManager(requireActivity())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}