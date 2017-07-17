package secom.accestur.mcoupon.activity.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import secom.accestur.mcoupon.activity.fragment.MCouponListFragment;


import secom.accestur.core.service.impl.UserMCouponService;


/**
 * Created by gornals on 13/07/2017.
 */

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

public static UserMCouponService userService;

    BottomNavigationView bottomNavigationView;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        setInitialFragment();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    public void setInitialFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        MCouponListFragment mCouponListFragment = new MCouponListFragment();
        fragmentTransaction.add(R.id.main_fragment_placeholder, mCouponListFragment);
        fragmentTransaction.commit();
    }
}
