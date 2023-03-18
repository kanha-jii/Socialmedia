package com.example.socialmedia

import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.webkit.MimeTypeMap
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask

class CreateProfile : AppCompatActivity() {
    private val ff:FirebaseFirestore = FirebaseFirestore.getInstance()
    private val db:FirebaseDatabase = FirebaseDatabase.getInstance()
    private lateinit var  sr:StorageReference
    private lateinit var  dr:DatabaseReference
    private lateinit var  imgView:ImageView
    private lateinit var  uri:Uri
    private lateinit var  imageUri:Uri
    private lateinit var  docRef:DocumentReference

    private var currentUserId:String =""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_profile)

        val user: FirebaseUser? = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            currentUserId = user.uid
        }
        val dr:DocumentReference =ff.collection("user").document(currentUserId)
        sr= FirebaseStorage.getInstance().getReference("profile images")
        this.dr =db.getReference("all users")
        val button = findViewById<Button>(R.id.btn_cp)
        button.setOnClickListener() {
            uploadData()
        }
        imgView = findViewById<ImageView>(R.id.iv_cp)
        imgView.setOnClickListener() {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
//            startActivityForResult(intent,1)
//            launcher.launch(intent)
            resultLauncher.launch(intent)

        }

    }

    private fun uploadData() {
        val name = findViewById<EditText>(R.id.et_name_cp)
        val course = findViewById<EditText>(R.id.et_course_cp)
        val branch = findViewById<EditText>(R.id.et_branch_cp)
        val year = findViewById<EditText>(R.id.et_year_cp)
        val about = findViewById<EditText>(R.id.et_about_cp)
        val nameStr = name.text.toString()
        val courseStr = course.text.toString()
        val branchStr = branch.text.toString()
        val yearStr = year.text.toString()
        val aboutStr = about.text.toString()
        val ref: StorageReference =
            sr.child((System.currentTimeMillis().toString() + "." + getFileExt(imageUri)))
        val uploadTask = ref.putFile(imageUri)
        val urlTask =
        uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            return@Continuation ref.downloadUrl
        }).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val downloadUri = task.result
                Log.d("STORAGE_UTIL", "downloadUri: " + downloadUri)
                var profile: HashMap<String, String> = HashMap<String, String>()
                profile["name"] = nameStr
                profile["course"] = courseStr
                profile["branch"] = branchStr
                profile["year"] = yearStr
                profile["about"] = aboutStr
                docRef = ff.collection("users").document()
                docRef.set(profile).addOnSuccessListener {
                    Handler(Looper.getMainLooper()).postDelayed({
//                        val intent = Intent(this,HomeFragment()::class.java)
//                        startActivity(intent)
                          val fragM = supportFragmentManager.beginTransaction()
                        fragM.replace(R.id.frames,HomeFragment())
                        fragM.addToBackStack(null)
                        fragM.commit()


                    },2000)
                }
            }
        }
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if(requestCode==1 || resultCode == RESULT_OK || data!=null ||
//                )
//
//    }

    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // There are no request codes
            val data: Intent? = result.data
            imageUri = data?.data!!
           // Picasso.get().load(imageUri).into(imgView)

        }
    }
    private fun getFileExt(uri:Uri): String? {
        val contentresolver:ContentResolver = applicationContext.contentResolver
        val mimetypemap = MimeTypeMap.getSingleton()
        return mimetypemap.getExtensionFromMimeType((contentresolver.getType(uri)))
    }

}


