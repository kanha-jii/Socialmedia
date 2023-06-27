package com.example.socialmedia

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AccountFragment : Fragment(), View.OnClickListener  {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.account_fragment,container,false)
        val btn: Button = view.findViewById(R.id.btn_edit_profile)
        btn.setOnClickListener(this)
        val btn2: Button = view.findViewById(R.id.btn_out)
        btn2.setOnClickListener(this)
        val db = Firebase.firestore
        val collectionRef = db.collection("users").orderBy("priority")
        collectionRef.get()
            .addOnSuccessListener { querySnapshot ->
                for (document in querySnapshot.documents) {
                    val myField = document.getString("current-user")
                    if(FirebaseAuth.getInstance().currentUser?.uid.toString() == myField) {
                        Toast.makeText(context, "uid matched", Toast.LENGTH_SHORT).show()
                        setData(view,document)
                        break
                    }
                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(context, "snapshot failed", Toast.LENGTH_SHORT).show()
            }
        return view
    }


    override  fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_edit_profile -> {
                val intent = Intent(activity?.application ,CreateProfile::class.java)
                startActivity(intent)
            }
            R.id.btn_out -> {
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(activity?.application ,RegisterActivity::class.java)
                startActivity(intent)
            }
        }
    }
    private fun setData(view: View,document:DocumentSnapshot) {
        view.findViewById<ImageView>(R.id.profile_image).loadImage(document["image"].toString())

    }

//    fun openProfile(view: View) {
//        val intent = Intent(activity?.application ,CreateProfile::class.java)
//        startActivity(intent)
//    }

}