package fud.fud

import android.app.Activity
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import fud.fud.databinding.ActivityCreateEventBinding
import fud.fud.databinding.ActivityMainBinding

class CreateEvent : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_event)

        val tagsSpinner: Spinner = findViewById(R.id.FoodTypeSpinner)
        ArrayAdapter.createFromResource(this, R.array.planets_array,
                android.R.layout.simple_spinner_dropdown_item).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            tagsSpinner.adapter = adapter
        }

        val binding: ActivityCreateEventBinding = DataBindingUtil.setContentView(this, R.layout.activity_create_event)
        binding.createEventVM = CreateEventVM()

    }
}
