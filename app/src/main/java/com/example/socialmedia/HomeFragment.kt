package com.example.socialmedia

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.socialmedia.adapter.EventAdapter
import com.example.socialmedia.adapter.PostAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class HomeFragment : Fragment() {
    public lateinit var myRecyclerView:RecyclerView
    private val db = FirebaseFirestore.getInstance()
    private val eventsRef = db.collection("posts")
    private lateinit var adapter:PostAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.home_fragment,container,false)
        setFirebaseAdapter(view)
        val pp = view.findViewById<FloatingActionButton>(R.id.fab)
        pp.setOnClickListener {
            Toast.makeText(context, "fab clicked", Toast.LENGTH_SHORT).show()
//            val intent = Intent(context,CreatePost::class.java)
//            startActivity(intent)
//            val intent = Intent(activity?.application ,CreatePost::class.java)
//            startActivity(intent)
//            startActivity(Intent(context, CreatePost::class.java))
            activity?.let{
                val intent = Intent (it, CreatePost::class.java)
                it.startActivity(intent)
            }
        }
        return view
    }
    private fun setFirebaseAdapter(view: View) {
        val query = eventsRef.orderBy("eventName", Query.Direction.ASCENDING)
        val options = FirestoreRecyclerOptions.Builder<PostClass>()
            .setQuery(query, PostClass::class.java)
            .build()
        adapter = PostAdapter(options)
        myRecyclerView =  view.findViewById<RecyclerView>(R.id.post_recycler)
        myRecyclerView.adapter = adapter
        myRecyclerView.layoutManager = LinearLayoutManager(context)
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }
    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }
}