package secom.accestur.mcoupon.activity.tasks;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import secom.accestur.core.api.ManufacturerAPI;
import secom.accestur.core.model.UserMCoupon;
import secom.accestur.core.service.impl.MCouponService;
import secom.accestur.core.service.impl.UserMCouponService;
import secom.accestur.core.utils.Constants;
import secom.accestur.mcoupon.activity.activity.LogInActivity;
import secom.accestur.mcoupon.activity.activity.MainActivity;

public class SignUpTask extends AsyncTask<String, Void, Boolean> {

    Context context;
    UserMCouponService userMCouponService = new UserMCouponService();
    UserMCoupon user = new UserMCoupon();
    MCouponService mCouponService = new MCouponService();


    public SignUpTask(Context context) {
        this.context = context;
    }

    @Override
    protected Boolean doInBackground(String... params) {

            ManufacturerAPI manufacturerAPI = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .build()
                    .create(ManufacturerAPI.class);

            String username = params[0];
            String password = params[1];

        String message = "";

        try {
            String request = userMCouponService.authenticateUsername(username,password);
            Call<String> stringCall = manufacturerAPI.getChallengeRegister(request);
            message = stringCall.execute().body();


        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        boolean response = false;
        if(message.equals("OKAY")){
            response = true;
            UserMCouponService userMCouponService = new UserMCouponService();

            UserMCoupon userRegistered= new UserMCoupon();
            userRegistered.setUsername(username);
            userRegistered.setPassword(password);
            userMCouponService.storeUserMCoupon(userRegistered);
            return response;
        }else{
            return response;
        }
    }

    @Override
    protected void onPostExecute(Boolean response) {
       if (response==true) {

           Toast toast = Toast.makeText(context, "Register Correct", Toast.LENGTH_SHORT);
           toast.show();
           Intent i = new Intent(context, LogInActivity.class);
           context.startActivity(i);
       }else{
           Toast toast = Toast.makeText(context, "Already Registered", Toast.LENGTH_SHORT);
           toast.show();
       }
    }
}
