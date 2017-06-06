package accestur.secom.mcitypass.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

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

public class ActivatePASSTask extends AsyncTask<Long, Void, Boolean> {

    Context context;
    UserService userService = new UserService();

    public ActivatePASSTask(Context context) {
        this.context = context;
    }

    @Override
    protected Boolean doInBackground(Long... params) {

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

        return     userService.getVerifyTicketConfirmation(message);
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        if(aBoolean){
            Toast toast = Toast.makeText(context, "The PASS has been activated", Toast.LENGTH_LONG);
            toast.show();

        } else {
            Toast toast = Toast.makeText(context, "An error has occurred. The PASS could be activated.",  Toast.LENGTH_LONG);
            toast.show();
        }

    }


}
