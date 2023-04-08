package com.example.socialmedia

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
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
import com.bumptech.glide.Glide
import com.example.socialmedia.fragments.EventsFragment
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class CreateEventFragment :Fragment() {
    private lateinit var imageUri: Uri
    private lateinit var nameEt: EditText
    private var tempImage:String = ""
    private lateinit var  sr:StorageReference
    private var currentTime = System.currentTimeMillis()
    private val ff: FirebaseFirestore = FirebaseFirestore.getInstance()
    val noteRef = ff.collection("events")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.create,container,false)
        val imgView = view.findViewById<ImageView>(R.id.imageView2)
        val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
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
                    sr= FirebaseStorage.getInstance().getReference("event images/")
                    currentTime = System.currentTimeMillis()
                    val ref=
                        sr.child((currentTime.toString() + "." + getFileExt(imageUri)))
                    val uploadTask = ref.putFile(imageUri)

//                    sr.listAll().addOnSuccessListener {
//                        for(item in it.items) {
//                            println(item.name)
//                            println("$currentTime.jpg")
//                            if(item.name == "1680962337435.jpg") {
//                                item.downloadUrl.addOnSuccessListener {usl ->
//                                    val md = ModelClass()
//                                    md.eventImage = usl.toString()
//                                    md.eventName = nameEt.text.toString()
//                                    Toast.makeText(context, "found image", Toast.LENGTH_SHORT).show()
//                                }
//                                break
//                            }
//                        }
//                    }
                    val storage = FirebaseStorage.getInstance().reference.child("event images")
                        .child("$currentTime.jpg")
                    uploadTask.continueWithTask() {
                        if(!it.isSuccessful) {
                            Toast.makeText(context, "not executed", Toast.LENGTH_SHORT).show()
                        }
                        storage.downloadUrl
                    }.addOnCompleteListener() {urlTaskCompleted->
                        tempImage = urlTaskCompleted.result.toString()
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
                    }.addOnFailureListener {
                        Toast.makeText(context, "faild", Toast.LENGTH_SHORT).show()
                    }
//                    uploadTask.addOnSuccessListener {Uri->
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
//                    }
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
                    val fragM = activity?.supportFragmentManager?.beginTransaction()
                    fragM?.replace(R.id.frames,EventsFragment())
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