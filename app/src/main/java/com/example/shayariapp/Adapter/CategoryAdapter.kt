package com.example.shayariapp.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shayariapp.R
import com.example.shayariapp.ShayariModelClass

class CategoryAdapter(var shayarilist: ArrayList<ShayariModelClass>, var invoke:((ShayariModelClass)->Unit)) : RecyclerView.Adapter<CategoryAdapter.myview>() {
    class myview(v: View) : RecyclerView.ViewHolder(v) {
        var txtcategory: TextView = v.findViewById(R.id.txtcategory)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myview {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.category_item, parent, false)
        val adapter = myview(v)
        return adapter
    }

    override fun onBindViewHolder(holder: myview, position: Int) {
        holder.txtcategory.setText(shayarilist[position].name)
        holder.txtcategory.setOnClickListener {
            invoke.invoke(shayarilist[position])
        }
    }

    override fun getItemCount(): Int {
      return  shayarilist.size
    }
}