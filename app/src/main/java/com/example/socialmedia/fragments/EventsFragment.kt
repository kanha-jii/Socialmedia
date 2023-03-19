package com.example.socialmedia.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.socialmedia.CreateEventFragment
import com.example.socialmedia.HomeActivity
import com.example.socialmedia.R
import com.example.socialmedia.adapter.EventAdapter
import com.google.firebase.auth.FirebaseAuth

class EventsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.events_fragment,container,false)
        handleEvent(view)
        setAdapter(view)
        return view
    }

    private fun handleEvent(view: View) {
        val createEvent =  view.findViewById<Button>(R.id.create_event)
        val user = FirebaseAuth.getInstance().currentUser
        user.let {
            val email = user?.email
            createEvent.isVisible = email == "kanha@gmail.com"
        }
//         if(user == ){
//             createEvent.isVisible = true
//         }else
//             createEvent.isVisible = false

        createEvent.setOnClickListener {
            Toast.makeText(context, "create event ", Toast.LENGTH_SHORT).show()
//             childFragmentManager.beginTransaction().replace(R.id.frames,CreateEventFragment()).addToBackStack(null).commit()
            fragmentManager?.beginTransaction()?.replace(R.id.frames,CreateEventFragment())
                ?.addToBackStack(null)
                ?.commit()

        }


    }

    private fun setAdapter(view: View) {
        val myRecyclerView =  view.findViewById<RecyclerView>(R.id.listRV)
        val ii: List<String> = listOf("we","er")
        var myAdapter = EventAdapter(ii)
        myRecyclerView.adapter = myAdapter
        myAdapter.setList((activity as HomeActivity).myDataList)
        myRecyclerView.layoutManager = LinearLayoutManager(context)
    }

}