package clement.broc;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;

import static android.app.Activity.RESULT_OK;
import static com.google.android.gms.internal.zzs.TAG;

/**
 * Created by clement on 28/05/2017.
 */

public class Tab4 extends Fragment implements View.OnClickListener {

    public static final int PICK_IMAGE_REQUEST = 234;
    private EditText editTextEventName;
    private EditText editTextEventAddress;
    private Button buttonChoose;
    private Button buttonEventAdd;
    private ImageView imageView;

    private DatabaseReference databaseReference;

    private Uri filePath;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle saveInstanceState) {
        View rootView = inflater.inflate(R.layout.tab4, container, false);
        rootView.setTag(TAG);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        editTextEventName = (EditText) rootView.findViewById(R.id.editTextEventName);
        editTextEventAddress = (EditText) rootView.findViewById(R.id.editTextEventAddress);
        buttonChoose = (Button) rootView.findViewById(R.id.buttonChoose);
        buttonEventAdd = (Button) rootView.findViewById(R.id.buttonEventAdd);
        imageView = (ImageView) rootView.findViewById(R.id.imageView);

        buttonChoose.setOnClickListener(this);
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


    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Sélectionner une image"), PICK_IMAGE_REQUEST);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getApplicationContext().getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
                ((ViewGroup) buttonChoose.getParent()).removeView(buttonChoose);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View view) {

        if(view == buttonChoose){
            showFileChooser();
        }

        if(view == buttonEventAdd){
            addEvent();
        }

    }
}
