package accestur.secom.mcitypass;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;

import accestur.secom.core.crypto.Cryptography;
import accestur.secom.core.crypto.Schnorr;
import accestur.secom.core.test.Greeting;
import accestur.secom.core.utils.Constants;

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
                    Cryptography crypto = new Cryptography();
                    crypto.initPrivateKey(Constants.PATH_PRIVATE_KEY);
                    crypto.initPublicKey(Constants.PATH_PUBLIC_KEY);

                    // METRICS
                    try {
                        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
                        SecureRandom random = new SecureRandom();
                        byte bytes[] = new byte[512];
                        random.nextBytes(bytes);
                        keyGen.initialize(2046,random);
                        KeyPair pair = keyGen.generateKeyPair();
                        PrivateKey priv = pair.getPrivate();
                        PublicKey pub = pair.getPublic();
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }

                    Log.d("Crypt : ",crypto.encryptWithPublicKey("testing"));
                    Log.d("DeCrypt : ",crypto.decryptWithPrivateKey(crypto.encryptWithPublicKey("testing")));
                    Log.d("Sign : ",crypto.getSignature("testing"));
                    Log.d("Verify : ",String.valueOf(crypto.getValidation("testing",crypto.getSignature("testing"))));

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
