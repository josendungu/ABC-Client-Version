package com.android.abc.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.android.abc.R
import com.android.abc.databinding.FragmentDashboardBinding


class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)

        val toolbar = binding.toolBarLayout
        (activity as AppCompatActivity?)!!.setSupportActionBar(toolbar)

        binding.schedule.setOnClickListener {
            findNavController().navigate(R.id.action_dashboard_to_scheduleCarDetails)
        }


        return binding.root
    }


}