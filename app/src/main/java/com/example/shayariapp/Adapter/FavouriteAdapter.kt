package com.example.shayariapp.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shayariapp.FavouriteShayariclass
import com.example.shayariapp.R

class FavouriteAdapter(var favouritelist: ArrayList<FavouriteShayariclass>,var invo:((Int,Int)->Unit)) : RecyclerView.Adapter<FavouriteAdapter.myview>() {
    class myview (v:View):RecyclerView.ViewHolder(v){
        var txtshayarishow: TextView = v.findViewById(R.id.txtshayarishow)
        var like: ImageView = v.findViewById(R.id.like)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myview {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.display_shayari_item, parent, false)
        val adapter =myview(v)
        return adapter
    }

    override fun onBindViewHolder(holder: myview, position: Int) {
       holder.txtshayarishow.setText(favouritelist[position].Shayari)

            holder.like.setImageResource(R.drawable.like2)

        holder.like.setOnClickListener {
//            if (favouritelist[position].fav == 1) {
                invo.invoke(0, favouritelist[position].s_id)
//                holder.like.setImageResource(R.drawable.like2)
//                favouritelist[position].fav = 0

//            }
            delete(position)
//            else {
//                invo.invoke(1, favouritelist[position].s_id)
////                holder.like.setImageResource(R.drawable.like)
//                favouritelist[position].fav = 1
////                Log.e("TAG", "onBindViewHolder: "+favouritelist)
//            }
        }
    }

    override fun getItemCount(): Int {
        return favouritelist.size
    }
    fun update(favouritelist: ArrayList<FavouriteShayariclass>){
        this.favouritelist=favouritelist
        notifyDataSetChanged()
    }
    fun delete(position: Int){
        favouritelist.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeRemoved(position,favouritelist.size)
    }

}