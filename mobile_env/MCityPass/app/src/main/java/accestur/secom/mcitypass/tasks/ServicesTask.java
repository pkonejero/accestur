package accestur.secom.mcitypass.tasks;

import android.os.AsyncTask;

import com.activeandroid.query.Select;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import accestur.secom.core.api.ProviderAPI;
import accestur.secom.core.api.ServicesAPI;
import accestur.secom.core.model.ServiceAgent;
import accestur.secom.core.service.impl.ServiceAgentService;
import accestur.secom.core.utils.Constants;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.Body;

//import static accestur.secom.mcitypass.UserActivity.userService;

/**
 * Created by Sebastià on 1/6/2017.
 */

public class ServicesTask  extends AsyncTask<Long, Void, Void> {

    ServiceAgentService serviceAgentService = new ServiceAgentService();

    @Override
    protected Void doInBackground(Long... params) {
        ServicesAPI serciceAPI = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build()
                .create(ServicesAPI.class);


        Call<String> stringCall = serciceAPI.getServices();

        String message = "";
        try{
            message = stringCall.execute().body();
            System.out.println(message);
        } catch (IOException e) {
            e.printStackTrace();
        }

        storeServices(message);

        return null;
    }


    private void storeServices(String message){
        try {
            JSONArray jsonArray = new JSONArray(message);
            JSONObject json;
            ServiceAgent serviceAgent;
            for(int i = 0; i < jsonArray.length(); i++){
                json = jsonArray.getJSONObject(i);
                serviceAgent = new Select().from(ServiceAgent.class).where("name = ? ", json.getString("name")).executeSingle();
                if(serviceAgent == null) {
                    serviceAgent = new ServiceAgent();
                    serviceAgent.setName(json.getString("name"));
                    serviceAgent.setM(json.getInt("m"));
                    serviceAgent.setProvider(json.getString("provider"));
                    serviceAgent.setIndexHash("" + json.getLong("id"));
                } else {
                    System.out.println("Service " + serviceAgent.getName() + " already exists");
                }


                serviceAgentService.storeServiceAgent(serviceAgent);
            }



        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    /*
    @Override
    protected void onPostExecute(Void aVoid) {
       ///serviceAgentService = new ServiceAgentService();
        //serviceAgentService.initService(1, true);

        //serviceAgentService.saveServiceAgent();
       // serviceAgentService.loadServiceAgent("TenTimesReusable");

        //System.out.println("ServiceAgent Name: " + serviceAgentService.getServiceAgent().getName());
    }
    */
}


