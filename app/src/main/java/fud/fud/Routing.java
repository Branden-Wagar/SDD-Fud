package fud.fud;

import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;

import fud.fud.Models.Event;
import fud.fud.Models.EventLocation;

public class Routing extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routing2);
        //get the event passed from eventDetails
        Event event=(Event)getIntent().getSerializableExtra("Event");
        //get the location from the event and convert it into String form
        EventLocation eventLocation = event.getLocation();
        String coordinates = Double.toString(eventLocation.getLatitude())+","+Double.toString(eventLocation.getLongitude());
        String location = "geo:0,0?q="+coordinates+"("+event.getEventName()+")";
        //start the google maps app and drop a pin on set lat/long with event name
        Uri gmmIntentUri = Uri.parse(location);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);

        //return to the main activity on button click
        Button btn = (Button)findViewById(R.id.HomeButton);
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Routing.this, MainActivity.class));
            }
        });
    }

}
