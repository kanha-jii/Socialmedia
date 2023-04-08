package com.example.socialmedia

import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.icu.lang.UCharacter.IndicPositionalCategory.LEFT_AND_RIGHT
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Display.Mode
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper.*
import com.bumptech.glide.Glide
import com.example.socialmedia.fragments.EventsFragment
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask

class CreateEventFragment :Fragment() {
    lateinit var imageUri: Uri
    lateinit var nameEt: EditText
    var tempImage:String = ""
    private lateinit var  sr:StorageReference
    var currentTime = System.currentTimeMillis()
    private lateinit var  docRef: DocumentReference
    private val ff: FirebaseFirestore = FirebaseFirestore.getInstance()
    val noteRef = ff.collection("events")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.create,container,false)
        val imgView = view.findViewById<ImageView>(R.id.imageView2)
        var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // There are no request codes
                val data: Intent? = result.data
                imageUri = data?.data!!
                fun getFileExt(uri: Uri): String? {
                    val contentresolver = context?.contentResolver
                    val mimetypemap = MimeTypeMap.getSingleton()
                    if (contentresolver != null) {
                        return mimetypemap.getExtensionFromMimeType((contentresolver.getType(uri)))
                    }
                    return "jpg"
                }
                fun uploadData() {
                    sr= FirebaseStorage.getInstance().getReference("event images")
                    currentTime = System.currentTimeMillis()
                    val ref=
                        sr.child((currentTime.toString() + "." + getFileExt(imageUri)))
                    val uploadTask = ref.putFile(imageUri)
//                    uploadTask.addOnCompleteListener() {urlTaskCompleted->
//                        tempImage = urlTaskCompleted.result.toString()
//                        val md = ModelClass()
//                        Toast.makeText(context, tempImage, Toast.LENGTH_SHORT).show()
//                        md.eventImage = tempImage
//                        println(tempImage)
//                        println(tempImage)
//                        println(tempImage)
//                        println(tempImage)
//                        println(tempImage)
//                        println(tempImage)
//                        nameEt = view.findViewById(R.id.event_create_name)
//                        md.eventName = nameEt.text.toString()
//                        noteRef.document().set(md)
//                    }.addOnFailureListener {
//                        Toast.makeText(context, "faild", Toast.LENGTH_SHORT).show()
//                    }
                    uploadTask.addOnSuccessListener {  Uri->

                        val md = ModelClass()
                        Toast.makeText(context, tempImage, Toast.LENGTH_SHORT).show()
                        md.eventImage = tempImage
                        println(tempImage)
                        println(tempImage)
                        println(tempImage)
                        println(tempImage)
                        println(tempImage)
                        println(tempImage)
                        nameEt = view.findViewById(R.id.event_create_name)
                        md.eventName = nameEt.text.toString()
                        noteRef.document().set(md)
                    }
//                    Toast.makeText(context,currentTime.toString(),Toast.LENGTH_LONG).show()
                }
//                 Picasso.get().load(imageUri).into(imgView)
                val img2 = view.findViewById<ImageView>(R.id.imageView2)
                context?.let { Glide.with(it).load(imageUri).into(img2) }
                val btn = view.findViewById<Button>(R.id.upload)
                btn.setOnClickListener {
                    uploadData()

//                    var pathString  = "event images/"+currentTime+"."+getFileExt(imageUri)
//                    sr = FirebaseStorage.getInstance().getReference(pathString)
//                    Toast.makeText(context, pathString, Toast.LENGTH_SHORT).show()
//                    sr.downloadUrl.addOnSuccessListener {
//                        md.eventImage = it.toString()
//                        println(md.eventImage)
//                        Toast.makeText(context,it.toString(),Toast.LENGTH_SHORT).show()
//                    }.addOnFailureListener {
//                        Toast.makeText(context,it.toString(),Toast.LENGTH_SHORT).show()
//                    }



//                    val rr = EventsFragment()
//                    var md = ModelClass()
//                    md.img = imageUri
//                    md.eventName = "ef"
//                    rr.ii = rr.ii+md
//                    rr.myRecyclerView.adapter?.notifyDataSetChanged()

                    Toast.makeText(context, "successful upload", Toast.LENGTH_SHORT).show()
                    val fragM = fragmentManager?.beginTransaction()
                    fragM?.replace(R.id.frames,EventsFragment(LEFT_AND_RIGHT))
                    fragM?.addToBackStack(null)
                    fragM?.commit()
                }
            }
        }




        imgView.setOnClickListener() {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
//            startActivityForResult(intent,1)
//            launcher.launch(intent)
            resultLauncher.launch(intent)
        }
        return view

    }
}