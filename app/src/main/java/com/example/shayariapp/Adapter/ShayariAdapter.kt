package com.example.shayariapp.Adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shayariapp.R
import com.example.shayariapp.ShayariDisplay

class ShayariAdapter(

    var invoke: ((ShayariDisplay) -> Unit),
    var invo: ((Int, Int) -> Unit)
) : RecyclerView.Adapter<ShayariAdapter.myview>() {

    var shayariDisplaylist =ArrayList<ShayariDisplay>()

    class myview(v: View) : RecyclerView.ViewHolder(v) {
        var txtshayarishow: TextView = v.findViewById(R.id.txtshayarishow)
        var layoutshayari: LinearLayout = v.findViewById(R.id.layoutshayari)
        var like: ImageView = v.findViewById(R.id.like)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myview {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.shayari_item, parent, false)
        val adapter = myview(v)
        return adapter
    }

    override fun onBindViewHolder(holder: myview, position: Int) {
        holder.txtshayarishow.setText(shayariDisplaylist[position].Shayari)

        if (shayariDisplaylist[position].fav == 1) {
            holder.like.setImageResource(R.drawable.like2)
        } else {
            holder.like.setImageResource(R.drawable.like)
        }
        holder.like.setOnClickListener {
            if (shayariDisplaylist[position].fav == 1) {
                invo.invoke(0, shayariDisplaylist[position].s_id)
                holder.like.setImageResource(R.drawable.like)
                shayariDisplaylist[position].fav = 0
            } else {
                invo.invoke(1, shayariDisplaylist[position].s_id)
                holder.like.setImageResource(R.drawable.like2)
                shayariDisplaylist[position].fav = 1
            }

        }
        holder.layoutshayari.setOnClickListener {
            invoke.invoke(shayariDisplaylist[position])
        }


    }

    override fun getItemCount(): Int {
        return shayariDisplaylist.size
    }
    fun update(shayariDisplaylist: ArrayList<ShayariDisplay>) {
        this.shayariDisplaylist= ArrayList()
        this.shayariDisplaylist.addAll(shayariDisplaylist)
        Log.e("TAG", "update: "+shayariDisplaylist.size)
        notifyDataSetChanged()
    }
}