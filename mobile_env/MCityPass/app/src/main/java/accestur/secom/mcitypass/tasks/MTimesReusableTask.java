package accestur.secom.mcitypass.tasks;

import android.os.AsyncTask;

import java.io.IOException;

import accestur.secom.core.api.ProviderAPI;
import accestur.secom.core.service.impl.UserService;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static accestur.secom.mcitypass.UserActivity.userService;

/**
 * Created by Sebastià on 24/4/2017.
 */

public class MTimesReusableTask extends AsyncTask<Integer, Void, Void> {
    public static final String BASE_URL = "http://192.168.1.33:8080/";
    private String message;

    @Override
    protected Void doInBackground(Integer ... params) {

        if(params[0]==0){
            userService = new UserService();
            userService.initUser();
            userService.initValues(1, 4);
        }

        //userService.initUser();
        ProviderAPI providerAPI = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build()
                .create(ProviderAPI.class);

        Call<String> stringCall = providerAPI.verifyMPass(userService.showMTicket());

        try {
            message = stringCall.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        stringCall = providerAPI.verifyMPass2(userService.solveMVerifyChallenge(message));
        try {
            message = stringCall.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        stringCall = providerAPI.verifyMProof(userService.showMProof(message));
        try {
            message = stringCall.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(userService.getVerifyMTicketConfirmation(message));
        return null;
    }


}
