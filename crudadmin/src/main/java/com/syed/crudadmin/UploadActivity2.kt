package com.syed.crudadmin

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.syed.crudadmin.databinding.ActivityUpload2Binding

class UploadActivity2 : AppCompatActivity() {

    private  lateinit var binding: ActivityUpload2Binding
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle? ) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityUpload2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.save.setOnClickListener{
            val ownerName = binding.uplName.text.toString()
            val vehicleBrand =binding.uplBrand.text.toString()
            val vehicleRTO =binding.uplRto.text.toString()
            val vehicleNumber =binding.uplVno.text.toString()

            if (ownerName.isEmpty()) {
                Toast.makeText(this, "Owner Name cannot be empty", Toast.LENGTH_SHORT).show()
                binding.uplName.requestFocus()
                return@setOnClickListener
            }

            if (vehicleBrand.isEmpty()) {
                Toast.makeText(this, "Vehicle Brand cannot be empty", Toast.LENGTH_SHORT).show()
                binding.uplBrand.requestFocus()
                return@setOnClickListener
            }

            if (vehicleRTO.isEmpty()) {
                Toast.makeText(this, "Vehicle RTO cannot be empty", Toast.LENGTH_SHORT).show()
                binding.uplRto.requestFocus()
                return@setOnClickListener
            }

            if (vehicleNumber.isEmpty()) {
                Toast.makeText(this, "Vehicle Number cannot be empty", Toast.LENGTH_SHORT).show()
                binding.uplVno.requestFocus()
                return@setOnClickListener
            }

            databaseReference= FirebaseDatabase.getInstance().getReference("Vehicle Information")
            val vehicleData= VehicleData(ownerName,vehicleBrand,vehicleRTO,vehicleNumber)

            databaseReference.child(vehicleNumber).setValue(vehicleData).addOnSuccessListener{
                binding.uplName.text.clear()
                binding.uplBrand.text.clear()
                binding.uplRto.text.clear()
                binding.uplVno.text.clear()

                Toast.makeText(this, "Vehicle information saved successfully!", Toast.LENGTH_SHORT).show()

                val intent= Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }.addOnFailureListener{
                Toast.makeText(this," Not Saved yet ", Toast.LENGTH_SHORT).show()
            }
        }
    }
}