package accestur.secom.mcitypass;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;

import accestur.secom.core.crypto.Cryptography;
import accestur.secom.core.crypto.Schnorr;
import accestur.secom.core.test.Greeting;

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

                    String path1 = Environment.getExternalStorageDirectory().toString() + "/DCIM/cert/private_USER.der";
                    String path2 = Environment.getExternalStorageDirectory().toString() + "/DCIM/cert/public_USER.der";

                    Cryptography crypto = new Cryptography();
                    crypto.initPrivateKey(path1);
                    crypto.initPublicKey(path2);

                    Log.d("Crypt : ",crypto.encryptWithPublicKey(path1));
                    Log.d("DeCrypt : ",crypto.decryptWithPrivateKey(crypto.encryptWithPublicKey(path1)));

                    Log.d("Sign : ",crypto.getSignature(path1));
                    Log.d("Verify : ",String.valueOf(crypto.getValidation(path1,crypto.getSignature(path1))));

                    return true;
                case R.id.navigation_dashboard:
                    Schnorr schnorr = new Schnorr();
                    schnorr.Init();
                    mTextMessage1.setText(schnorr.SecretKey().toString());
                    mTextMessage2.setText(schnorr.PublicKey().toString());
                    return true;
                case R.id.navigation_notifications:
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
            mTextMessage3 = (TextView) findViewById(R.id.content_value);
            mTextMessage3.setText(greeting.getContent());
        }
    }
}
