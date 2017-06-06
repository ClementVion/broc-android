package clement.broc;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static com.google.android.gms.internal.zzs.TAG;

/**
 * Created by clement on 28/05/2017.
 */

public class Tab3 extends Fragment implements View.OnClickListener {

    private TextView profileEmail;
    private TextView profileSettingsAccount;
    private TextView profileSettingsEmail;
    private TextView profileSettingsEvents;

    private FirebaseAuth firebaseAuth;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle saveInstanceState) {
        View rootView = inflater.inflate(R.layout.tab3, container, false);
        rootView.setTag(TAG);

        firebaseAuth = FirebaseAuth.getInstance();

        profileEmail = (TextView) rootView.findViewById(R.id.profileEmail);
        profileSettingsAccount = (TextView) rootView.findViewById(R.id.profileSettingsAccount);
        profileSettingsEmail = (TextView) rootView.findViewById(R.id.profileSettingsEmail);
        profileSettingsEvents = (TextView) rootView.findViewById(R.id.profileSettingsEvents);

        FirebaseUser user = firebaseAuth.getCurrentUser();
        profileEmail.setText(user.getEmail());

        profileSettingsAccount.setOnClickListener(this);
        profileSettingsEmail.setOnClickListener(this);
        profileSettingsEvents.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View view) {

        if(view == profileSettingsAccount) {
            Toast.makeText(getContext(), "Cette fonctionnalité sera bientôt disponible !", Toast.LENGTH_SHORT).show();
        }

        if(view == profileSettingsEmail) {
            Toast.makeText(getContext(), "Cette fonctionnalité sera bientôt disponible !", Toast.LENGTH_SHORT).show();
        }

        if(view == profileSettingsEvents) {
            Toast.makeText(getContext(), "Cette fonctionnalité sera bientôt disponible !", Toast.LENGTH_SHORT).show();
        }

    }
}
