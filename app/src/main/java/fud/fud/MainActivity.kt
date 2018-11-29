package fud.fud

import android.app.Activity
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.View
import android.widget.*
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.firestore.FirebaseFirestore
import fud.fud.Database.DatabaseManager
import fud.fud.Models.Event
import fud.fud.databinding.ActivityMainBinding
import android.content.Context.LOCATION_SERVICE
import android.content.pm.PackageManager
import android.location.Location
import android.support.v4.content.ContextCompat.getSystemService
import android.location.LocationManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat


class MainActivity : Activity() {
    private lateinit var lv: ListView

    private lateinit var activityVM : MainActivityVM


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // databinding to MainActivityVM setup
        permissionsControl()
        var events = ArrayList<String>()
        var adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, events)

        var t : ArrayList<String> = ArrayList(resources.getStringArray(R.array.food_tags).toList())
        //var events = ArrayList<String>()
        //var adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, events)

        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        activityVM = MainActivityVM(this, t)
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

    private fun permissionsControl(){
        if (ContextCompat.checkSelfPermission(this,
                        android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                            android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                        1)

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
        }
    }


    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            1 -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return
            }

            // Add other 'when' lines to check for other
            // permissions this app might request.
            else -> {
                // Ignore all other requests.
            }
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
