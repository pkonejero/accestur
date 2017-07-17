package secom.accestur.mcoupon.activity.content;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

//import accestur.secom.core.model.MCityPass;
//import accestur.secom.mcitypass.R;
import secom.accestur.core.model.MCoupon;
import secom.accestur.mcoupon.activity.activity.R;

/**
 * Created by gornals on 13/07/2017.
 */

public class MCouponAdapter extends ArrayAdapter<MCoupon> {

    public MCouponAdapter(@NonNull Context context, @NonNull ArrayList<MCoupon> objects) {
        super(context,0,  objects);
    }

    @NonNull
    @Override
    public View getView (int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView==null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.service_item,parent,false);
        }
        MCoupon mCoupon = getItem(position);

        TextView textView = (TextView)  listItemView.findViewById(R.id.service_item_name);
        textView.setText("P="+mCoupon.getP().toString());

        TextView textView1 = (TextView) listItemView.findViewById(R.id.service_item_times);
        textView1.setText("Merchant="+mCoupon.getMerchant().toString());

        return listItemView;
    }
}
