package accestur.secom.mcitypass.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import accestur.secom.core.service.impl.UserService;
import accestur.secom.mcitypass.R;
import accestur.secom.mcitypass.tasks.GeneratePseudonymTask;

public class SettingsFragment extends Fragment {

    private TextView txtConfiguration;

    private Button generatePseudonym;

    private TextView pseudonym;

    public SettingsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);
        // txtConfiguration = (TextView)getActivity().findViewById(R.id.textConfiguration);
        // txtConfiguration.setText("SETTINGS");

        generatePseudonym = (Button) getActivity().findViewById(R.id.pseudonymButton);
        pseudonym = (TextView) getActivity().findViewById(R.id.pseudonymText);

        UserService userService = new UserService();
        userService.loadUser(1);
        if (userService.getUser() == null) {
            pseudonym.setVisibility(View.INVISIBLE);
            generatePseudonym.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    GeneratePseudonymTask generatePseudonymTask = new GeneratePseudonymTask();
                    generatePseudonymTask.execute();
                    generatePseudonym.setVisibility(View.INVISIBLE);
                    pseudonym.setVisibility(View.VISIBLE);
                }
            });
        } else {
            generatePseudonym.setVisibility(View.INVISIBLE);
        }

    }
}
