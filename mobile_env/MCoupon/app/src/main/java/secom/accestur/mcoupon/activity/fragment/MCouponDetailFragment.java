package secom.accestur.mcoupon.activity.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLOutput;

import secom.accestur.core.model.MCoupon;
import secom.accestur.core.model.UserMCoupon;
import secom.accestur.core.service.impl.CounterMCouponService;
import secom.accestur.core.service.impl.MCouponService;
import secom.accestur.core.service.impl.UserMCouponService;
import secom.accestur.mcoupon.activity.R;
import secom.accestur.mcoupon.activity.tasks.BuyCouponTask;
import secom.accestur.mcoupon.activity.tasks.RedeemCouponTask;

//import accestur.secom.core.service.impl.ActivationService;
//import accestur.secom.core.service.impl.CounterService;
//import accestur.secom.core.service.impl.MCityPassService;
//import accestur.secom.core.service.impl.ServiceAgentService;
//import accestur.secom.mcitypass.R;
//import accestur.secom.mcitypass.content.CounterAdapter;
//import accestur.secom.mcitypass.tasks.ActivatePASSTask;
//import accestur.secom.mcitypass.tasks.InfiniteReusableTask;
//import accestur.secom.mcitypass.tasks.MTimesReusableTask;
//import accestur.secom.mcitypass.tasks.NonReusableTask;

public class MCouponDetailFragment extends Fragment {

    private TextView txtDetail;
    private String text;

    private CounterMCouponService counterService;
    private MCouponService mCouponService;
    int currentMCoupon;
    private MCoupon mCoupon;
    private UserMCouponService userMCouponService;

    public MCouponDetailFragment() {
    }

    public void setCurrentMCoupon(int i) {
        currentMCoupon = i;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mcpassdetail, container, false);
    }

    // public void mostrarDetalle(String texto) {
    //    text = texto;
    // }

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);
        // txtDetail = (TextView)getActivity().findViewById(R.id.textDetail);
        // txtDetail.setText(text);

        mCouponService = new MCouponService();
        mCoupon = mCouponService.initMCoupon(currentMCoupon + 1);

        TextView userText = (TextView) getActivity().findViewById(R.id.textUser);
//        //int lifetime = Integer.parseInt(mCityPassService.getMCityPass().getLifeTime()) / (3600 * 1000 * 24);
        if(mCoupon.getUser()==null){
            userText.setText("Cap");
        }else {
            userText.setText("" + mCoupon.getUser().getUsername().toString());
        }

        TextView counterText = (TextView) getActivity().findViewById(R.id.textCounter);
        counterText.setText(Integer.toString(mCoupon.getCounterMCoupon().getCounterMCoupon()));

        TextView expDateText = (TextView) getActivity().findViewById(R.id.textExpDate);
        if(mCoupon.getEXD()==null){
            System.out.println("NO EXPDATE");
        }else {
            expDateText.setText("" + mCoupon.getEXD().toString());
        }


        TextView merchantText = (TextView) getActivity().findViewById(R.id.Merchant);
        String merchant = mCoupon.getMerchant();
        merchantText.setText(merchant);



        Button buyButton = (Button) getActivity().findViewById(R.id.buyButton);
        Button redeemButton = (Button) getActivity().findViewById(R.id.redeemButton);
        Button shareButton = (Button) getActivity().findViewById(R.id.shareButton);
        redeemButton.setVisibility(View.INVISIBLE);
        shareButton.setVisibility(View.INVISIBLE);
        if (mCouponService.getMCoupon().getUser() == null) {
            buyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BuyCouponTask buyCouponTask = new BuyCouponTask(getActivity());
                    buyCouponTask.execute(mCouponService.getMCoupon().getId());
                }
            });
        } else {
            buyButton.setVisibility(View.INVISIBLE);
            if(mCoupon.getCounterMCoupon().getCounterMCoupon()!=0) {
                redeemButton.setVisibility(View.VISIBLE);
                shareButton.setVisibility(View.VISIBLE);
                redeemButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RedeemCouponTask redeemCouponTask = new RedeemCouponTask(getActivity());
                        redeemCouponTask.execute(mCouponService.getMCoupon().getId());
                    }
                });
            }else{
                redeemButton.setVisibility(View.INVISIBLE);
                Toast toast = Toast.makeText(getActivity(), "This Coupon Can't Be Redeemed Again", Toast.LENGTH_LONG);
                toast.show();
            }
        }

//        ListView listView = (ListView) getActivity().findViewById(R.id.listServicesUse);
//        serviceAgentService = new ServiceAgentService();
//        counterService = new CounterService();
//
//        CounterAdapter servicesAdapter = new CounterAdapter(getActivity(), counterService.getCountersByMCityPass(mCityPassService.getMCityPass().getId()));
//        listView.setAdapter(servicesAdapter);
//
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> list, View view, int pos, long id) {
//                if (activationService.getActivation() != null) {
//                    if (counterService.getCounters().get(pos).getService().getM() == 1 ) {
//                        if(counterService.getCounters().get(pos).getCounter() >0){
//                            NonReusableTask nonReusableTask = new NonReusableTask(getActivity());
//                            nonReusableTask.execute(mCityPassService.getMCityPass().getId(),counterService.getCounters().get(pos).getService().getId());
//                        } else {
//                            Toast toast = Toast.makeText(getActivity(), "This ticket can't be reused", Toast.LENGTH_LONG);
//                            toast.show();
//                        }
//
//                    } else if (counterService.getCounters().get(pos).getService().getM() == -1) {
//                        InfiniteReusableTask infiniteReusableTask = new InfiniteReusableTask(getActivity());
//                        infiniteReusableTask.execute(mCityPassService.getMCityPass().getId(), counterService.getCounters().get(pos).getService().getId());
//                    } else {
//                        if(counterService.getCounters().get(pos).getCounter() >0) {
//                            MTimesReusableTask mTimesReusableTask = new MTimesReusableTask(getActivity());
//                            mTimesReusableTask.execute(mCityPassService.getMCityPass().getId(), counterService.getCounters().get(pos).getService().getId());
//                        } else{
//                            Toast toast = Toast.makeText(getActivity(), "You ticket can't be reused anymore", Toast.LENGTH_LONG);
//                            toast.show();
//                        }
//                    }
//                } else {
//                    Toast toast = Toast.makeText(getActivity(), "Activate the PASS first", Toast.LENGTH_LONG);
//                    toast.show();
//                }
//
//
//            }
//        });
//
//    }
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