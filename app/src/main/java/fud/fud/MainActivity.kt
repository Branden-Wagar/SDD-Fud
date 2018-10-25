package fud.fud

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Button
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import fud.fud.Database.DatabaseManager
import fud.fud.Models.Event
import fud.fud.databinding.ActivityMainBinding
import java.util.*

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.mainvm = MainActivityVM("Free Food Only", "Who cares")

        val button: Button = findViewById(R.id.CreateEventButton)
        button.setOnClickListener {
            startActivity(Intent(this, EventDetails::class.java))
        }

        val tagsSpinner: Spinner = findViewById(R.id.FoodTagsSpinner)
        ArrayAdapter.createFromResource(this, R.array.planets_array,
                android.R.layout.simple_spinner_dropdown_item).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            tagsSpinner.adapter = adapter
        }

    }
}
