package com.syed.crudadmin

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.syed.crudadmin.databinding.ActivityDeleteBinding
import com.syed.crudadmin.databinding.ActivityMainBinding

class DeleteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDeleteBinding
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityDeleteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.deleteBtn.setOnClickListener{
            val vehicleNumber=binding.dltVno.text.toString()
            if (vehicleNumber.isNotEmpty()){
                deleteData(vehicleNumber)
            }else{
                Toast.makeText(this,"Vehicle Number needed.", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun deleteData(vehicleNumber: String){
        databaseReference= FirebaseDatabase.getInstance().getReference("Vehicle Information")
        databaseReference.child(vehicleNumber).removeValue().addOnSuccessListener{
            binding.dltVno.text.clear()
            Toast.makeText(this,"Record Deleted Successfully", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener{
            Toast.makeText(this,"Record Deletion Failed", Toast.LENGTH_SHORT).show()
        }
    }
}