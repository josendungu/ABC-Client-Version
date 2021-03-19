package com.android.abc.fragments.scheduleValuation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.abc.R
import com.android.abc.databinding.FragmentScheduleCarDetailsBinding
import com.android.abc.databinding.FragmentScheduleVenueDetailsBinding


class ScheduleVenueDetailsFragment : Fragment() {

    private var _binding: FragmentScheduleVenueDetailsBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentScheduleVenueDetailsBinding.inflate(inflater, container, false)

        return binding.root
    }


}