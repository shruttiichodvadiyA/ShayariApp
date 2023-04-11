package com.example.shayariapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shayariapp.Adapter.FavouriteAdapter
import com.example.shayariapp.databinding.ActivityFavouriteBinding

class Favourite_Activity : AppCompatActivity() {

    lateinit var binding: ActivityFavouriteBinding
    lateinit var db: MyDatabase
    lateinit var adapter: FavouriteAdapter
    var favouritelist = ArrayList<FavouriteShayariclass>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavouriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = MyDatabase(this)
        favourite()
    }
    private fun favourite() {
        favouritelist=db.DisplayFavouriteShayari()
         adapter=FavouriteAdapter(favouritelist) { fav,  sid ->
            db.displayfavoutite(fav,sid)
             adapter.update(favouritelist)
        }
        var manager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rcvdisplayshayari.layoutManager=manager
        binding.rcvdisplayshayari.adapter=adapter
        binding.imgback.setOnClickListener {
            onBackPressed()
        }
    }
}