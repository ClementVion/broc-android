package clement.broc;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonSignIn;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignup;

    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null) {
            //start profile activity here
            finish();
            startActivity(new Intent(getApplicationContext(), NavActivity.class));
        }

        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        buttonSignIn = (Button) findViewById(R.id.buttonSignIn);
        textViewSignup = (TextView) findViewById(R.id.textViewSignUp);

        progressDialog = new ProgressDialog(this);

        buttonSignIn.setOnClickListener(this);
        textViewSignup.setOnClickListener(this);
    }

    private void userLogin(){

        // Getting email and password from edit texts
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        // Checking if email and password are empty
        if(TextUtils.isEmpty(email)) {
            // email is empty
            Toast.makeText(this, "Entrez un email", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(password)) {
            // password is empty
            Toast.makeText(this, "Entrez un mot de passe", Toast.LENGTH_SHORT).show();
            return;
        }
        // If validations are ok we will show a progress dialog

        progressDialog.setMessage("Signin User...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();

                        if(task.isSuccessful()){
                            // start the profile activity
                            finish();
                            startActivity(new Intent(getApplicationContext(), NavActivity.class));
                        }
                    }
                });
    }

    @Override
    public void onClick(View view) {

        if(view == buttonSignIn){
            userLogin();
        }

        if(view == textViewSignup){
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }
    }
}
