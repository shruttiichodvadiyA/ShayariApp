package com.example.shayariapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shayariapp.Adapter.ShayariAdapter
import com.example.shayariapp.databinding.ActivityDisplayShayariBinding

class Display_Shayari_Activity : AppCompatActivity() {
    lateinit var binding: ActivityDisplayShayariBinding
    lateinit var db: MyDatabase
    lateinit var adapter: ShayariAdapter
    var getid=0
    var shayariDisplaylist = ArrayList<ShayariDisplay>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDisplayShayariBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = MyDatabase(this)
        initview()
    }

    private fun initview() {
        var titlename:String?=intent.getStringExtra("title")
        binding.txtshayari.text=titlename

         getid=intent.getIntExtra("id",0)

//        shayariDisplaylist=db.displayShayari(getid)

         adapter= ShayariAdapter( {
            val intent = Intent(this, ShayariActivity::class.java)
            intent.putExtra("shayari", it.Shayari)
            startActivity(intent)
        },{ fav , sid ->

            db.displayfavoutite(fav,sid)
        })
        var manager=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        binding.rcvshayari.layoutManager=manager
        binding.rcvshayari.adapter=adapter

        binding.imgback.setOnClickListener {
            onBackPressed()
        }
        binding.imglike.setOnClickListener {
            val intent =Intent(this,Favourite_Activity::class.java)
            startActivity(intent)
        }


    }

    override fun onResume() {
        super.onResume()
        shayariDisplaylist=db.displayShayari(getid)
        adapter.update(shayariDisplaylist)
        Log.e("TAG", "onResume: "+getid )
    }
}