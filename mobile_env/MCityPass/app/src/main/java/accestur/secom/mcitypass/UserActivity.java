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

import accestur.secom.core.service.impl.UserService;
import accestur.secom.mcitypass.tasks.InfiniteReusableTask;
import accestur.secom.mcitypass.tasks.MTimesReusableTask;
import accestur.secom.mcitypass.tasks.NonReusableTask;

import accestur.secom.core.crypto.Schnorr;
import accestur.secom.core.test.Greeting;

public class UserActivity extends AppCompatActivity {
    private TextView mTextMessage1;
    private TextView mTextMessage2;
    private TextView mTextMessage3;

    public static UserService userService;

    private int counter;


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
                    MTimesReusableTask mTimesReusableTask = new MTimesReusableTask();
                    mTimesReusableTask.execute(counter);
                    counter++;
                    return true;
                case R.id.navigation_notifications:
                    cleanLayout();
                    InfiniteReusableTask infiniteReusableTask = new InfiniteReusableTask();
                    infiniteReusableTask.execute();
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
        counter = 0;
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
