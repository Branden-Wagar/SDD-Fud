package fud.fud

import android.app.Activity
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Spinner
import com.google.firebase.firestore.FirebaseFirestore
import fud.fud.Database.DatabaseManager
import fud.fud.Models.Event
import fud.fud.databinding.ActivityMainBinding


class MainActivity : Activity() {
    private lateinit var lv: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // databinding to MainActivityVM setup
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.mainvm = MainActivityVM("Free Food Only", "Who cares")

        val button: Button = findViewById(R.id.CreateEventButton)
        button.setOnClickListener {
            startActivity(Intent(this, CreateEvent::class.java))
        }

        populateList()

        val tagsSpinner: Spinner = findViewById(R.id.FoodTagsSpinner)
        ArrayAdapter.createFromResource(this, R.array.food_tags,
                android.R.layout.simple_spinner_dropdown_item).also { adapter1 ->
            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            tagsSpinner.adapter = adapter1
        }

    }
    private fun populateList(){
        // setup our firebase connection
        var dbInstance = FirebaseFirestore.getInstance()
        var dbManager = DatabaseManager(dbInstance)
        //set up arrayLists for holding the information for the ListView
        lv = findViewById<ListView>(R.id.EventsList);
        val events = arrayListOf<String>();
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1,events)
        //get the information for each of the events
        dbManager.allEvents.addOnCompleteListener {
            task -> if (task.isSuccessful()){
            var temp = task.getResult()
            temp!!.forEach {
                // foreach document we get from allEvents convert it to an Event
                val t = it.toObject(Event::class.java)
                events.add(t.toString()) // then put the string rep of the object in our events
                lv.adapter = adapter // update the UI with this Event info
            }
            }

        }
    }
    //on return to the page repopulate the ListView to push any changes to the database
    override fun onResume(){
        super.onResume()
        populateList()
    }

}
