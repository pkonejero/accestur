package secom.accestur.mcoupon.activity.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import secom.accestur.core.api.IssuerAPI;
import secom.accestur.core.api.MerchantAPI;
import secom.accestur.core.crypto.Cryptography;
import secom.accestur.core.model.MCoupon;
import secom.accestur.core.model.UserMCoupon;
import secom.accestur.core.service.impl.CounterMCouponService;
import secom.accestur.core.service.impl.MCouponService;
import secom.accestur.core.service.impl.UserMCouponService;
import secom.accestur.core.utils.Constants;
import secom.accestur.mcoupon.activity.activity.MainActivity;
import secom.accestur.mcoupon.activity.fragment.MCouponListFragment;

//import accestur.secom.core.api.IssuerAPI;
//import accestur.secom.core.service.impl.UserService;
//import accestur.secom.core.utils.Constants;
//import accestur.secom.mcitypass.activity.MainActivity;
//import accestur.secom.mcitypass.fragment.MCPassListFragment;

public class RedeemCouponTask extends AsyncTask<Long, Void, Boolean> {

    Context context;
    UserMCouponService userMCouponService = new UserMCouponService();
    UserMCoupon user = new UserMCoupon();
    MCoupon mCoupon;
    MCouponService mCouponService = new MCouponService();
    CounterMCouponService counterMCouponService = new CounterMCouponService();
    Cryptography crypto2= new Cryptography();

    private String namec;

    private String signature;

    private String merchant;

    private String request;


    public RedeemCouponTask(Context context) {
        this.context = context;
    }

    @Override
    protected Boolean doInBackground(Long... params) {

        MerchantAPI merchantAPI = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build()
                .create(MerchantAPI.class);

        //AGAFAM L'USUARI QUE HA DE COMPRAR EL COUPON(AGAFAR AMB UNA SELECT)
        user = userMCouponService.getUserMCouponByUsername(UserMCouponService.getUserConnected());
        mCoupon = mCouponService.getmCouponbyId(params[0]);

        Call<String> stringCall = merchantAPI.getParamsRedeem();

        String message = "";
        try{
            message = stringCall.execute().body();
            System.out.println(message);
            JSONArray jsonArray = new JSONArray(message);
            JSONObject json = jsonArray.getJSONObject(0);
            namec = json.getString("namec");
            signature = json.getString("signature");
            merchant = json.getString("merchant");
            crypto2.initPublicKey(Constants.PATH_ISSUER_KEY);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //catch (JSONException e) {
//            e.printStackTrace();
//        }
        try {
            if(crypto2.getValidation(merchant+namec,signature))
            request = userMCouponService.initRedeemMCoupon(mCoupon,mCoupon.getId(),user,mCoupon.getCounterMCoupon().getCounterMCoupon(),message);
            mCoupon.getCounterMCoupon().setCounterMCoupon(mCoupon.getCounterMCoupon().getCounterMCoupon()-1);

            stringCall = merchantAPI.getChallengeRedeem(request);

            message = null;
            message = stringCall.execute().body();

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        boolean aBoolean = false;
        if(message==null){
            return aBoolean;
        }else{
            if(message.equals("FAILED")){
                return aBoolean;
            }
            aBoolean= true;
            return aBoolean;
        }

    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        if(aBoolean){

            Toast toast = Toast.makeText(context, "You Redeemed the coupon", Toast.LENGTH_LONG);
            toast.show();
            MCouponListFragment mCouponListFragment = new MCouponListFragment();
           ((MainActivity)context).replaceFragment(mCouponListFragment);

        } else {
            Toast toast = Toast.makeText(context, "An error has occurred Redeeming Coupon.",  Toast.LENGTH_LONG);
            toast.show();
        }

    }


}
