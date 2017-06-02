package accestur.secom.core;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.app.Application;

import accestur.secom.core.model.Counter;
import accestur.secom.core.model.SecretValue;
import accestur.secom.core.model.ServiceAgent;
import accestur.secom.core.service.impl.CounterService;
import accestur.secom.core.service.impl.MCityPassService;
import accestur.secom.core.service.impl.ServiceAgentService;
import accestur.secom.mcitypass.tasks.ActivatePASSTask;
import accestur.secom.mcitypass.tasks.GeneratePseudonymTask;
import accestur.secom.mcitypass.tasks.InfiniteReusableTask;
import accestur.secom.mcitypass.tasks.MTimesReusableTask;
import accestur.secom.mcitypass.tasks.NonReusableTask;
import accestur.secom.mcitypass.tasks.PurchasePASSTask;
import accestur.secom.mcitypass.tasks.ServicesTask;

/**
 * Created by Sebastià on 31/5/2017.
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

        ServicesTask servicesTask = new ServicesTask();
        servicesTask.execute();

        //     GeneratePseudonymTask generatePseudonymTask = new GeneratePseudonymTask();
        //     generatePseudonymTask.execute();

        //   PurchasePASSTask purchasePASSTask = new PurchasePASSTask();//
        //   purchasePASSTask.execute();


        //ActivatePASSTask activatePASSTask = new ActivatePASSTask();
        //long sn = 2;
        //activatePASSTask.execute(sn);


          NonReusableTask nonReusableTask = new NonReusableTask();
         nonReusableTask.execute();

         MTimesReusableTask mTimesReusableTask = new MTimesReusableTask();
         mTimesReusableTask.execute();

         InfiniteReusableTask infiniteReusableTask = new InfiniteReusableTask();
         infiniteReusableTask.execute();
    }
}
