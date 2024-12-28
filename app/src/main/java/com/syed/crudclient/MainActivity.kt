package com.syed.crudclient

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.syed.crudclient.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var databaseReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.button.setOnClickListener{
            val  searchvehiclNumber: String = binding.search.text.toString()
            if (searchvehiclNumber.isNotEmpty()){
                readData(searchvehiclNumber)

            }else{
                Toast.makeText(this,"Please Enter the Vehicle Number", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun readData(vehicleNumber: String){
        databaseReference = FirebaseDatabase.getInstance().getReference("Vehicle Information")
        databaseReference.child(vehicleNumber).get().addOnSuccessListener{
            if (it.exists()){
                val ownerName = it.child("ownerName").value
                val vehicleBrand = it.child("vehicleBrand").value
                val vehicleRTO = it.child("vehicleRTO").value

                Toast.makeText(this,"Result found", Toast.LENGTH_SHORT).show()
                binding.search.text.toString()
                binding.readOwner.text = ownerName.toString()
                binding.readBrand.text = vehicleBrand.toString()
                binding.readRto.text = vehicleRTO.toString()
            }else{
                Toast.makeText(this,"No Match found", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener{
            Toast.makeText(this,"Something Went Wrong ", Toast.LENGTH_SHORT).show()
        }
    }
}