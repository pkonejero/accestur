package accestur.secom.mcitypass.tasks;

import android.os.AsyncTask;

import java.io.IOException;

import accestur.secom.core.api.ProviderAPI;
import accestur.secom.core.service.impl.UserService;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static accestur.secom.mcitypass.activity.MainActivity.userService;

/**
 * Created by Sebastià on 24/4/2017.
 */

public class MTimesReusableTask extends AsyncTask<Long, Void, Void> {
    public static final String BASE_URL = "http://192.168.1.33:8080/";
    private String message;

    @Override
    protected Void doInBackground(Long... params) {


        userService = new UserService();
        userService.loadUser(1);
        userService.initValues(params[0], params[1]);


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
