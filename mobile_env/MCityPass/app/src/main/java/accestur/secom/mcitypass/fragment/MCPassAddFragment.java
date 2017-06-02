package accestur.secom.mcitypass.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import accestur.secom.mcitypass.R;

public class MCPassAddFragment extends Fragment {

    private TextView txtCategory;

    public MCPassAddFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mcpassadd, container, false);
    }

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);
        txtCategory = (TextView)getActivity().findViewById(R.id.textCategory);
        txtCategory.setText("MCPASSADDFRAGMENT");
    }
}