package accestur.secom.mcitypass.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import accestur.secom.core.model.MCityPass;
import accestur.secom.core.service.impl.MCityPassService;
import accestur.secom.mcitypass.R;
import accestur.secom.mcitypass.content.MCPassItem;
import accestur.secom.mcitypass.content.MCityPassAdapter;

public class MCPassListFragment extends Fragment {

   private MCPassItem[] datos;
    /*=
            new MCPassItem[]{
                    new MCPassItem("MCityPass 1", "Info 1", "Detail 1"),
                    new MCPassItem("MCityPass 2", "Info 2", "Detail 2"),
                    new MCPassItem("MCityPass 3", "Info 3", "Detail 3"),
                    new MCPassItem("MCityPass 4", "Info 4", "Detail 4"),
                    new MCPassItem("MCityPass 5", "Info 5", "Detail 5")};*/


    private ListView listView;
    private MCPassListener listener;
    private MCityPassService mCityPassService;
    private ArrayList<MCityPass> mCityPasses;

    public MCPassListFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mcpasslist, container, false);
    }

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

        mCityPassService = new MCityPassService();
        mCityPasses = (ArrayList) mCityPassService.getAllMCityPass();

        datos = new MCPassItem[mCityPasses.size()];
        for (int i = 0; i< datos.length; i++){
            int lifetime = Integer.parseInt(mCityPasses.get(i).getLifeTime())/(3600*1000*24);
            datos[i] = new MCPassItem("" + lifetime + " days MCityPASS",  mCityPasses.get(i).getCategory(), mCityPasses.get(i).getExpDate());

        }

        listView = (ListView)getView().findViewById(R.id.listView);
        listView.setAdapter(new MCPassAdapter(this));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> list, View view, int pos, long id) {
                if (listener!=null) {
                    listener.onMCPassItemSeleccionado(
                            (MCPassItem)listView.getAdapter().getItem(pos));
                }
            }
        });
    }

    class MCPassAdapter extends ArrayAdapter<MCPassItem> {

        Activity context;

        MCPassAdapter(Fragment context) {
            super(context.getActivity(), R.layout.listitem_correo, datos);
            this.context = context.getActivity();
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = context.getLayoutInflater();
            View item = inflater.inflate(R.layout.listitem_correo, null);

            TextView lblDe = (TextView)item.findViewById(R.id.LblDe);
            lblDe.setText(datos[position].getDe());

            //TextView lblAsunto = (TextView)item.findViewById(R.id.LblAsunto);
            //lblAsunto.setText(datos[position].getAsunto());

            return(item);
        }
    }

    public interface MCPassListener {
        void onMCPassItemSeleccionado(MCPassItem c);
    }

    public void setMCPassListListener(MCPassListener listener) {
        this.listener=listener;
    }


}