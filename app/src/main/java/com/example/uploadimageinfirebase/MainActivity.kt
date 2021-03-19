package com.example.uploadimageinfirebase

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.contactwithfirebase.Adapter.UploadImageAdapter
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {
    var float_action:View? = null
    var db: FirebaseFirestore? = null
    var images:MutableList<String>? = null
    var image:Map<String, Any>? = null
    var re_image: RecyclerView? = null
    var progressDialog: ProgressDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        progressDialog = ProgressDialog(this)
        progressDialog!!.setMessage("Loading....")
        progressDialog!!.setCancelable(false)
        progressDialog!!.show()
        float_action = findViewById(R.id.btn_float)
        re_image = findViewById(R.id.container)
        db = FirebaseFirestore.getInstance()
        images = mutableListOf()
        float_action!!.setOnClickListener {view ->
            val intent = Intent(this,UploadImageActivity::class.java)
            startActivity(intent)
        }


        db!!.collection("images")
            .get()
            .addOnCompleteListener {result->
                if(result.isSuccessful){
                    for( document in result.result!!){
                        image = document.data
                        Log.e("image",image!!["image"].toString())
                        images!!.add(image!!["image"].toString())

                    }
                    if(progressDialog!!.isShowing){
                        progressDialog!!.dismiss()
                    }
                    re_image!!.layoutManager = LinearLayoutManager(this)
                    val imageAdapter = UploadImageAdapter(this,images!!)
                    re_image!!.adapter = imageAdapter
                }

            }
    }
}