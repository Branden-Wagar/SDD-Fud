package fud.fud

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.databinding.DataBindingUtil
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.support.v4.content.ContextCompat
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

        val locationManager = this.getSystemService(Activity.LOCATION_SERVICE) as LocationManager

        var bestLocation : Location? = null
                if (locationManager.isLocationEnabled && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                    val providers = locationManager.getProviders(true)
                    for (provider in providers) {
                        val l = locationManager.getLastKnownLocation(provider) ?: continue
                        if (bestLocation == null || l.accuracy < bestLocation.accuracy) {
                            // Found best last known location: %s", l);
                            bestLocation = l
                        }
                    }

                }
                else{
                    bestLocation = null
                }
        val binding: ActivityCreateEventBinding = DataBindingUtil.setContentView(this, R.layout.activity_create_event)
        binding.createEventVM = CreateEventVM(this, t, "Chinese", adapter, bestLocation)

    }
}
