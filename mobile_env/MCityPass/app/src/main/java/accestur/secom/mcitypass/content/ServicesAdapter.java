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
import java.util.List;

import accestur.secom.core.model.ServiceAgent;
import accestur.secom.mcitypass.R;

/**
 * Created by Sebastià on 5/6/2017.
 */

public class ServicesAdapter extends ArrayAdapter<ServiceAgent> {

    public ServicesAdapter(@NonNull Context context,  @NonNull ArrayList<ServiceAgent> objects) {
        super(context,0,  objects);
    }

    @NonNull
    @Override
    public View getView (int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView==null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.service_item,parent,false);
        }
        ServiceAgent serviceAgent = getItem(position);

        TextView textView = (TextView)  listItemView.findViewById(R.id.service_item_name);
        textView.setText(serviceAgent.getName());

        TextView textView1 = (TextView) listItemView.findViewById(R.id.service_item_times);
        textView1.setText("" +serviceAgent.getM());

        return listItemView;
    }

}
