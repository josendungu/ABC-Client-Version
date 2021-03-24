package com.android.abc.fragments

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.android.abc.R
import com.android.abc.data.models.Client
import com.android.abc.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<DashboardFragmentArgs>()

    private lateinit var client: Client
    private var scheduleSuccess: Boolean = false
    private var clientDetailsAdd: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)

        client = args.clientDetails
        scheduleSuccess = args.sheduleSuccess
        clientDetailsAdd = args.clientAddSuccess

        if (clientDetailsAdd){
            binding.addedPrompt.visibility = View.VISIBLE
            binding.addedPrompt.text = getString(R.string.client_added)
            hideMessage()
        }

        if (scheduleSuccess){
            binding.addedPrompt.visibility = View.VISIBLE
            hideMessage()
        }


        val toolbar = binding.toolBarLayout
        (activity as AppCompatActivity?)!!.setSupportActionBar(toolbar)
        toolbar.title = null

        binding.schedule.setOnClickListener {
            val action = DashboardFragmentDirections.actionDashboardToScheduleCarDetails(client)
            findNavController().navigate(action)
        }



        return binding.root
    }

    private fun hideMessage(){
        Handler().postDelayed({
            binding.addedPrompt.visibility = View.GONE
        }, 10000)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}