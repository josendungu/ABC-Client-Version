package com.android.abc.fragments.scheduleValuation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.RecyclerView
import com.android.abc.R
import com.android.abc.fragments.details.CarDetailsAddFragmentDirections

class CarSelectAdapter: RecyclerView.Adapter<CarSelectAdapter.ViewHolder>() {

    var plateList: MutableList<String> = ArrayList()

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)   {

        private val plateTextView: TextView = itemView.findViewById(R.id.plate)
        private val imageButtonCancel: ImageButton = itemView.findViewById(R.id.imageButtonCancel)
        private val plateContainer: RelativeLayout = itemView.findViewById(R.id.plateContainer)

        fun bind(plate: String){
            plateTextView.text = plate
            imageButtonCancel.visibility = View.INVISIBLE

            plateContainer.setOnClickListener {
                val action = ScheduleCarDetailsFragmentDirections.actionScheduleCarDetailsToScheduleClientDetails(plate)
                itemView.findNavController().navigate(action)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.car_recycler_item,  parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(plateList[position])
    }

    override fun getItemCount(): Int = plateList.size

}