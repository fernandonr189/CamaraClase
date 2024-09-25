package com.example.camaraclase

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.provider.MediaStore.Audio.Media
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class PhotoActivity : AppCompatActivity() {

    private lateinit var photoImage: ImageView
    private lateinit var photoButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_photo)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        photoButton = findViewById(R.id.photo_button)
        photoImage = findViewById(R.id.photo_image_view)

        photoButton.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            responseLauncer.launch(intent)
        }
    }

    private val responseLauncer = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        activityResult ->
        if(activityResult.resultCode == RESULT_OK) {
            Toast.makeText(this, "Fotografia tomada", Toast.LENGTH_SHORT).show()
            val extras = activityResult.data!!.extras
            val imgBitmap = extras!!["data"] as Bitmap?
            photoImage.setImageBitmap(imgBitmap)
        }
        else {
            Toast.makeText(this, "Proceso cancelado", Toast.LENGTH_SHORT).show()
        }
    }
}