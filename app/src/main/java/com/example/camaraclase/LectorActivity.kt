package com.example.camaraclase

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.zxing.integration.android.IntentIntegrator

class LectorActivity : AppCompatActivity() {

    private lateinit var scanButton: Button
    private lateinit var captureButton: Button
    private lateinit var clearButton: Button

    private lateinit var obtainedCodeText: EditText
    private lateinit var descriptionText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_lector)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        scanButton = findViewById(R.id.qr_scan_button)
        captureButton = findViewById(R.id.capture_button)
        clearButton = findViewById(R.id.clear_button)

        obtainedCodeText = findViewById(R.id.read_code_text)
        descriptionText = findViewById(R.id.description_text)

        scanButton.setOnClickListener {
            scanQrCode()
        }

        captureButton.setOnClickListener {
            if(obtainedCodeText.text.toString().isNotEmpty() && descriptionText.text.toString().isNotEmpty()){
                Toast.makeText(this, "Datos capturados", Toast.LENGTH_SHORT).show()
                clearForm()
            }
            else {
                Toast.makeText(this, "Debe registrar datos", Toast.LENGTH_SHORT).show()
            }
        }

        clearButton.setOnClickListener {
            clearForm()
        }
    }

    private fun scanQrCode() {
        val intentIntegrator = IntentIntegrator(this)
        intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)
        intentIntegrator.setPrompt("Lector de codigos qr")
        intentIntegrator.setCameraId(1)
        intentIntegrator.setBeepEnabled(true)
        intentIntegrator.setBarcodeImageEnabled(true)
        intentIntegrator.initiateScan()

    }

    private fun clearForm() {
        obtainedCodeText.text.clear()
        descriptionText.text.clear()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)

        if(intentResult != null) {
            if(intentResult.contents == null) {
                Toast.makeText(this, "Lectura cancelada", Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(this, "Codigo leido", Toast.LENGTH_SHORT).show()
                obtainedCodeText.setText(intentResult.contents)
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}