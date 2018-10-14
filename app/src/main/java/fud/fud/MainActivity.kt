package fud.fud

import android.app.Activity
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Button
import com.google.firebase.firestore.FirebaseFirestore
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
        val also = ArrayAdapter.createFromResource(this, R.array.planets_array,
                android.R.layout.simple_spinner_dropdown_item).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            tagsSpinner.adapter = adapter
        }

//        var db = FirebaseFirestore.getInstance()
//
//        var databaseManager = DatabaseManager(db)
//
//        databaseManager.add(Event(Date(), "123 fud st.", "Test", "chinese"))

    }
}
