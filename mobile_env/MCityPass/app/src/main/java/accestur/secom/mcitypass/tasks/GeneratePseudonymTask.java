package accestur.secom.mcitypass.tasks;

import android.os.AsyncTask;

import java.io.IOException;

import accestur.secom.core.api.TTPAPI;
import accestur.secom.core.model.User;
import accestur.secom.core.service.impl.UserService;
import accestur.secom.core.utils.Constants;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class GeneratePseudonymTask extends AsyncTask<String, Void, Void> {

    UserService userService = new UserService();
    @Override
    protected Void doInBackground(String... params) {
        TTPAPI ttpapi = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build()
                .create(TTPAPI.class);

        Call<String> stringCall = ttpapi.generatePseudonym(userService.authenticateUser());
        String message = "";
        try {
            message = stringCall.execute().body();
            System.out.println(message);
        } catch (IOException e) {
            e.printStackTrace();
        }

        userService.verifyPseudonym(message);

        userService.loadUser(1);

        //serviceAgent = new Select().from(ServiceAgent.class).where("name = ?", name).executeSingle();



        return null;
    }
}
