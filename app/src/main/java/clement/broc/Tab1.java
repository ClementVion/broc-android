package clement.broc;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.google.android.gms.internal.zzs.TAG;

/**
 * Created by clement on 28/05/2017.
 */

public class Tab1 extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    private List<ListItem> listItems;

    DatabaseReference databaseEvents;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle saveInstanceState) {
        View rootView = inflater.inflate(R.layout.tab1, container, false);
        rootView.setTag(TAG);

        databaseEvents = FirebaseDatabase.getInstance().getReference("events");

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        listItems = new ArrayList<>();

        // Retrieve data from Firebase
        databaseEvents.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                listItems.clear();

                for(DataSnapshot eventSnapshot : dataSnapshot.getChildren()){
                    EventInformation event = eventSnapshot.getValue(EventInformation.class);

                    ListItem listItem = new ListItem(
                            event.getName(),
                            event.getAddress(),
                            eventSnapshot.getKey(),
                            event.getImageUrl()
                    );

                    listItems.add(listItem);
                }

                adapter = new MyAdapter(listItems, getActivity());

                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });


        adapter = new MyAdapter(listItems, getActivity());

        recyclerView.setAdapter(adapter);
        return rootView;
    }
}
