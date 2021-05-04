package com.abc.client.fragments.details

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.abc.client.R

class CarRecyclerAdapter: RecyclerView.Adapter<CarRecyclerAdapter.ViewHolder>() {

    var plateList: MutableList<String> = ArrayList()
    inner class ViewHolder(item: View): RecyclerView.ViewHolder(item) {

        private val plateTextView: TextView = itemView.findViewById(R.id.plate)
        private val imageButtonCancel: ImageButton = itemView.findViewById(R.id.imageButtonCancel)


        fun bind(plate: String, position: Int){
            plateTextView.text = plate

            imageButtonCancel.setOnClickListener {
                plateList.removeAt(position)
                notifyDataSetChanged()
            }
        }



    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CarRecyclerAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.car_recycler_item,  parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: CarRecyclerAdapter.ViewHolder, position: Int) {
        holder.bind(plateList[position], position)
    }

    override fun getItemCount(): Int = plateList.size

    fun setData(plates: MutableList<String>){
        this.plateList = plates
        notifyDataSetChanged()
    }
}