package com.example.socialmedia.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.socialmedia.R
import com.example.socialmedia.adapter.EventAdapter

class EventsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.events_fragment,container,false)
        setAdapter(view)
        return view
    }

    private fun setAdapter(view: View) {
        val myRecyclerView =  view.findViewById<RecyclerView>(R.id.listRV)
        var myAdapter = EventAdapter()
        myRecyclerView.adapter = myAdapter
        myRecyclerView.layoutManager = LinearLayoutManager(context)
    }

}