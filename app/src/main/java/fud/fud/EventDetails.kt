package fud.fud

import android.os.Bundle
import android.app.Activity
import android.content.Intent
import android.widget.Button

import kotlinx.android.synthetic.main.activity_event_details.*

class EventDetails : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_details)
        val button: Button =findViewById(R.id.button2) as Button
        button.setOnClickListener({
            startActivity(Intent(this, MainActivity::class.java))
        })
        val button2: Button =findViewById(R.id.button) as Button
        button2.setOnClickListener({
            startActivity(Intent(this, CreateEvent::class.java))
        })
    }

}
