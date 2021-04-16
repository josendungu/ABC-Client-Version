package com.android.abc.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import com.android.abc.R
import com.android.abc.activity.DrawerLocker
import com.android.abc.activity.SetupActionBar
import com.android.abc.databinding.FragmentDashboardBinding
import com.android.abc.databinding.FragmentRoadSideBinding
import java.lang.Exception


class RoadSideFragment : Fragment() {

    private var _binding: FragmentRoadSideBinding? = null
    private val binding get() = _binding!!
    private lateinit var setupActionBar: SetupActionBar
    private val partnerArray = arrayOf(
        "Rescue Service Company",
        "Partner 1",
        "Partner 2",
        "Partner 3"
    )
    private lateinit var drawerLocker: DrawerLocker

    override fun onResume() {
        super.onResume()
        setupToolBar()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        drawerLocker.unlockDrawer()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        try {
            setupActionBar = (activity as SetupActionBar)
            drawerLocker = (activity as DrawerLocker)
        } catch (e: Exception){
            throw ClassCastException(activity.toString() + " must implement MyInterface");
        }
    }

    private fun setupToolBar() {
        binding.toolBarLayout.title = ""
        setupActionBar.setup(binding.toolBarLayout, R.id.dashboardFragment)
    }

    override fun onDetach() {
        super.onDetach()
        drawerLocker.lockDrawer()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentRoadSideBinding.inflate(inflater, container, false)
        val adapter = ArrayAdapter(requireActivity(), R.layout.spinner_item, partnerArray)
        binding.spinner.adapter = adapter
        binding.buttonRequest.setOnClickListener {
            Toast.makeText(requireContext(), "Send request",Toast.LENGTH_LONG).show()
        }
        return binding.root
    }

}