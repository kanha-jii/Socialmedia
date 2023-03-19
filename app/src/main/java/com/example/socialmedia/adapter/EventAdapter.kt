package com.example.socialmedia.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.socialmedia.R
import com.example.socialmedia.loadImage

class EventAdapter(val items:List<String>) : RecyclerView.Adapter<MyViewHolder>() {

    private lateinit var myDataList: List<String>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        val myHolder = MyViewHolder(view)
//        view.setOnClickListener() {
//            listener.onItemClicked(items[myHolder.adapterPosition])
//        }
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = myDataList[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int {
        return myDataList.size
    }

    fun setList(list: List<String>) {
        myDataList = list
    }
}

class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(data: String) {
        itemView.findViewById<TextView>(R.id.itemName).text = data
//        itemView.findViewById<ImageView>(R.id.itemImg).loadImage("")
    }
}
interface MyViewHolderItemClicked {
    fun onItemClicked(item: String)
}


