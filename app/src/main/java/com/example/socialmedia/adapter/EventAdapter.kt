package com.example.socialmedia.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.socialmedia.ModelClass
import com.example.socialmedia.R
import com.example.socialmedia.loadImage
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.firebase.ui.firestore.ObservableSnapshotArray
import com.google.firebase.firestore.DocumentSnapshot

/*class EventAdapter(private val items:MutableList<ModelClass>) : RecyclerView.Adapter<MyViewHolder>() {

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
*/

class EventAdapter(options: FirestoreRecyclerOptions<ModelClass>) :
        FirestoreRecyclerAdapter<ModelClass,EventAdapter.MyViewHolder>(options) {
    interface OnItemClickListener {
        fun onItemClick(documentSnapshot: DocumentSnapshot,position: Int)
    }
    private lateinit var mlistener:OnItemClickListener


    fun setOnItemClickListener(listener: OnItemClickListener) {
        mlistener = listener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return MyViewHolder(view,mlistener,snapshots)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int, model: ModelClass) {
        // bind data to views inside view holder
        model.eventImage?.let { holder.eventImg.loadImage(it) }
        holder.eventNam.text = model.eventName
    }
    fun deleteItem(position: Int) {
        snapshots.getSnapshot(position).reference.delete()
    }
    class MyViewHolder(
        itemView: View,
        listener: OnItemClickListener,
        snapshot: ObservableSnapshotArray<ModelClass>
    ) : RecyclerView.ViewHolder(itemView) {
        val eventImg: ImageView = itemView.findViewById<ImageView>(R.id.itemImg)
        init {
            itemView.setOnClickListener {
                val position = absoluteAdapterPosition
                if(position != RecyclerView.NO_POSITION && listener != null) {

                    listener.onItemClick(snapshot.getSnapshot(position),position)
                }
            }
        }
        val eventNam: TextView = itemView.findViewById<TextView>(R.id.itemName)

    }

}

