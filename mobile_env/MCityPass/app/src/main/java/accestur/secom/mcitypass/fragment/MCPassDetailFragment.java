package accestur.secom.mcitypass.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import accestur.secom.mcitypass.R;
import accestur.secom.mcitypass.content.MCPassItem;

public class MCPassDetailFragment extends Fragment {

    public MCPassDetailFragment() {}
    TextView txtDetalle;
    String text;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mcpassdetail, container, false);
    }

    public void mostrarDetalle(String texto) {
        text = texto;
    }

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);
        txtDetalle = (TextView)getActivity().findViewById(R.id.TxtDetalle);
        txtDetalle.setText(text);
    }


//    @Override
//    public void onViewCreated(View view, Bundle savedInstanceState) {
//       int counter=0;
//       NonReusableTask nonReusableTask = new NonReusableTask();
//       nonReusableTask.execute();
//
//       MTimesReusableTask mTimesReusableTask = new MTimesReusableTask();
//       mTimesReusableTask.execute(counter);
//
//       InfiniteReusableTask infiniteReusableTask = new InfiniteReusableTask();
//       infiniteReusableTask.execute();
//    }

}