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

import secom.accestur.core.model.MCoupon;
import secom.accestur.core.model.UserMCoupon;
import secom.accestur.core.service.impl.MCouponService;
import secom.accestur.mcoupon.activity.Queries.EventListQuery;
import secom.accestur.mcoupon.activity.fragment.MCouponListFragment;
import secom.accestur.mcoupon.activity.R;


import secom.accestur.core.service.impl.UserMCouponService;
import secom.accestur.mcoupon.activity.tasks.OffersTask;


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
//        Fragment fragment = null;
//        switch (item.getItemId()) {
//            case R.id.action_device:
//                MCouponListFragment mcPassListFragment = new MCouponListFragment();
//                //mcPassListFragment.setMCPassListListener(this);
//                replaceFragment(mcPassListFragment);
//                break;
//            case R.id.action_cloud:
//                fragment = new MCPassAddFragment();
//                replaceFragment(fragment);
//                break;
//            case R.id.action_settings:
//                fragment = new SettingsFragment();
//                replaceFragment(fragment);
//                break;
//        }
        return false;
    }

    public void setInitialFragment() {
//        MCouponService mCouponService = new MCouponService();
//
//        MCoupon mCoupon = new MCoupon();
//        mCoupon.setId((long) 1);
//        mCoupon.setP(2);
//        mCoupon.setQ(4);
//        mCouponService.storeMCoupon(mCoupon);
//        OffersTask offersTask = new OffersTask();
//        offersTask.execute();

        UserMCouponService userMCouponService = new UserMCouponService();

        UserMCoupon userRegistered= new UserMCoupon();
        userRegistered.setUsername("Toni");
        userMCouponService.storeUserMCoupon(userRegistered);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        MCouponListFragment mCouponListFragment = new MCouponListFragment();
        fragmentTransaction.add(R.id.main_fragment_placeholder, mCouponListFragment);
        fragmentTransaction.commit();
    }

    public void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_fragment_placeholder, fragment);
        fragmentTransaction.commit();

    }
}
