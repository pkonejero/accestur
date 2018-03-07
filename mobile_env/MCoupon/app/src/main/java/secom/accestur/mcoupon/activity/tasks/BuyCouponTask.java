package secom.accestur.mcoupon.activity.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

//import accestur.secom.core.api.IssuerAPI;
//import accestur.secom.core.service.impl.UserService;
//import accestur.secom.core.utils.Constants;
//import accestur.secom.mcitypass.activity.MainActivity;
//import accestur.secom.mcitypass.fragment.MCPassListFragment;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import secom.accestur.core.model.CounterMCoupon;
import secom.accestur.core.model.MCoupon;
import secom.accestur.core.model.UserMCoupon;
import secom.accestur.core.service.impl.MCouponService;
import secom.accestur.core.service.impl.UserMCouponService;
import secom.accestur.core.api.IssuerAPI;
import secom.accestur.core.utils.Constants;
import secom.accestur.mcoupon.activity.activity.MainActivity;
import secom.accestur.mcoupon.activity.fragment.MCouponListFragment;

public class BuyCouponTask extends AsyncTask<Long, Void, Boolean> {

    Context context;
    UserMCouponService userMCouponService = new UserMCouponService();
    UserMCoupon user = new UserMCoupon();
    MCouponService mCouponService = new MCouponService();

    public BuyCouponTask(Context context) {
        this.context = context;
    }

    @Override
    protected Boolean doInBackground(Long... params) {

        IssuerAPI issuerAPI = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build()
                .create(IssuerAPI.class);


        //AGAFAM L'USUARI QUE HA DE COMPRAR EL COUPON(AGAFAR AMB UNA SELECT)
        user = userMCouponService.getUserMCouponByUsername(UserMCouponService.getUserConnected());
        MCoupon coupon = mCouponService.getmCouponbyId(params[0]);

        userMCouponService.setUserCoupon(params[0],user);
        //GENERAM ELS PARAMETRES X,Y, YO, XO i els guardam a l'usuari per ara.
//        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//        String data= "26/07/1992";
//        Date date = new Date();
//        try {
//            date = dateFormat.parse(data);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
        userMCouponService.generateUserParamsCoupon(coupon.getP(),coupon.getQ(),user,coupon.getEXD());
        JSONObject json = new JSONObject();
        try {
            json.put("username",user.getUsername());
            json.put("id",params[0].toString());
            json.put("Xo",user.getXo());
            json.put("Yo",user.getYo());
            json.put("p",coupon.getP().toString());
            json.put("q",coupon.getQ().toString());
            json.put("merchant",coupon.getMerchant());
            json.put("EXD",coupon.getEXD());
            json.put("signature", user.getSignature());
            Call<String> stringCall = issuerAPI.setUserCoupon(json.toString());
            String message = "";
            message = stringCall.execute().body();
            return true;
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        if(aBoolean){
            Toast toast = Toast.makeText(context, "You Bought the coupon", Toast.LENGTH_LONG);
            toast.show();
            MCouponListFragment mCouponListFragment = new MCouponListFragment();
           ((MainActivity)context).replaceFragment(mCouponListFragment);

        } else {
            Toast toast = Toast.makeText(context, "An error has occurred. The Coupon could be bought.",  Toast.LENGTH_LONG);
            toast.show();
        }

    }


}
