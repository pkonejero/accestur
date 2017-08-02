package secom.accestur.mcoupon.activity.tasks;

import android.os.AsyncTask;

import com.activeandroid.query.Select;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

//import accestur.secom.core.api.ServicesAPI;
//import accestur.secom.core.model.ServiceAgent;
//import accestur.secom.core.service.impl.ServiceAgentService;
//import accestur.secom.core.utils.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import secom.accestur.core.api.ManufacturerAPI;
import secom.accestur.core.model.CounterMCoupon;
import secom.accestur.core.model.MCoupon;
import secom.accestur.core.service.impl.CounterMCouponService;
import secom.accestur.core.service.impl.MCouponService;
import secom.accestur.core.utils.Constants;

public class OffersTask extends AsyncTask<Long, Void, Void> {

    MCouponService serviceMCoupon = new MCouponService();
    CounterMCouponService counterMCouponService = new CounterMCouponService();

    @Override
    protected Void doInBackground(Long... params) {
        ManufacturerAPI manufacturerAPI = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build()
                .create(ManufacturerAPI.class);


        Call<String> stringCall = manufacturerAPI.getParamsCoupon();

        String message = "";
        try{
           message = stringCall.execute().body();
            System.out.println(message);
        } catch (IOException e) {
            e.printStackTrace();
        }

        storeServices(message);

        return null;
    }


    private void storeServices(String message){
        try {
            JSONArray jsonArray = new JSONArray(message);
            JSONObject json;
            MCoupon mCoupon;
            CounterMCoupon counterMCoupon = null;
            for(int i = 0; i < jsonArray.length(); i++){
                json = jsonArray.getJSONObject(i);
                mCoupon = new Select().from(MCoupon.class).where("merchant = ? ", json.getString("merchant")).executeSingle();
                if(mCoupon == null) {
                    mCoupon = new MCoupon();
                    int p = json.getInt("p");
                    counterMCoupon = new CounterMCoupon();
                    counterMCoupon.setCounterMCoupon(p);
                    counterMCouponService.storeCounterMCoupon(counterMCoupon);

                    mCoupon.setMerchant(json.getString("merchant"));
                    mCoupon.setP(p);
                    mCoupon.setQ(json.getInt("q"));


                   mCoupon.setCounterMCoupon(counterMCoupon);
               } else {
                   System.out.println("MCoupon  already exists");
               }


                serviceMCoupon.storeMCoupon(mCoupon);

                //counterMCoupon.setmCoupon(mCoupon);
                //counterMCouponService.storeCounterMCoupon(counterMCoupon);
            }



        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    /*
    @Override
    protected void onPostExecute(Void aVoid) {
       ///serviceAgentService = new ServiceAgentService();
        //serviceAgentService.initService(1, true);

        //serviceAgentService.saveServiceAgent();
       // serviceAgentService.loadServiceAgent("TenTimesReusable");

        //System.out.println("ServiceAgent Name: " + serviceAgentService.getServiceAgent().getName());
    }
    */
}


