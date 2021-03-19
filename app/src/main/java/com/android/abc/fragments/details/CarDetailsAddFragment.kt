package com.android.abc.fragments.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.android.abc.R
import com.android.abc.databinding.FragmentCarDetailsAddBinding


class CarDetailsAddFragment : Fragment() {

    private var _binding: FragmentCarDetailsAddBinding? = null
    private val binding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentCarDetailsAddBinding.inflate(inflater, container, false)

        binding.buttonSubmit.setOnClickListener {
            findNavController().navigate(R.id.action_carDetailsAdd_to_dashboard)
        }

        return binding.root
    }


}