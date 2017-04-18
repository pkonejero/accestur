package accestur.secom.mcitypass;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import accestur.secom.core.crypto.Schnorr;

public class UserActivity extends AppCompatActivity {

    private TextView mTextMessage1;
    private TextView mTextMessage2;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage1.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    Schnorr schnorr = new Schnorr();
                    schnorr.Init();
                    mTextMessage1.setText(schnorr.SecretKey().toString());
                    mTextMessage2.setText(schnorr.PublicKey().toString());
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage1.setText(R.string.title_notifications);
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

}
