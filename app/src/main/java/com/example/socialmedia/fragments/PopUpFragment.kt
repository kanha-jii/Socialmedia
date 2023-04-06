package com.example.socialmedia.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
//import androidx.fragment.app.setFragmentResult
import com.example.socialmedia.R

class PopUpFragment : DialogFragment() {
public var  txt:String = "hii"
//    private var mListener: MyDialogListener? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val tx = view?.findViewById<TextView>(R.id.blank)
        txt = tx?.text as String
//        mListener?.onDataReceived(txt)
//        tx.setOnClickListener {
//            setFragmentResult("datakey", bundleOf("data" to txt))
//        }

        return inflater.inflate(R.layout.fragment_pop_up, container, false)
    }
//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        mListener = try {
//            context as MyDialogListener
//        } catch (e: ClassCastException) {
//            throw ClassCastException("$context must implement MyDialogListener")
//        }
//    }

}