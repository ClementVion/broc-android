package clement.broc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EventActivity extends AppCompatActivity {

    DatabaseReference databaseEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        databaseEvents = FirebaseDatabase.getInstance().getReference("events");

        // Get the id corresponding to the event clicked
        final String eventId;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                eventId = null;
            } else {
                eventId = extras.getString("eventId");
            }
        } else {
            eventId = (String) savedInstanceState.getSerializable("eventId");
        }

        // Retrieve the event from the database by its id
        //String EventName = databaseEvents.child(eventId).child("name").toString().trim();
        //String EventAddress = databaseEvents.child(eventId).child("address").toString().trim();

        //System.out.println("Event : " + databaseEvents.child(eventId).("name"));

        databaseEvents.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                EventInformation event = dataSnapshot.child(eventId).getValue(EventInformation.class);
                System.out.println("Event : " + event.getName());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }
}
