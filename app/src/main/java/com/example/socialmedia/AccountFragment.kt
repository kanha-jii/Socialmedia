package com.example.socialmedia

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth

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


//    fun openProfile(view: View) {
//        val intent = Intent(activity?.application ,CreateProfile::class.java)
//        startActivity(intent)
//    }

}