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
import android.widget.ListView
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import fud.fud.Database.DatabaseManager
import fud.fud.Models.Event
import fud.fud.databinding.ActivityMainBinding
import java.time.Instant
import java.time.temporal.TemporalAmount
import java.util.*


class MainActivity : Activity() {
    private lateinit var lv: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.mainvm = MainActivityVM("Free Food Only", "Who cares")

        /*val button: Button = findViewById(R.id.CreateEventButton)
        button.setOnClickListener {
            startActivity(Intent(this, EventDetails::class.java))
        }*/

        lv = findViewById<ListView>(R.id.EventsList);
        val events = arrayListOf<Event>();
        /*var dbInstance = FirebaseFirestore.getInstance()
        var dbManager = DatabaseManager(dbInstance)
        dbManager.allEvents.addOnCompleteListener {
            task -> if (task.isSuccessful)
            task.result!!.forEach {
                it ->
                // Do something with data
                Event event = it.data.toObject(Event.class);
            }
        }*/

        var listEvents : Array<Event?> = arrayOfNulls<Event>(20)
        for (i in 0 until 20){
            var t : Event = Event()
            t.price = Math.random() % 5
            t.eventName = "Event" + i
            t.date = Date.from(Instant.now())
            t.distanceToUser = i.toDouble()
            t.cuisineType = "Who cares"
            listEvents[i] = t
        }

        val listItems =arrayOfNulls<String>(20)
        for (i in 0 until 20){
            listItems[i]="TEST"
        }
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1,listEvents)
        lv.adapter = adapter

        val tagsSpinner: Spinner = findViewById(R.id.FoodTagsSpinner)
        ArrayAdapter.createFromResource(this, R.array.planets_array,
                android.R.layout.simple_spinner_dropdown_item).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            tagsSpinner.adapter = adapter
        }

    }
}
