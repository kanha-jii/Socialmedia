package com.example.socialmedia.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.socialmedia.ModelClass
import com.example.socialmedia.R
import com.example.socialmedia.loadImage

class EventAdapter(private val items:MutableList<ModelClass>) : RecyclerView.Adapter<MyViewHolder>() {

//    private var myDataList: List<ModelClass> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        val myHolder = MyViewHolder(view)
//        view.setOnClickListener() {
//            listener.onItemClicked(items[myHolder.adapterPosition])
//        }
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//        val data = myDataList[position]
        val data = items[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int {
//        return myDataList.size
        return items.size
    }
    public fun addData(newData: ModelClass) {
        items.add(newData)
        notifyDataSetChanged()
    }

//    fun setList(list: List<String>) {
//        myDataList = list
//    }
}

class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(data: ModelClass) {
        itemView.findViewById<TextView>(R.id.itemName).text = data.eventName
        itemView.findViewById<ImageView>(R.id.itemImg).loadImage(data.img.toString())
    }
//    val img = itemView.findViewById<ImageView>(R.id.itemImg)
//    val txt = itemView.findViewById<TextView>(R.id.itemName)

}
interface MyViewHolderItemClicked {
    fun onItemClicked(item: String)
}


