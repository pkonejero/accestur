package accestur.secom.mcitypass.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.IOException;

import accestur.secom.core.api.ProviderAPI;
import accestur.secom.core.service.impl.UserService;
import accestur.secom.mcitypass.activity.MainActivity;
import accestur.secom.mcitypass.fragment.MCPassListFragment;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static accestur.secom.mcitypass.activity.MainActivity.userService;

public class InfiniteReusableTask extends AsyncTask<Long, Void, Boolean> {
    public static final String BASE_URL = "http://192.168.1.33:8080/";
    private String message;
    Context context;

    public InfiniteReusableTask(Context context) {
        this.context = context;
    }

    @Override
    protected Boolean doInBackground(Long... params) {
        userService = new UserService();
        userService.loadUser(1);
        //userService.initUser();
        ProviderAPI providerAPI = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build()
                .create(ProviderAPI.class);

        Call<String> stringCall = providerAPI.verifyInfPass(userService.showInfinitePass(params[0], params[1]));

        try {
            message = stringCall.execute().body();
            System.out.println(message);
        } catch (IOException e) {
            e.printStackTrace();
        }

        stringCall = providerAPI.verifyInfPass2(userService.solveInfiniteVerifyChallenge(message));
        try {
            message = stringCall.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        stringCall = providerAPI.verifyInfProof(userService.showInfiniteProof(message));
        try {
            message = stringCall.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }

       return userService.getInfiniteValidationConfirmation(message);

    }
    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        if(aBoolean){
            Toast toast = Toast.makeText(context, "You can access this service", Toast.LENGTH_LONG);
            toast.show();
            MCPassListFragment mcPassListFragment = new MCPassListFragment();
            ((MainActivity)context).replaceFragment(mcPassListFragment);
        } else {
            Toast toast = Toast.makeText(context, "An error has occurred. You can't access the service",  Toast.LENGTH_LONG);
            toast.show();
        }

    }
}
