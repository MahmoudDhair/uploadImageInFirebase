package com.example.uploadimageinfirebase

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.lang.Math.random

var btn_pick:Button? = null
var btn_upload:Button? = null
var image:ImageView? = null
const val PICK_IMAGE_REQUEST = 1
var filepath:Uri? = null
var storageReference:StorageReference? = null
var db: FirebaseFirestore? = null
class UploadImageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_image)
        btn_pick = findViewById(R.id.btn_pickImage)
        btn_upload = findViewById(R.id.btn_uploadImage)
        image = findViewById(R.id.image)
        storageReference = FirebaseStorage.getInstance().reference
        db = FirebaseFirestore.getInstance()
        btn_pick!!.setOnClickListener {
            pickImage()
        }

        btn_upload!!.setOnClickListener {view->
            if(filepath === null){
                Snackbar.make(view, "Pleas Choose Any Image", Snackbar.LENGTH_LONG)
                        .show()
            }else{
                uploadImage(view)
            }

        }
    }

    fun pickImage(){
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent,PICK_IMAGE_REQUEST)
    }

    fun uploadImage(view:View){
        var imageReference = storageReference!!.child("images/${filepath!!.pathSegments}")
        imageReference.putFile(filepath!!)
            .addOnSuccessListener { uri->
                val url = imageReference.downloadUrl.addOnSuccessListener {
                    Log.e("test",it.toString())
                    val images:HashMap<String, Any> = HashMap<String,Any>()
                    images.put("image",it.toString())
                    db!!.collection("images").add(images)
                    startActivity(Intent(this,MainActivity::class.java))
                }
                Snackbar.make(view, "The Image Upload Successfully", Snackbar.LENGTH_LONG)
                    .show()
            }
            .addOnFailureListener {
                Snackbar.make(view, "The Image Not Upload Successfully", Snackbar.LENGTH_LONG)
                    .show()
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.data != null){
            filepath = data.data
            image!!.setImageURI(filepath)
        }
    }
}