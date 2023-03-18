package com.example.socialmedia.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.socialmedia.R
import com.example.socialmedia.loadImage

class EventAdapter : RecyclerView.Adapter<MyViewHolder>() {

    private val myDataList = listOf("Item 1", "Item 2", "Item 3", "Item 4", "Item 5")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = myDataList[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int {
        return myDataList.size
    }
}

class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(data: String) {
        itemView.findViewById<TextView>(R.id.itemName).text = data
        itemView.findViewById<ImageView>(R.id.itemImg).loadImage("")
    }
}



