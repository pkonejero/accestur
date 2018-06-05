package secom.accestur.core;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Configuration;
import com.activeandroid.app.Application;

import secom.accestur.core.model.CounterMCoupon;
import secom.accestur.core.model.MCoupon;
import secom.accestur.core.model.UserMCoupon;
import secom.accestur.core.service.impl.CounterMCouponService;
import secom.accestur.mcoupon.activity.tasks.OffersTask;

/**
 * Created by gornals on 13/07/2017.
 */

public class GlobalApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Configuration dbConfiguration = new Configuration.Builder(this)
                .setDatabaseName("MCoupon.db")
                .addModelClass(UserMCoupon.class)
                .addModelClass(MCoupon.class)
                .addModelClass(CounterMCoupon.class)
                .create();
        ActiveAndroid.initialize(dbConfiguration);

        /*
        ServiceAgentService serviceAgentService = new ServiceAgentService();
        serviceAgentService.initService(1, true);

        serviceAgentService.saveServiceAgent();
        serviceAgentService.loadServiceAgent("InfiniteReusable");


        System.out.println("ServiceAgent: " + serviceAgentService.getServiceAgent().getName());

        */
//        System.out.println("PROVA");
//        CounterMCouponService counterMCouponService = new CounterMCouponService();
//        counterMCouponService.init(5);




      //  OffersTask offersTask = new OffersTask();
      //  offersTask.execute();


        //   PurchasePASSTask purchasePASSTask = new PurchasePASSTask();//
        //   purchasePASSTask.execute();


        //ActivatePASSTask activatePASSTask = new ActivatePASSTask();
        //long sn = 2;
        //activatePASSTask.execute(sn);


        //    NonReusableTask nonReusableTask = new NonReusableTask();
        //  nonReusableTask.execute();

        // MTimesReusableTask mTimesReusableTask = new MTimesReusableTask();
        // mTimesReusableTask.execute();

        // InfiniteReusableTask infiniteReusableTask = new InfiniteReusableTask();
        // infiniteReusableTask.execute();
    }
}
