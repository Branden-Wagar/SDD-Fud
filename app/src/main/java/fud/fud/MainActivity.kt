package fud.fud

import android.app.Activity
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.View
import android.widget.*
import com.google.firebase.firestore.FirebaseFirestore
import fud.fud.Database.DatabaseManager
import fud.fud.Models.Event
import fud.fud.databinding.ActivityMainBinding


class MainActivity : Activity() {
    private lateinit var lv: ListView

    private lateinit var activityVM : MainActivityVM


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // databinding to MainActivityVM setup

        //var events = ArrayList<String>()
        //var adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, events)

        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        activityVM = MainActivityVM(this)
        binding.mainvm = activityVM
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
        
        lv.setOnItemClickListener {adapterView, view, position: Int, id: Long ->
            activityVM.getEvent(this,position)
        }


    }
    private fun populateList(){
        lv = findViewById<ListView>(R.id.EventsList);
        activityVM.UpdateEventsList()
        //lv.adapter = adapter
        //get the information for each of the events
    }

    //on return to the page repopulate the ListView to push any changes to the database
    override fun onResume(){
        super.onResume()
        populateList()
    }

}
