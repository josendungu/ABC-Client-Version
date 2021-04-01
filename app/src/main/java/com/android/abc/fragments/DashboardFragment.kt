package com.android.abc.fragments

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.android.abc.R
import com.android.abc.activity.ClearStack
import com.android.abc.activity.SetupActionBar
import com.android.abc.data.models.Client
import com.android.abc.databinding.FragmentDashboardBinding
import java.lang.Exception

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<DashboardFragmentArgs>()

    private lateinit var client: Client
    private var scheduleSuccess: Boolean = false
    private var clientDetailsAdd: Boolean = false

    private lateinit var setupActionBar: SetupActionBar

    override fun onAttach(context: Context) {
        super.onAttach(context)

        try {
            setupActionBar = (activity as SetupActionBar)
        } catch (e: Exception){
            throw ClassCastException(activity.toString() + " must implement MyInterface");
        }
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)

        scheduleSuccess = args.scheduleSuccess
        clientDetailsAdd = args.clientAddSuccess

        client = args.clientDetails
        val clientName = "${client.firstName} ${client.lastName}"

        Log.d("ClientDetails", "onCreateView: ${client.surname}")

        setupToolBar()

        if (clientDetailsAdd){
            binding.addedPrompt.visibility = View.VISIBLE
            binding.addedPrompt.text = getString(R.string.client_added)
            hideMessage()
        }

        if (scheduleSuccess){
            binding.addedPrompt.visibility = View.VISIBLE
            hideMessage()
        }

        binding.schedule.setOnClickListener {
            val action = DashboardFragmentDirections.actionDashboardToScheduleCarDetails(client)
            findNavController().navigate(action)
        }



        return binding.root
    }

    private fun setupToolBar() {
        binding.toolBarLayout.title = ""
        setupActionBar.setup(binding.toolBarLayout, R.id.dashboardFragment)
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