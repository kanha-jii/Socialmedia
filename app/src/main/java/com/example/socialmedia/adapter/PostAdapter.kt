package com.example.socialmedia.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.socialmedia.ModelClass
import com.example.socialmedia.PostClass
import com.example.socialmedia.R
import com.example.socialmedia.loadImage
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.firebase.ui.firestore.ObservableSnapshotArray

class PostAdapter(options: FirestoreRecyclerOptions<PostClass>):
    FirestoreRecyclerAdapter<PostClass, PostAdapter.MyViewHolder>(options){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
        return MyViewHolder(view)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int, model: PostClass) {
        model.eventImage?.let { holder.eventImg.loadImage(it) }
        holder.eventNam.text = model.eventName
    }
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val eventImg: ImageView = itemView.findViewById<ImageView>(R.id.feed_post_image)
        val eventNam: TextView = itemView.findViewById<TextView>(R.id.post_author)
    }
}