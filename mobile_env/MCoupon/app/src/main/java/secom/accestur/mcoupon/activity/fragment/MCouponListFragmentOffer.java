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

import secom.accestur.core.model.MCoupon;
import secom.accestur.core.service.impl.MCouponService;
import secom.accestur.core.service.impl.UserMCouponService;
import secom.accestur.mcoupon.activity.R;
import secom.accestur.mcoupon.activity.content.MCouponAdapter;

public class MCouponListFragmentOffer extends Fragment {

    private ListView listView;
    private MCouponService mCouponService;
    private ArrayList<MCoupon> mCoupons;
    private UserMCouponService userMCouponService;
    private ArrayList<MCoupon> mCoupons1 = new ArrayList<MCoupon>();
    public MCouponListFragmentOffer() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mcouponofferlist, container, false);
    }

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

        mCouponService = new MCouponService();


        mCoupons = (ArrayList) mCouponService.getAllMCoupon();

        //mCoupons1 = new ArrayList<MCoupon>();
        mCoupons1.clear();

        int i = 0;
        int j = 0;
        for(i=0;i<=mCoupons.size()-1;i++){
            if (mCoupons.get(i).getUser()==null){
                mCoupons1.add(j,mCoupons.get(i));
                j++;
            }
        }


        listView = (ListView)getView().findViewById(R.id.listView1);
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