package accestur.secom.mcitypass.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import accestur.secom.mcitypass.R;

public class SettingsFragment extends Fragment {

    private TextView txtConfiguration;

    public SettingsFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);
        txtConfiguration = (TextView)getActivity().findViewById(R.id.textConfiguration);
        txtConfiguration.setText("SETTINGS");
    }
}
