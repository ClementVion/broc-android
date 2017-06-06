package clement.broc;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static com.google.android.gms.internal.zzs.TAG;

/**
 * Created by clement on 28/05/2017.
 */

public class Tab3 extends Fragment {

    private TextView profileEmail;

    private FirebaseAuth firebaseAuth;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle saveInstanceState) {
        View rootView = inflater.inflate(R.layout.tab3, container, false);
        rootView.setTag(TAG);

        firebaseAuth = FirebaseAuth.getInstance();

        profileEmail = (TextView) rootView.findViewById(R.id.profileEmail);

        FirebaseUser user = firebaseAuth.getCurrentUser();
        profileEmail.setText(user.getEmail());

        return rootView;
    }
}
