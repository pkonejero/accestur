package accestur.secom.mcitypass.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import accestur.secom.mcitypass.R;

public class MCPassListFragment extends Fragment {

    public MCPassListFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mcpasslist, container, false);
    }
}
