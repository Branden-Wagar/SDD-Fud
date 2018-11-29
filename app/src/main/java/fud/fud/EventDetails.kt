package fud.fud

import android.os.Bundle
import android.app.Activity
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.net.Uri
import android.widget.Button
import fud.fud.Models.Event
import fud.fud.databinding.ActivityEventDetailsBinding

import kotlinx.android.synthetic.main.activity_event_details.*

class EventDetails : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_details)

        //get the event passed from main activity
        val event = getIntent().getSerializableExtra("Event") as Event

        val binding: ActivityEventDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_event_details)
        binding.eventDetailsVM = EventDetailsVM(event)

        //if route button is clicked start the routing activity and pass it the event
        val button: Button = findViewById<Button>(R.id.RouteButton)
        button.setOnClickListener({
            val intent = Intent(this,Routing::class.java)
            intent.putExtra("Event",event)
            startActivity(intent)
        })

        //if fake button is clicked start the fake event activity
        val button2: Button = findViewById<Button>(R.id.FakeButton)
        button2.setOnClickListener({
            startActivity(Intent(this, FakeEvent::class.java))
        })

        //if verify button is clicked start verify activity
        val button3: Button = findViewById<Button>(R.id.VerifyButton)
        button3.setOnClickListener({
            //startActivity(Intent(this, CreateEvent::class.java))
        })
    }



}
