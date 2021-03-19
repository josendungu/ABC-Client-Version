package com.android.abc.fragments.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.android.abc.R
import com.android.abc.databinding.FragmentClientDetailsAddBinding


class ClientDetailsAddFragment : Fragment() {

    private var _binding: FragmentClientDetailsAddBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentClientDetailsAddBinding.inflate(inflater, container, false)

        binding.buttonClientNext.setOnClickListener {
            findNavController().navigate(R.id.action_clientDetails_to_carDetails)
        }



        return binding.root
    }


}