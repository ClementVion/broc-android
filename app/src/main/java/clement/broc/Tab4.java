package clement.broc;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.Calendar;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;
import static com.google.android.gms.internal.zzs.TAG;

/**
 * Created by clement on 28/05/2017.
 */

public class Tab4 extends Fragment implements
        View.OnClickListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    public static final int PICK_IMAGE_REQUEST = 234;
    private EditText editTextEventName;
    private EditText editTextEventAddress;
    private Button buttonChoose;
    private EditText buttonDatePicker;
    private Button buttonEventAdd;
    private ImageView imageView;

    private DatabaseReference databaseReference;

    private Uri filePath;

    private StorageReference storageReference;

    int day, month, year, hour, minute;
    int dayFinal, monthFinal, yearFinal, hourFinal, minuteFinal;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle saveInstanceState) {
        View rootView = inflater.inflate(R.layout.tab4, container, false);
        rootView.setTag(TAG);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        storageReference = FirebaseStorage.getInstance().getReference();

        editTextEventName = (EditText) rootView.findViewById(R.id.editTextEventName);
        editTextEventAddress = (EditText) rootView.findViewById(R.id.editTextEventAddress);
        buttonChoose = (Button) rootView.findViewById(R.id.buttonChoose);
        buttonDatePicker = (EditText) rootView.findViewById(R.id.buttonDatePicker);
        buttonEventAdd = (Button) rootView.findViewById(R.id.buttonEventAdd);
        imageView = (ImageView) rootView.findViewById(R.id.imageView);

        buttonChoose.setOnClickListener(this);
        buttonDatePicker.setOnClickListener(this);
        buttonEventAdd.setOnClickListener(this);

        return rootView;
    }


    public void addEvent(Uri imageUrl) {
        String name = editTextEventName.getText().toString().trim();
        String address = editTextEventAddress.getText().toString().trim();

        // Datetime init
        String dateTime = dayFinal + "/" + monthFinal + "/" + yearFinal  + " à " + hourFinal + "h" + minuteFinal;

        // Transform url into string to fit the db
        String imageUrlString;
        imageUrlString = imageUrl.toString();

        // Progress dialog
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Enregistrement de l'évènement...");
        progressDialog.show();

        // Create new model
        EventInformation eventInformation = new EventInformation(name, address, imageUrlString, dateTime);

        // Push to firebase db
        DatabaseReference postReference = databaseReference.child("events").push();
        postReference.setValue(eventInformation);

        // Hide progress dialog
        progressDialog.dismiss();
        Toast.makeText(getActivity(), "Evènement enregistré", Toast.LENGTH_LONG).show();

        // Reset inputs
        editTextEventName.getText().clear();
        editTextEventAddress.getText().clear();
        buttonDatePicker.getText().clear();

        imageView.setImageDrawable(null);
        imageView.setVisibility(View.GONE);
        buttonChoose.setVisibility(View.VISIBLE);
    }

    private void uploadFile() {

        if(filePath != null) {

            final ProgressDialog progressDialog = new ProgressDialog(getActivity());
            progressDialog.setTitle("Enregistrement de l'image...");
            progressDialog.show();

            StorageReference riversRef = storageReference.child("images/event_" + UUID.randomUUID().toString() + ".jpg");

            riversRef.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Uri downloadUrl = taskSnapshot.getDownloadUrl();
                            progressDialog.dismiss();
                            addEvent(downloadUrl);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            //Toast.makeText(getActivity().getApplicationContext(), "Problème durant l'enregistrement de l'image", Toast.LENGTH_LONG).show();
                        }
                    });
        }else {
            addEvent(null);
        }
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
                imageView.setVisibility(View.VISIBLE);
                buttonChoose.setVisibility(View.GONE);
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

        if(view == buttonDatePicker){
            chooseDate();
        }

        if(view == buttonEventAdd){
            //addEvent();
            uploadFile();
        }

    }

    private void chooseDate() {
        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), (DatePickerDialog.OnDateSetListener)this, year, month, day);
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        yearFinal = i;
        monthFinal = i1 + 1;
        dayFinal = i2;

        Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), (TimePickerDialog.OnTimeSetListener)this, hour, minute, DateFormat.is24HourFormat(getActivity()));
        timePickerDialog.show();
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        hourFinal = i;
        minuteFinal = i1;

        buttonDatePicker.setText(dayFinal + "/" + monthFinal + "/" + yearFinal  + " à " + hourFinal + "h" + minuteFinal);
    }
}
