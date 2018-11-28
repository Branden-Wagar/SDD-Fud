package fud.fud;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;

import fud.fud.Models.Event;

public class Routing extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routing2);
        Event event=(Event)getIntent().getSerializableExtra("Event");
        String location = "geo:0,0?q=42.7302,-73.6788("+event.getEventName()+")";
        //String location = "geo:0,0?q="+event.getAddress()+"("+event.getEventName()+")";
        Uri gmmIntentUri = Uri.parse(location);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);

        Button btn = (Button)findViewById(R.id.HomeButton);
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Routing.this, MainActivity.class));
            }
        });
    }

}
