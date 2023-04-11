package com.example.shayariapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shayariapp.Adapter.CategoryAdapter
import com.example.shayariapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var db: MyDatabase
    var shayarilist = ArrayList<ShayariModelClass>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = MyDatabase(this)
        shayari()
    }

    fun shayari() {
        shayarilist = db.readData()
        var adapter = CategoryAdapter(shayarilist){
            val intent=Intent(this,Display_Shayari_Activity::class.java)
            intent.putExtra("title",it.name)
            intent.putExtra("id",it.c_id)
            Log.e("tagggggggg", "shayari: "+it.c_id )
            startActivity(intent)
        }
        var manager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rcvcategory.layoutManager = manager
        binding.rcvcategory.adapter = adapter

        binding.imglike.setOnClickListener {
            val intent =Intent(this,Favourite_Activity::class.java)
            startActivity(intent)
        }
    }
}