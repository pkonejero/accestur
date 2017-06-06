package accestur.secom.mcitypass.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import accestur.secom.mcitypass.R;
import accestur.secom.mcitypass.content.MCPassItem;
import accestur.secom.mcitypass.fragment.MCPassAddFragment;
import accestur.secom.mcitypass.fragment.MCPassDetailFragment;
import accestur.secom.mcitypass.fragment.MCPassListFragment;
import accestur.secom.mcitypass.fragment.SettingsFragment;
import accestur.secom.core.service.impl.UserService;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, MCPassListFragment.MCPassListener{

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
                MCPassListFragment mcPassListFragment = new MCPassListFragment();
                mcPassListFragment.setMCPassListListener(this);
                replaceFragment(mcPassListFragment);
                break;
            case R.id.action_cloud:
                fragment = new MCPassAddFragment();
                replaceFragment(fragment);
                break;
            case R.id.action_settings:
                fragment = new SettingsFragment();
                replaceFragment(fragment);
                break;
        }
        return true;
    }

    @Override
    public void onMCPassItemSeleccionado(MCPassItem c) {
        MCPassDetailFragment fragment = new MCPassDetailFragment();
        fragment.mostrarDetalle(c.getTexto());
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_fragment_placeholder, fragment);
        fragmentTransaction.commit();
    }


    private void setInitialFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        MCPassListFragment mcPassListFragment = new MCPassListFragment();
        fragmentTransaction.add(R.id.main_fragment_placeholder, mcPassListFragment);
        fragmentTransaction.commit();
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_fragment_placeholder, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        View view = bottomNavigationView.findViewById(R.id.action_device);
        view.performClick();
        MCPassListFragment mcPassListFragment = new MCPassListFragment();
       // mcPassListFragment.setMCPassListListener(this);
        replaceFragment(mcPassListFragment);
    }

}