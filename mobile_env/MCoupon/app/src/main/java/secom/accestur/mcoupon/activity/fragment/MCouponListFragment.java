package secom.accestur.mcoupon.activity.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import secom.accestur.core.service.impl.UserMCouponService;
import secom.accestur.mcoupon.activity.R;

import secom.accestur.core.model.MCoupon;
import secom.accestur.core.service.impl.MCouponService;

import secom.accestur.mcoupon.activity.content.MCouponItem;
import secom.accestur.mcoupon.activity.content.MCouponAdapter;

public class MCouponListFragment extends Fragment {

    private ListView listView;
    private MCouponService mCouponService;
    private ArrayList<MCoupon> mCoupons;
    private UserMCouponService userMCouponService;

    public MCouponListFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mcpasslist, container, false);
    }

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

        mCouponService = new MCouponService();


        mCoupons = (ArrayList) mCouponService.getAllMCoupon();

        ArrayList<MCoupon> mCoupons1 = new ArrayList<MCoupon>();

        int i = 0;
        int j = 0;
        for(i=0;i<=mCoupons.size()-1;i++){

            if (mCoupons.get(i).getUser()!=null){

                if (mCoupons.get(i).getUser().getUsername().equals(UserMCouponService.getUserConnected())){
                    mCoupons1.add(j,mCoupons.get(i));
                    j++;
                }

            }

        }


        listView = (ListView)getView().findViewById(R.id.listView);
        listView.setAdapter(new MCouponAdapter(getActivity(), mCoupons1));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> list, View view, int pos, long id) {

                MCouponDetailFragment mCouponDetailFragment = new MCouponDetailFragment();
                mCouponDetailFragment.setCurrentMCoupon(pos);
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();
                transaction.replace(R.id.main_fragment_placeholder, mCouponDetailFragment);
                transaction.commit();

            }
        });


    }



}