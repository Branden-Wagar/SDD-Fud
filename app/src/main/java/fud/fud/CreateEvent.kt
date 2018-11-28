package fud.fud

import android.app.Activity
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import fud.fud.databinding.ActivityCreateEventBinding

class CreateEvent : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_event)


        var t : ArrayList<String> = ArrayList(resources.getStringArray(R.array.food_tags).toList())

        val adapter = ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, t)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)


        val binding: ActivityCreateEventBinding = DataBindingUtil.setContentView(this, R.layout.activity_create_event)
        binding.createEventVM = CreateEventVM(t, "Chinese", adapter)

    }
}
