package clement.broc;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.google.android.gms.internal.zzs.TAG;

/**
 * Created by clement on 28/05/2017.
 */

public class Tab4 extends Fragment implements View.OnClickListener {

    private EditText editTextEventName;
    private EditText editTextEventAddress;
    private Button buttonEventAdd;

    private DatabaseReference databaseReference;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle saveInstanceState) {
        View rootView = inflater.inflate(R.layout.tab4, container, false);
        rootView.setTag(TAG);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        editTextEventName = (EditText) rootView.findViewById(R.id.editTextEventName);
        editTextEventAddress = (EditText) rootView.findViewById(R.id.editTextEventAddress);
        buttonEventAdd = (Button) rootView.findViewById(R.id.buttonEventAdd);

        buttonEventAdd.setOnClickListener(this);

        return rootView;
    }


    public void addEvent() {
        String name = editTextEventName.getText().toString().trim();
        String address = editTextEventAddress.getText().toString().trim();

        EventInformation eventInformation = new EventInformation(name, address);

        DatabaseReference postReference = databaseReference.child("events").push();

        postReference.setValue(eventInformation);

        Toast.makeText(getActivity(), "Evènement enregistré", Toast.LENGTH_LONG).show();

        editTextEventName.getText().clear();
        editTextEventAddress.getText().clear();
    }


    @Override
    public void onClick(View view) {

        if(view == buttonEventAdd){
            addEvent();
        }

    }
}
