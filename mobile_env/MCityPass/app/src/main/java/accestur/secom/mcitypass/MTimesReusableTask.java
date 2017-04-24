package accestur.secom.mcitypass;

import android.os.AsyncTask;

import java.io.IOException;

import accestur.secom.core.api.ProviderAPI;
import accestur.secom.core.service.impl.UserService;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by Sebastià on 24/4/2017.
 */

public class MTimesReusableTask extends AsyncTask<String, Void, Void> {
    public static final String BASE_URL = "http://192.168.1.33:8080/";
    private String message;
    @Override
    protected Void doInBackground(String... params) {
        UserService userService = new UserService();
        //userService.initUser();
        ProviderAPI providerAPI = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build()
                .create(ProviderAPI.class);

        Call<String> stringCall = providerAPI.verifyPass(userService.showTicket(1, 2));

        try {
            message = stringCall.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        stringCall = providerAPI.verifyPass2(userService.solveVerifyChallenge(message));
        try {
            message = stringCall.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        stringCall = providerAPI.verifyProof(userService.showProof(message));
        try {
            message = stringCall.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(userService.getValidationConfirmation(message));
        return null;
    }
}
