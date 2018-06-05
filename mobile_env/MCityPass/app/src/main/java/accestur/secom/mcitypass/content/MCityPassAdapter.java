package accestur.secom.mcitypass.content;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import accestur.secom.core.model.MCityPass;
import accestur.secom.core.model.ServiceAgent;
import accestur.secom.mcitypass.R;

/**
 * Created by Sebastià on 6/6/2017.
 */

public class MCityPassAdapter extends ArrayAdapter<MCityPass> {

    public MCityPassAdapter(@NonNull Context context,  @NonNull ArrayList<MCityPass> objects) {
        super(context,0,  objects);
    }

    @NonNull
    @Override
    public View getView (int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView==null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.service_item,parent,false);
        }
        MCityPass mCityPass = getItem(position);

        TextView textView = (TextView)  listItemView.findViewById(R.id.service_item_name);
        int lifetime = Integer.parseInt(mCityPass.getLifeTime())/(3600*1000*24);
        textView.setText("" +lifetime + " days MCityPASS");

        TextView textView1 = (TextView) listItemView.findViewById(R.id.service_item_times);
        textView1.setText(mCityPass.getCategory());

        return listItemView;
    }
}
