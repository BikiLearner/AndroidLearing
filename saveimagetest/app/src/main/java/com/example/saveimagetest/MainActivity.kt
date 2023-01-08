package com.example.saveimagetest

import android.content.ContentValues
import android.graphics.Bitmap
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.core.view.drawToBitmap
import java.io.File
import java.io.OutputStream
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val image=findViewById<ImageView>(R.id.image)
        val btn=findViewById<Button>(R.id.button)

        btn.setOnClickListener{
            val bitmap=image.drawToBitmap()
            saveToGallery(bitmap)
        }
    }

    private fun saveToGallery(bitmap: Bitmap) {
        val fos:OutputStream
        try {
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.Q){
                val resolver = contentResolver
                val contentValue=ContentValues()
                contentValue.put(MediaStore.MediaColumns.DISPLAY_NAME,"Image_"+".jpg")
                contentValue.put(MediaStore.MediaColumns.MIME_TYPE,"image/jpg")
                contentValue.put(MediaStore.MediaColumns.RELATIVE_PATH,Environment.DIRECTORY_PICTURES+File.separator+"TestBiki")
                val imageUri=resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,contentValue)
                fos=resolver.openOutputStream(Objects.requireNonNull(imageUri)!!)!!
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,fos)
                Objects.requireNonNull<OutputStream?>(fos)
                Toast.makeText(this,"finally done",Toast.LENGTH_LONG).show()

            }
        }catch (e :Exception){
            Toast.makeText(this,"finally ++ done",Toast.LENGTH_LONG).show()
        }

    }
}