package com.example.contactwithfirebase.Adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.uploadimageinfirebase.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.image_item.view.*

class UploadImageAdapter(var activity: Activity, var data:MutableList<String>): RecyclerView.Adapter<UploadImageAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imag = itemView.imag


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(activity).inflate(R.layout.image_item, parent, false)
        return MyViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Picasso.get()
            .load(data[position])
            .into(holder.imag)


    }

    override fun getItemCount(): Int {
        return data.size

    }
}