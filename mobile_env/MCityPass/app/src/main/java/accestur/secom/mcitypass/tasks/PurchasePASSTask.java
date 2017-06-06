package accestur.secom.mcitypass.tasks;

import android.os.AsyncTask;

import java.io.IOException;
import java.util.List;

import accestur.secom.core.api.IssuerAPI;
import accestur.secom.core.model.ServiceAgent;
import accestur.secom.core.service.impl.ServiceAgentService;
import accestur.secom.core.service.impl.UserService;
import accestur.secom.core.utils.Constants;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by Sebastià on 1/6/2017.
 */

public class PurchasePASSTask extends AsyncTask<String, Void, Void> {

    ServiceAgentService serviceAgentService = new ServiceAgentService();
    UserService userService = new UserService();

    @Override
    protected Void doInBackground(String... params) {

        IssuerAPI issuerAPI = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build()
                .create(IssuerAPI.class);

        Call<String>  stringCall = issuerAPI.getChallenge(userService.getService(params[0], params[1], params[2]));

        String message  = "";

        try {
            message = stringCall.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<ServiceAgent> servicesList = serviceAgentService.getServices();
        String[] services = new String[servicesList.size()];

        for(int i = 0; i < servicesList.size(); i++){
            services[i] = servicesList.get(i).getName();
        }

        stringCall = issuerAPI.getPASS(userService.solveChallenge(message,services));

        try{
            message = stringCall.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        userService.receivePass(message);

        /*
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

         */




        return null;
    }
}
