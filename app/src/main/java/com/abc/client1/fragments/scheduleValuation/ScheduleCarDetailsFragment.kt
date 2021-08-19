package com.abc.client1.fragments.scheduleValuation

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.abc.client1.activity.DrawerLocker
import com.abc.client1.data.models.Client
import com.abc.client1.databinding.FragmentScheduleCarDetailsBinding
import java.lang.Exception


class ScheduleCarDetailsFragment : Fragment() {

    private var _binding: FragmentScheduleCarDetailsBinding? = null
    private val binding get() = _binding!!

    private val adapter = CarSelectAdapter()
    private lateinit var client: Client
    private var platesUpdated: Boolean = false

    private val args by navArgs<ScheduleCarDetailsFragmentArgs>()

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
        _binding = FragmentScheduleCarDetailsBinding.inflate(inflater, container, false)

        client = args.clientDetails
        platesUpdated = args.platesUpdated
        adapter.setData(client.plates!!, client)

        drawerLocker.lockDrawer()

        binding.buttonAdd.setOnClickListener {
            val action = ScheduleCarDetailsFragmentDirections.actionScheduleCarDetailsFragmentToCarDetailsAddFragment(client, true)
            findNavController().navigate(action)
        }
        setUpRecyclerView()

        return binding.root
    }

    private fun setUpRecyclerView() {
        binding.carRecycler.adapter = adapter
        binding.carRecycler.layoutManager = LinearLayoutManager(requireActivity())
    }

    override fun onStop() {
        super.onStop()
        drawerLocker.unlockDrawer()

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}