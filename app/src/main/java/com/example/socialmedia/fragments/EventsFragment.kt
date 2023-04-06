package com.example.socialmedia.fragments

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.manager.SupportRequestManagerFragment
import com.example.socialmedia.CreateEventFragment
import com.example.socialmedia.ModelClass
import com.example.socialmedia.R
import com.example.socialmedia.adapter.EventAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage

class EventsFragment : Fragment() {

        var codeExecuted = false
        public lateinit var ii:MutableList<ModelClass>
        public lateinit var myRecyclerView:RecyclerView
        private lateinit var myAdapter:EventAdapter

    lateinit var imageUri: Uri
//    val dialog = context?.let { it1 -> Dialog(it1) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.events_fragment,container,false)
//        handleEvent(view)
        setAdapter(view)
//        setAdapter(view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(!codeExecuted) {
            handleEvent(view)
            codeExecuted = true
        }
        val createEvent:Button = view.findViewById<Button>(R.id.create_event)
        createEvent.setOnClickListener() {
            val fragM = fragmentManager?.beginTransaction()
            fragM?.replace(R.id.frames,CreateEventFragment())
            fragM?.addToBackStack(null)
            fragM?.commit()
            // Get a reference to the Firebase Storage instance





//            val md = ModelClass()
//            md.img = Uri.parse("https://images.unsplash.com/photo-1599580506193-fef9dc35b205?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=871&q=80")
//            md.eventName = "second event"
//            ii.add(md)
//            myAdapter.notifyItemInserted(ii.size -1)
//            val dia = PopUpFragment()
//            dia.show((activity as AppCompatActivity).supportFragmentManager,"show pop-up")
//            setFragmentResultListener("data") { s: String, bundle: Bundle ->
//                val res = bundle.getString("datakey")
//                createEvent.text = res
//            }

//            createEvent.text = dia.txt
//           createEvent.text = dia.view?.findViewById<TextView>(R.id.blank)?.text ?: "hii"
//            createEvent.text = dia.dialog?.findViewById<TextView>(R.id.blank)?.text ?: "hii"
        }
    }


    private fun handleEvent(view: View) {
        val createEvent:Button
        createEvent =  view.findViewById<Button>(R.id.create_event)
        val user = FirebaseAuth.getInstance().currentUser
        user.let {
            val email = user?.email
            createEvent.isVisible = email == "kanha@gmail.com"

            if(email == "kanha@gmail.com" || email=="jkjk@gmail.com") {
                val storage = FirebaseStorage.getInstance()


// Get a reference to the folder that contains the images
                val imagesRef = storage.getReference("event images/")

// Get a list of all the items (images) in the folder
                imagesRef.listAll()
                    .addOnSuccessListener { listResult ->
                        // Loop through all the items (images) in the folder
                        for (item in listResult.items) {
                            // Get the download URL of the item (image)
                            item.downloadUrl
                                .addOnSuccessListener { url ->
                                    val md = ModelClass()
                                    md.img = url
                                    Toast.makeText(context,url.toString(),Toast.LENGTH_SHORT).show()
                                    md.eventName = "gotted"
                                    ii.add(md)
                                    println(url)
                                    println(item.path)
                                    myAdapter.notifyItemInserted(ii.size-1)
                                }
                        }
                    }
            }
        }
//         if(user == ){
//             createEvent.isVisible = true
//         }else
//             createEvent.isVisible = false


    }

    private fun setAdapter(view: View) {
        myRecyclerView =  view.findViewById<RecyclerView>(R.id.listRV)
        val md = ModelClass()
        md.img = Uri.parse("https://images.unsplash.com/photo-1599580506193-fef9dc35b205?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=871&q=80")
        md.eventName = "first event"
        ii= arrayListOf(md)

        myAdapter = EventAdapter(ii)
        myRecyclerView.adapter = myAdapter
//        myAdapter.setList((activity as HomeActivity).myDataList)
        myRecyclerView.layoutManager = LinearLayoutManager(context)
    }




}