package com.syed.crudadmin

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.syed.crudadmin.databinding.ActivityUpdateBinding

class UpdateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateBinding

    private lateinit var databaseReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.update.setOnClickListener{
            val updataVehicleNumber = binding.updVno.text.toString()
            val updataName = binding.updName.text.toString()
            val updataBrand = binding.updBrand.text.toString()
            val updataRTO = binding.updRto.text.toString()

            updataData(updataVehicleNumber,updataName,updataBrand,updataRTO)

        }
    }
    private fun updataData(vechicleNumber: String, ownerName: String, vehicleBrand: String, vehicleRTO: String) {
        databaseReference = FirebaseDatabase.getInstance().getReference("Vehicle Information")

        // Check if the vehicle data already exists
        databaseReference.child(vechicleNumber).get().addOnSuccessListener { snapshot ->
            if (snapshot.exists()) {
                // If the vehicle data exists, update it
                val vehicleData = mapOf<String, String>(
                    "ownerName" to ownerName,
                    "vehicleBrand" to vehicleBrand,
                    "vehicleRTO" to vehicleRTO
                )

                databaseReference.child(vechicleNumber).updateChildren(vehicleData).addOnSuccessListener {
                    binding.updVno.text.clear()
                    binding.updName.text.clear()
                    binding.updBrand.text.clear()
                    binding.updRto.text.clear()
                    Toast.makeText(this, "Update Successful", Toast.LENGTH_SHORT).show()
                }.addOnFailureListener {
                    Toast.makeText(this, "Update Failed", Toast.LENGTH_SHORT).show()
                }
            } else {
                // If the vehicle data does not exist, notify the user
                Toast.makeText(this, "Vehicle data does not exist", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {
            // Handle any errors during the read operation
            Toast.makeText(this, "Error checking data", Toast.LENGTH_SHORT).show()
        }
    }


}