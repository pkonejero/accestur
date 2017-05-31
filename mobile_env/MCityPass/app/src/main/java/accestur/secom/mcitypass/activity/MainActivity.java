package accestur.secom.mcitypass.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import accestur.secom.mcitypass.R;
import accestur.secom.mcitypass.fragment.MCPassAddFragment;
import accestur.secom.mcitypass.fragment.MCPassListFragment;
import accestur.secom.mcitypass.fragment.SettingsFragment;
import accestur.secom.core.service.impl.UserService;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    public static UserService userService;

    BottomNavigationView bottomNavigationView;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        setInitialFragment();
    }

    @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()) {
            case R.id.action_device:
                fragment = new MCPassListFragment();
                break;
            case R.id.action_cloud:
                fragment = new MCPassAddFragment();
                break;
            case R.id.action_settings:
                fragment = new SettingsFragment();
                break;
        }
        replaceFragment(fragment);
        return true;
    }

    private void setInitialFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.main_fragment_placeholder, new MCPassListFragment());
        fragmentTransaction.commit();
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_fragment_placeholder, fragment);
        fragmentTransaction.commit();
    }

}