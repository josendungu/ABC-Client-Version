package com.android.abc.fragments

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.navArgs
import com.android.abc.R
import com.android.abc.activity.DrawerLocker
import com.android.abc.activity.SetupActionBar
import com.android.abc.data.models.Client
import com.android.abc.databinding.FragmentContactBinding
import com.android.abc.databinding.FragmentDashboardBinding
import java.lang.Exception

class ContactFragment : Fragment() {

    private val REQUEST_CALL = 1
    private var _binding: FragmentContactBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<DashboardFragmentArgs>()

    private lateinit var client: Client
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
    ): View? {
        _binding = FragmentContactBinding.inflate(inflater, container, false)

        setupToolBar()
        client = args.clientDetails

        binding.phoneOne.setOnClickListener {

            binding.message.text = getString(R.string.call)
            binding.message.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))

            val callIntent = Intent(Intent.ACTION_CALL)
            callIntent.data = Uri.parse("tel:${resources.getString(R.string.phone_1)}")

            if (checkCallPermission()){
                startActivity(callIntent)
            } else {
                binding.message.text = getString(R.string.call_permission)
                binding.message.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))
            }
        }

        binding.phoneTwo.setOnClickListener {

            binding.message.text = getString(R.string.call_permission)
            binding.message.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))

            val callIntent = Intent(Intent.ACTION_CALL)
            callIntent.data = Uri.parse("tel:${resources.getString(R.string.phone_2)}")

            if (checkCallPermission()){
                startActivity(callIntent)
            } else {
                binding.message.text = getString(R.string.call)
                binding.message.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            }
        }

        return binding.root
    }

    private fun checkCallPermission() : Boolean {
        return if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.CALL_PHONE),
                REQUEST_CALL
            )

            ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED


        } else {
            true
        }
    }


    private fun setupToolBar() {
        binding.toolBarLayout.title = ""
        setupActionBar.setup(binding.toolBarLayout, R.id.dashboardFragment)
    }


}