package com.example.shayariapp

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.shayariapp.databinding.ActivityShayariBinding
import java.io.File
import java.io.FileOutputStream
import java.util.*


class ShayariActivity : AppCompatActivity() {
    var finalBitmap: Bitmap? = null
    lateinit var binding: ActivityShayariBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShayariBinding.inflate(layoutInflater)
        setContentView(binding.root)
        displayShayari()
    }

    private fun displayShayari() {
        var shayari: String? = intent.getStringExtra("shayari")
        binding.txtshay.text = shayari


        binding.imgback.setOnClickListener {
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
            var i = Intent(Intent.ACTION_SEND)
            i.setType("text/plain")
            i.putExtra(Intent.EXTRA_TEXT, shayari)
            startActivity(i)
        }
        binding.imgcopy.setOnClickListener {
            val clipboard: ClipboardManager =
                getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("label", shayari)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(this, "copy", Toast.LENGTH_SHORT).show()
        }
        binding.imgdownload.setOnClickListener {
            val pic:View=binding.layoutdownload
            pic.isDrawingCacheEnabled=true
            val hight:Int=pic.height
            val width:Int=pic.width
            pic.layout(0,0,width,hight)
            pic.buildDrawingCache(true)
            val bm:Bitmap=Bitmap.createBitmap(pic.drawingCache)
            pic.isDrawingCacheEnabled=false
            Toast.makeText(this, "Image Saved", Toast.LENGTH_SHORT).show()
            MediaStore.Images.Media.insertImage(contentResolver,bm,null,null)
        }

    }
}