package secom.accestur.mcoupon.activity.tasks;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.HttpsURLConnection;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import secom.accestur.core.api.IssuerAPI;
import secom.accestur.core.api.ManufacturerAPI;
import secom.accestur.core.model.UserMCoupon;
import secom.accestur.core.service.impl.MCouponService;
import secom.accestur.core.service.impl.UserMCouponService;
import secom.accestur.core.utils.Constants;
import secom.accestur.mcoupon.activity.activity.MainActivity;

public class LogInTask extends AsyncTask<String, Void, Boolean> {

    Context context;
    UserMCouponService userMCouponService = new UserMCouponService();
    UserMCoupon user = new UserMCoupon();
    MCouponService mCouponService = new MCouponService();
    String userConnected;

    public LogInTask(Context context) {
        this.context = context;
    }

    @Override
    protected Boolean doInBackground(String... params) {

            ManufacturerAPI manufacturerAPI = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .build()
                    .create(ManufacturerAPI.class);

            userConnected = params[0];
            String username = params[0];
            String password = params[1];

        String message = "";

        try {
            String request = userMCouponService.authenticateUsername(username,password);
            Call<String> stringCall = manufacturerAPI.logIn(request);
            message = stringCall.execute().body();


        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        boolean response = false;
        if(message==null){
            return response;
        }else{
            if(message.equals("OKAY")){
                response=true;
                return response;
            }
            return response;
        }
    }

    @Override
    protected void onPostExecute(Boolean response) {
       if (response==true) {
           Toast toast = Toast.makeText(context, "Log In Correct", Toast.LENGTH_SHORT);
           toast.show();
           UserMCouponService.setUserConnected(userConnected);
           Intent i = new Intent(context, MainActivity.class);
           context.startActivity(i);
       }else{
           Toast toast = Toast.makeText(context, "Incorrect password or username", Toast.LENGTH_SHORT);
           toast.show();
       }
    }
}
