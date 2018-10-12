package fud.fud

import android.app.Activity
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.widget.Button
import com.google.firebase.firestore.FirebaseFirestore
import fud.fud.databinding.ActivityMainBinding

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.mainvm = MainActivityVM("Chinese", "Distance")

        val button: Button =findViewById(R.id.CreateEventButton) as Button
        button.setOnClickListener({
            startActivity(Intent(this, EventDetails::class.java))
        })
    }
}