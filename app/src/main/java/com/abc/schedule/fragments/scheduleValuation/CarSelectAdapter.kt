package com.abc.schedule.fragments.scheduleValuation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.abc.schedule.R
import com.abc.schedule.data.models.Client
import com.abc.schedule.data.models.ScheduleDetails

class CarSelectAdapter: RecyclerView.Adapter<CarSelectAdapter.ViewHolder>() {

    var plateList: List<String>? = emptyList()
    private lateinit var client: Client

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)   {

        private val plateTextView: TextView = itemView.findViewById(R.id.plate)
        private val imageButtonCancel: ImageButton = itemView.findViewById(R.id.imageButtonCancel)
        private val plateContainer: RelativeLayout = itemView.findViewById(R.id.plateContainer)

        fun bind(plate: String){
            plateTextView.text = plate
            val scheduleDetails = ScheduleDetails()
            scheduleDetails.plateNumber = plate
            imageButtonCancel.visibility = View.INVISIBLE

            plateContainer.setOnClickListener {
                val action = ScheduleCarDetailsFragmentDirections.actionScheduleCarDetailsToScheduleClientDetails(scheduleDetails, client)
                itemView.findNavController().navigate(action)
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.car_recycler_item,  parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (plateList != null){
            holder.bind(plateList!![position])
        }
    }

    override fun getItemCount(): Int = plateList!!.size



    fun setData(plates: MutableList<String>, client: Client){
        this.plateList = plates
        this.client = client
        notifyDataSetChanged()
    }

}