package accestur.secom.mcitypass;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import accestur.secom.core.service.impl.UserService;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;

import accestur.secom.core.api.ProviderAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import accestur.secom.core.crypto.Cryptography;
import accestur.secom.core.crypto.Schnorr;
import accestur.secom.core.test.Greeting;
import accestur.secom.core.utils.Constants;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class UserActivity extends AppCompatActivity {
    private TextView mTextMessage1;
    private TextView mTextMessage2;
    private TextView mTextMessage3;



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    cleanLayout();
                    NonReusableTask nonReusableTask = new NonReusableTask();
                    nonReusableTask.execute();


                    return true;
                case R.id.navigation_dashboard:
                    cleanLayout();
                    Schnorr schnorr = new Schnorr();
                    schnorr.Init();
                    mTextMessage1.setText(schnorr.SecretKey().toString());
                    mTextMessage2.setText(schnorr.PublicKey().toString());
                    return true;
                case R.id.navigation_notifications:
                    cleanLayout();
                    new HttpRequestTask().execute();
                    return true;
            }
            return false;


        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        mTextMessage1 = (TextView) findViewById(R.id.message1);
        mTextMessage2 = (TextView) findViewById(R.id.message2);
        mTextMessage3 = (TextView) findViewById(R.id.content_value);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private class HttpRequestTask extends AsyncTask<Void, Void, Greeting> {
        @Override
        public Greeting doInBackground(Void... params) {
            try {
                final String url = "http://rest-service.guides.spring.io/greeting";
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                Greeting greeting = restTemplate.getForObject(url, Greeting.class);
                return greeting;
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }
            return null;
        }

        @Override
        public void onPostExecute(Greeting greeting) {
            mTextMessage3.setText(greeting.getContent());
        }
    }

    private void cleanLayout() {
        mTextMessage1.setText("");
        mTextMessage2.setText("");
        mTextMessage3.setText("");
    }
}
