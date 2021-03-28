package com.android.abc.fragments.scheduleValuation

import android.animation.TimeAnimator
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.text.format.DateFormat.is24HourFormat
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.android.abc.R
import com.android.abc.activity.DrawerLocker
import com.android.abc.data.models.Client
import com.android.abc.data.models.ScheduleDetails
import com.android.abc.databinding.FragmentScheduleCarDetailsBinding
import com.android.abc.databinding.FragmentScheduleDayBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import java.lang.Exception
import java.text.DateFormat
import java.util.*


class ScheduleDayFragment : Fragment(), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private var _binding: FragmentScheduleDayBinding? = null
    private val binding get() = _binding!!

    var day = 0
    private var month: Int = 0
    private var year: Int = 0
    var hour: Int = 0
    var minute: Int = 0
    var myDay = 0
    var myMonth: Int = 0
    var myYear: Int = 0
    var myHour: Int = 0
    var myMinute: Int = 0

    private var finalTime: String? = null
    private var finalDate: String? = null

    private val args by navArgs<ScheduleDayFragmentArgs>()

    private lateinit var scheduleDetails: ScheduleDetails
    private lateinit var client: Client

    private lateinit var drawerLocker: DrawerLocker

    override fun onAttach(context: Context) {
        super.onAttach(context)

        try {
            drawerLocker = (activity as DrawerLocker)
        } catch (e: Exception){
            throw ClassCastException(activity.toString() + " must implement MyInterface")
        }

    }

    override fun onStop() {
        super.onStop()
        drawerLocker.unlockDrawer()

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScheduleDayBinding.inflate(inflater,container, false)

        scheduleDetails = args.scheduleDetails
        client = args.clientDetails
        drawerLocker.lockDrawer()


        binding.plateNumber.text = scheduleDetails.plateNumber

        pickTime()

        binding.buttonCancel.setOnClickListener {
            val action = ScheduleDayFragmentDirections.actionScheduleDayToDashboard(client)
            findNavController().navigate(action)
        }


        binding.buttonNext.setOnClickListener {

            binding.editTextDate.error = null

            when {
                finalDate == null -> {
                    binding.editTextDate.error = "Please set the date"
                }
                finalTime == null -> {
                    binding.editTextDate.error = "Please set the time"
                }
                else -> {

                    binding.progressBar.visibility = View.VISIBLE

                    scheduleDetails.day = finalDate
                    scheduleDetails.time = finalTime

                    val firebaseDatabase = FirebaseDatabase.getInstance()
                    val databaseReference = firebaseDatabase.getReference("ScheduledValuations/")

                    databaseReference.push().setValue(scheduleDetails)
                        .addOnSuccessListener {
                            val action = ScheduleDayFragmentDirections.actionScheduleDayToDashboard(client, true)
                            findNavController().navigate(action)

                        }
                        .addOnFailureListener {
                            Snackbar.make(requireContext(),requireView(), "Error: $it ", Snackbar.LENGTH_LONG).show()
                            binding.progressBar.visibility = View.INVISIBLE

                        }


                }
            }
        }

        return binding.root
    }


    private fun pickTime(){

        binding.editTextDate.setOnClickListener{
            getDateTimeCalender()
            val datePickerDialog = DatePickerDialog(requireContext(), this, year, month,day)
            datePickerDialog.datePicker.minDate = getCalender().timeInMillis
            datePickerDialog.show()

        }

    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        myDay = dayOfMonth
        myYear = year
        myMonth = month

        getDateTimeCalender()
        TimePickerDialog(requireContext(), this, hour, minute, true).show()
    }
    private fun getCalender(): Calendar{
        return Calendar.getInstance()
    }

    private fun getDateTimeCalender() {
        val calendar = getCalender()
        day = calendar.get(Calendar.DAY_OF_MONTH)
        month = calendar.get(Calendar.MONTH)
        year = calendar.get(Calendar.YEAR)
        hour = calendar.get(Calendar.HOUR)
        minute = calendar.get(Calendar.MINUTE)
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        myHour = hourOfDay
        myMinute = minute
        finalDate = "$myDay-$myMonth-$myYear"
        finalTime = "$myHour:$myMinute"
        val dateTimeValue  = "At $finalTime on the $finalDate"
        binding.editTextDate.text = dateTimeValue

    }

}