package com.example.socialmedia

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class CreatePost : AppCompatActivity() {
    var tempImage:String = ""
    lateinit var btnPost:Button
    var currentTime = System.currentTimeMillis()
    lateinit var  sr: StorageReference
    lateinit var imageUri: Uri
    lateinit var imgView:ImageView
    val ff: FirebaseFirestore = FirebaseFirestore.getInstance()
    var noteRef = ff.collection("posts")
    lateinit var postText:EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_post)
        fun getFileExt(uri: Uri): String? {
            val contentresolver = this?.contentResolver
            val mimetypemap = MimeTypeMap.getSingleton()
            if (contentresolver != null) {
                return mimetypemap.getExtensionFromMimeType((contentresolver.getType(uri)))
            }
            return "jpg"
        }
        btnPost = findViewById(R.id.btn_post)
        postText = findViewById<EditText>(R.id.post_text)
        imgView = findViewById(R.id.post_image)
        fun uploadData() {
            val sr= FirebaseStorage.getInstance().getReference("post images/")
            currentTime = System.currentTimeMillis()
            val ref=
                sr.child((currentTime.toString() + "." + getFileExt(imageUri)))
            val uploadTask = ref.putFile(imageUri)
            val storage = FirebaseStorage.getInstance().reference.child("post images")
                .child("$currentTime.jpg")
            uploadTask.continueWithTask() {
                if(!it.isSuccessful) {
                    Toast.makeText(this, "not executed", Toast.LENGTH_SHORT).show()
                }
                storage.downloadUrl
            }.addOnCompleteListener() {urlTaskCompleted->
                tempImage = urlTaskCompleted.result.toString()
                val pd = PostClass()
                Toast.makeText(this, tempImage, Toast.LENGTH_SHORT).show()
                pd.eventImage = tempImage
//                val nameEt = findViewById<EditText>(R.id.post_text)
                pd.eventName = postText.text.toString()
                noteRef.document().set(pd)
            }.addOnFailureListener {
                Toast.makeText(this, "faild", Toast.LENGTH_SHORT).show()
            }
        }
        btnPost.setOnClickListener {
            val text = postText.text.toString()
            if (TextUtils.isEmpty(text)) {
                Toast.makeText(
                    this,
                    "Description cannot be empty.",
                    Toast.LENGTH_LONG
                ).show()
                return@setOnClickListener
            }
            uploadData()
            finish()
//            addPost(text)
        }

        val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // There are no request codes
                val data: Intent? = result.data
                imageUri = data?.data!!
                imgView.loadImage(imageUri.toString())
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
    }
}




//    private fun addPost(text: String) {
//        val firestore = FirebaseFirestore.getInstance()
//        firestore.collection("Users")
////                fetching the document details from firestore
//            .document(FirebaseAuth.getInstance().currentUser?.uid!!).get()
//            .addOnCompleteListener {
////                changing the firebase result to User's class object
//                val user = it.result?.toObject(User::class.java)
////                Storage Reference: Firebase Storage
//                val storage = FirebaseStorage.getInstance().reference.child("Images")
//                    .child(FirebaseAuth.getInstance().currentUser?.email.toString() + "_" + System.currentTimeMillis() + ".jpg")
////                Upload task: Passing the uri of the image file which have to be upload to the firebase storage
//                val uploadTask = storage.putFile(imageUri!!)
////                Uploading to Firebase
//                uploadTask.continueWithTask { task ->
//                    if (!task.isSuccessful) {
//                        Log.d("Upload Task", task.exception.toString())
//                    }
//                    storage.downloadUrl
//                }.addOnCompleteListener { urlTaskCompleted ->
//                    if (urlTaskCompleted.isSuccessful) {
//                        val downloadUri = urlTaskCompleted.result
////                        Creating an object of Post data class
//                        val post =
//                            Post(text, downloadUri.toString(), user!!, System.currentTimeMillis())
//                        firestore.collection("Posts")
//                            .document()
//                            .set(post)
//                            .addOnCompleteListener { posted ->
//                                if (posted.isSuccessful) {
//                                    Toast.makeText(
//                                        this,
//                                        "Posted Successfully",
//                                        Toast.LENGTH_LONG
//                                    ).show()
//                                    finish()
//                                } else {
//                                    Toast.makeText(
//                                        this,
//                                        "Error occurred! Please Try again.",
//                                        Toast.LENGTH_LONG
//                                    ).show()
//                                }
//                            }
//                    } else {
//                        Log.d(TAG, urlTaskCompleted.exception.toString())
//                    }
//                }
//            }
//    }
//}