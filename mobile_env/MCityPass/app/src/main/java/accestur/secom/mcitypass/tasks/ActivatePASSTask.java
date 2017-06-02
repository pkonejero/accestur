package accestur.secom.mcitypass.tasks;

import android.os.AsyncTask;

import java.io.IOException;

import accestur.secom.core.api.IssuerAPI;
import accestur.secom.core.service.impl.UserService;
import accestur.secom.core.utils.Constants;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by Sebastià on 2/6/2017.
 */

public class ActivatePASSTask extends AsyncTask<Long, Void, Void> {

    UserService userService = new UserService();

    @Override
    protected Void doInBackground(Long... params) {

        IssuerAPI issuerAPI = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build()
                .create(IssuerAPI.class);

        Call<String> stringCall = issuerAPI.verifyTicket(userService.showPass(params[0]));
        String message = "";

        try {
            message = stringCall.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        userService.getVerifyTicketConfirmation(message);

        return null;
    }
}
