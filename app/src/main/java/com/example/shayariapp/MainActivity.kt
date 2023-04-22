package com.example.shayariapp

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.core.view.GravityCompat
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
        drawer()
    }

    private fun drawer() {
        binding.imgmenu.setOnClickListener {
            binding.drawerlayout.openDrawer(GravityCompat.START)
        }
        binding.layouthome.setOnClickListener {
            binding.drawerlayout.closeDrawer(GravityCompat.START)
            Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show()
        }
        binding.layoutfavourite.setOnClickListener {
            binding.drawerlayout.closeDrawer(GravityCompat.START)
            val i = Intent(this, Favourite_Activity::class.java)
            startActivity(i)
        }
        binding.layoutshare.setOnClickListener {
            binding.drawerlayout.closeDrawer(GravityCompat.START)
            val share = Intent(Intent.ACTION_SEND)
            share.type = "text/plain"
            share.putExtra(
                Intent.EXTRA_TEXT,
                "https://play.google.com/store/apps/details?id=saptak.statusquotes"
            )
            startActivity(share)
        }
        binding.layoutexit.setOnClickListener {
            onBackPressed()
        }
        binding.txtprivacy.setOnClickListener {
            val url =
                "https://shrutichodvadiya.blogspot.com/2023/04/policy-terms-conditions-privacy-policy.html"
            val i = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(i)
        }
    }

    fun shayari() {
        shayarilist = db.readData()
        val adapter = CategoryAdapter(shayarilist) {
            val intent = Intent(this, Display_Shayari_Activity::class.java)
            intent.putExtra("title", it.name)
            intent.putExtra("id", it.c_id)
            Log.e("tagggggggg", "shayari: " + it.c_id)
            startActivity(intent)
        }
        val manager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rcvcategory.layoutManager = manager
        binding.rcvcategory.adapter = adapter

        binding.imglike.setOnClickListener {
            val intent = Intent(this, Favourite_Activity::class.java)
            startActivity(intent)
        }
        binding.txtversion.text= db.writableDatabase.version.toString()
//        db.writableDatabase.version
    }

    private var onBackToExitPressedOnce: Boolean = false
    override fun onBackPressed() {
        if (onBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }
        this.onBackToExitPressedOnce = true
        Toast.makeText(this, "Back Again To Exit", Toast.LENGTH_SHORT).show()
        Handler(Looper.getMainLooper()).postDelayed({ onBackToExitPressedOnce = false }, 2000)
    }

}