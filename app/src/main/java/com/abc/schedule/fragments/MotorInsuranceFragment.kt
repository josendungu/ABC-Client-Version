package com.abc.schedule.fragments

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.DatePicker
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.abc.schedule.R
import com.abc.schedule.activity.DrawerLocker
import com.abc.schedule.activity.SetupActionBar
import com.abc.schedule.data.models.Client
import com.abc.schedule.data.viewmodel.ClientDetailsViewModel
import com.abc.schedule.databinding.FragmentMotorInsuranceBinding
import com.abc.schedule.utils.GMailSender
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import java.util.*

class MotorInsuranceFragment : Fragment(), DatePickerDialog.OnDateSetListener {
    private var _binding: FragmentMotorInsuranceBinding? = null
    private val binding get() = _binding!!
    private lateinit var drawerLocker: DrawerLocker
    private lateinit var setupActionBar: SetupActionBar
    private lateinit var client: Client
    var day = 0
    private var month: Int = 0
    private var year: Int = 0
    var myDay = 0
    var myMonth: Int = 0
    var myYear: Int = 0

    private val insuranceType = arrayOf(
        "Select Insurance Type",
        "Private",
        "Commercial",
        "PSV"
    )

    private val coverType = arrayOf(
        "Select Cover Type",
        "Comprehensive",
        "Third Party",
        "Third Party and Fire"

    )

    private lateinit var make : String
    private lateinit var model : String
    private lateinit var manufactureYear : String
    private var cover : String = coverType[0]
    private var insurance : String = insuranceType[0]
    private var startDate: String = ""

    private var sender = "drive.abcautovaluersltd@gmail.com"
    private var passSender = "Denniswhyt1"



    private val mClientDetailsViewModel: ClientDetailsViewModel by viewModels()


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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMotorInsuranceBinding.inflate(inflater,container, false)

        val insuranceAdapter = ArrayAdapter(requireActivity(), R.layout.spinner_item, insuranceType)
        binding.spinnerInsuranceType.adapter = insuranceAdapter

        val coverAdapter = ArrayAdapter(requireActivity(), R.layout.spinner_item, coverType)
        binding.spinnerCoverType.adapter = coverAdapter

        binding.spinnerInsuranceType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position != 0) {
                    insurance = parent?.getItemAtPosition(position) as String
                } else {
                    insurance = insuranceType[0]
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }



        binding.spinnerCoverType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                cover = if (position != 0) {
                    parent?.getItemAtPosition(position) as String
                } else {
                    coverType[0]
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        mClientDetailsViewModel.fetchClientData().observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            client = it
        })

        binding.editTextDate.setOnClickListener {
            pickDate()
        }


        binding.buttonPurchase.setOnClickListener {
            setErrorNull()

            make = binding.editTextVehicleMake.text.toString()
            model = binding.editTextVehicleModel.text.toString()
            manufactureYear = binding.editTextYear.text.toString()

            if (validateData()){
                binding.emailProgress.visibility = View.VISIBLE
                CoroutineScope(IO).launch {
                    sendEmail()
                }

            }

        }

        return binding.root
    }

    private suspend  fun sendEmail() {
        val gMailSender =  GMailSender(sender, passSender)
        val subject = "Purchase Insurance: ${client.firstName} ${client.lastName}"
        val body = "Name: ${client.firstName} ${client.lastName} \n" +
                "Email: ${client.email} \n" +
                "Phone Number: ${client.phoneNumber} \n\n" +
                "Vehicle Make: $make \n" +
                "Vehicle Model: $model \n" +
                "Year Manufactured: $manufactureYear \n\n"+
                "Insurance Type: $insurance \n" +
                "Cover Type: $cover \n" +
                "Start Date: $startDate \n"

        //puriemunyoki@gmail.com
        try {
            gMailSender.sendMail(subject, body, sender, "primeriskinsuranceagency@gmail.com")
            sendSuccess()
        } catch (e: Exception){
            sendError()
            Log.d("SendingEmail", "sendEmail: $e")
        }
    }

    private suspend fun sendSuccess() {
        withContext(Main){
            binding.emailProgress.visibility = View.GONE
            val message = "Your purchase request was successfully received. One of our agents will contact you soon."
            val snackBar = Snackbar.make(binding.snackBarContainer,message , Snackbar.LENGTH_INDEFINITE)
            snackBar.setAction("OK") {
                findNavController().navigate(R.id.dashboardFragment)
            }
            snackBar.show()
        }
    }

    private suspend fun sendError() {
        withContext(Main){
            binding.emailProgress.visibility = View.GONE
            val message = "There has been a problem while sending your purchase request. Please try again later"
            val snackBar = Snackbar.make(binding.snackBarContainer,message , Snackbar.LENGTH_INDEFINITE)
            snackBar.setAction("OK") {
                findNavController().navigate(R.id.action_scheduleDay_to_dashboard)
            }
            snackBar.setAction("RETRY NOW") {
                CoroutineScope(IO).launch {
                    sendEmail()
                }
            }
            snackBar.show()
        }
    }

    private fun setupToolBar() {
        binding.toolBarLayout.title = ""
        setupActionBar.setup(binding.toolBarLayout, R.id.dashboardFragment)
    }


    private fun validateData(): Boolean {

        return when {
            make.isBlank() -> {
                binding.editTextVehicleMake.error = "Field can't be empty"
                false
            }
            model.isBlank() -> {
                binding.editTextVehicleModel.error = "Field can't be empty"
                false
            }
            manufactureYear.isBlank() -> {
                binding.editTextYear.error = "Field can't be empty"
                false
            }
            cover == coverType[0] -> {
                binding.spinnerErrorCover.text = getString(R.string.select_field)
                binding.spinnerErrorCover.visibility = View.VISIBLE
                false
            }
            insurance == insuranceType[0] -> {
                binding.spinnerErrorInsurance.text = getString(R.string.select_field)
                binding.spinnerErrorInsurance.visibility = View.VISIBLE
                false
            }
            startDate.isBlank() -> {
                binding.editTextDate.error = "Click to select date"
                false
            }
            else -> {
                true
            }
        }

    }

    private fun setErrorNull() {
        binding.editTextVehicleMake.error = null
        binding.editTextVehicleModel.error = null
        binding.spinnerErrorCover.visibility = View.GONE
        binding.spinnerErrorInsurance.visibility = View.GONE
        binding.editTextDate.error = null
        binding.editTextYear.error = null

    }

    override fun onStop() {
        super.onStop()
        drawerLocker.unlockDrawer()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getCalender(): Calendar {
        return Calendar.getInstance()
    }

    private fun getDateTimeCalender() {
        val calendar = getCalender()
        day = calendar.get(Calendar.DAY_OF_MONTH)
        month = calendar.get(Calendar.MONTH)
        year = calendar.get(Calendar.YEAR)

    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        myDay = dayOfMonth
        myYear = year
        myMonth = month+1
        startDate = "$myDay-$myMonth-$myYear"
        binding.editTextDate.text = startDate
    }

    private fun pickDate(){

        binding.editTextDate.setOnClickListener{
            getDateTimeCalender()
            val datePickerDialog = DatePickerDialog(requireContext(), this, year, month,day)
            datePickerDialog.datePicker.minDate = getCalender().timeInMillis
            datePickerDialog.show()

        }

    }

}