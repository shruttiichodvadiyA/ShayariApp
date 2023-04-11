package com.example.shayariapp

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.shayariapp.databinding.ActivityShayariBinding

class ShayariActivity : AppCompatActivity() {
lateinit var binding: ActivityShayariBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityShayariBinding.inflate(layoutInflater)
        setContentView(binding.root)
        displayShayari()
    }

    private fun displayShayari() {
        var shayari:String?=intent.getStringExtra("shayari")
        binding.txtshay.text=shayari


        binding.imgback.setOnClickListener {
//            val intent = Intent(this,MainActivity::class.java)
//            startActivity(intent)
            onBackPressed()
        }

        val Gallerylanchuar = registerForActivityResult<Intent, ActivityResult>(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                val data = result.data
                val uri = data!!.data
                binding.imageview.setImageURI(uri)
            }
        }
        binding.imggallery.setOnClickListener { view ->
            val gallery =
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            Gallerylanchuar.launch(gallery)
        }
        binding.imgshare.setOnClickListener {
            var i =Intent(Intent.ACTION_SEND)
            i.setType("text/plain")
            i.putExtra(Intent.EXTRA_TEXT,shayari)
            startActivity(i)
        }


    }
}