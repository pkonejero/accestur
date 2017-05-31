package accestur.secom.mcitypass.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import accestur.secom.mcitypass.R;
import accestur.secom.mcitypass.activity.MainActivity;

public class MCPassListFragment extends Fragment {

    public MCPassListFragment() {}

    ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_mcpasslist, container, false);

        String[] elementos = {"jose", "pedro", "maria", "miguel", "luis", "daniel", "elena", "Laura", "Sofia"};

        ListView lv = (ListView)rootView.findViewById(R.id.listView);

        ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(getActivity().getBaseContext(), android.R.layout.simple_expandable_list_item_1, elementos);       // adapter.clear();
        lv.setAdapter(itemsAdapter);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getActivity().getApplicationContext(),String.valueOf(position),Toast.LENGTH_SHORT).show();
//
//            }
//        });

        return inflater.inflate(R.layout.fragment_mcpasslist, container, false);
    }
}
