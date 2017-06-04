package clement.broc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class EventActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imageViewEventPicture;
    private TextView textViewEventName;
    private TextView textViewEventAddress;
    private TextView textViewEventDate;
    private TextView textViewEventParticipate;

    DatabaseReference databaseEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        imageViewEventPicture = (ImageView) findViewById(R.id.imageViewEventPicture);
        textViewEventName = (TextView)findViewById(R.id.textViewEventName);
        textViewEventAddress = (TextView) findViewById(R.id.textViewEventAddress);
        textViewEventDate = (TextView) findViewById(R.id.textViewEventDate);
        textViewEventParticipate = (TextView) findViewById(R.id.textViewEventParticipate);

        databaseEvents = FirebaseDatabase.getInstance().getReference("events");

        textViewEventParticipate.setOnClickListener(this);

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

        databaseEvents.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                EventInformation event = dataSnapshot.child(eventId).getValue(EventInformation.class);

                Picasso.with(getApplicationContext())
                        .load(event.getImageUrl())
                        .into(imageViewEventPicture);

                textViewEventName.setText(event.getName());
                textViewEventAddress.setText(event.getAddress());
                textViewEventDate.setText("Le " + event.getDate());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    @Override
    public void onClick(View view) {

        if(view == textViewEventParticipate) {
            Toast.makeText(this, "Cette fonctionnalité sera bientôt disponible !", Toast.LENGTH_SHORT).show();
        }

    }
}
