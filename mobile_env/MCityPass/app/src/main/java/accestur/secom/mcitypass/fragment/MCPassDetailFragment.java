package accestur.secom.mcitypass.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import accestur.secom.core.model.MCityPass;
import accestur.secom.core.service.impl.ActivationService;
import accestur.secom.core.service.impl.MCityPassService;
import accestur.secom.core.service.impl.ServiceAgentService;
import accestur.secom.mcitypass.R;
import accestur.secom.mcitypass.content.ServicesAdapter;
import accestur.secom.mcitypass.tasks.ActivatePASSTask;
import accestur.secom.mcitypass.tasks.InfiniteReusableTask;
import accestur.secom.mcitypass.tasks.MTimesReusableTask;
import accestur.secom.mcitypass.tasks.NonReusableTask;

public class MCPassDetailFragment extends Fragment {

    private TextView txtDetail;
    private String text;
    int currentMPASS;

    public MCPassDetailFragment() {}

    public void setCurrentMPASS (int i ){
        currentMPASS = i;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mcpassdetail, container, false);
    }

    public void mostrarDetalle(String texto) {
        text = texto;
    }

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);
       // txtDetail = (TextView)getActivity().findViewById(R.id.textDetail);
       // txtDetail.setText(text);

        final MCityPassService mCityPassService = new MCityPassService();
        mCityPassService.initMCityPass(currentMPASS+1);

        TextView lifetimeText = (TextView) getActivity().findViewById(R.id.textLifetime);
        int lifetime = Integer.parseInt(mCityPassService.getMCityPass().getLifeTime())/(3600*1000*24);
        lifetimeText.setText("" +lifetime);

        TextView categoryText = (TextView) getActivity().findViewById(R.id.textCategory);
        categoryText.setText(mCityPassService.getMCityPass().getCategory());

        TextView expDateText = (TextView) getActivity().findViewById(R.id.textExpDate);
        expDateText.setText(mCityPassService.getMCityPass().getExpDate());

        final ActivationService activationService = new ActivationService();
        activationService.initActivation(mCityPassService.getMCityPass());

        Button activateButton = (Button) getActivity().findViewById(R.id.activatePASSButton);
        if (activationService.getActivation()==null){
            activateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ActivatePASSTask activatePASSTask = new ActivatePASSTask();
                    activatePASSTask.execute(mCityPassService.getMCityPass().getsN());
                }
            });
        } else {
            activateButton.setVisibility(View.INVISIBLE);
        }

        ListView listView = (ListView) getActivity().findViewById(R.id.listServicesUse);
        final ServiceAgentService serviceAgentService = new ServiceAgentService();
        ServicesAdapter servicesAdapter = new ServicesAdapter(getActivity(), (ArrayList) serviceAgentService.getServices());
        listView.setAdapter(servicesAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> list, View view, int pos, long id) {
                if(activationService.getActivation()==null){
               if(serviceAgentService.getServices().get(pos).getM()==1){
                   NonReusableTask nonReusableTask = new NonReusableTask();
                   nonReusableTask.execute(mCityPassService.getMCityPass().getId() , serviceAgentService.getServiceAgent().getId());
               } else if(serviceAgentService.getServices().get(pos).getM() == -1){
                   InfiniteReusableTask infiniteReusableTask = new InfiniteReusableTask();
                   infiniteReusableTask.execute(mCityPassService.getMCityPass().getId() , serviceAgentService.getServiceAgent().getId());
               } else {
                   MTimesReusableTask mTimesReusableTask = new MTimesReusableTask();
                   mTimesReusableTask.execute(mCityPassService.getMCityPass().getId() , serviceAgentService.getServiceAgent().getId());
               }
                } else {
                    Toast toast = Toast.makeText(getActivity(),  "Activate the PASS first", Toast.LENGTH_LONG);
                    toast.show();
                }

            }
        });

    }
}

//    @Override
//    public void onViewCreated(View view, Bundle savedInstanceState) {
//       int counter=0;
//       NonReusableTask nonReusableTask = new NonReusableTask();
//       nonReusableTask.execute();
//
//       MTimesReusableTask mTimesReusableTask = new MTimesReusableTask();
//       mTimesReusableTask.execute(counter);
//
//       InfiniteReusableTask infiniteReusableTask = new InfiniteReusableTask();
//       infiniteReusableTask.execute();
//    }