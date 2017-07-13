package secom.accestur.core;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.app.Application;

/**
 * Created by gornals on 13/07/2017.
 */

public class GlobalApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);

        /*
        ServiceAgentService serviceAgentService = new ServiceAgentService();
        serviceAgentService.initService(1, true);

        serviceAgentService.saveServiceAgent();
        serviceAgentService.loadServiceAgent("InfiniteReusable");


        System.out.println("ServiceAgent: " + serviceAgentService.getServiceAgent().getName());

        */

        //ServicesTask servicesTask = new ServicesTask();
        //servicesTask.execute();


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
