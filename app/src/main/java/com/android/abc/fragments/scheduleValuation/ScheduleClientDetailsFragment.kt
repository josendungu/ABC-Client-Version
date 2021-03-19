package com.android.abc.fragments.scheduleValuation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.abc.R
import com.android.abc.databinding.FragmentScheduleCarDetailsBinding
import com.android.abc.databinding.FragmentScheduleClientDetailsBinding


class ScheduleClientDetailsFragment : Fragment() {

    private var _binding: FragmentScheduleClientDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentScheduleClientDetailsBinding.inflate(inflater, container, false)

        return binding.root
    }

}