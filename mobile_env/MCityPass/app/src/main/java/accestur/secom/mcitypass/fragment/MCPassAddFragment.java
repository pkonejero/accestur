package accestur.secom.mcitypass.fragment;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import accestur.secom.core.service.impl.ServiceAgentService;
import accestur.secom.core.service.impl.UserService;
import accestur.secom.mcitypass.R;
import accestur.secom.mcitypass.content.ServicesAdapter;
import accestur.secom.mcitypass.tasks.PurchasePASSTask;

public class MCPassAddFragment extends Fragment {

    private TextView txtCategory;
    private Button setDate;
    private Button purchasePass;
    private Spinner setLifetime;
    private Spinner setCategory;
    private ListView servicesList;


    private String Category;
    private String Lifetime;
    private String ExpDate;

    Calendar c = Calendar.getInstance();
    int mYear = c.get(Calendar.YEAR);
    int mMonth = c.get(Calendar.MONTH);
    int mDay = c.get(Calendar.DAY_OF_MONTH);

    private ServiceAgentService serviceAgentService;
    private UserService userService;

    public MCPassAddFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mcpassadd, container, false);

    }

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);
        // txtCategory = (TextView)getActivity().findViewById(R.id.textCategory);
        servicesList = (ListView) getActivity().findViewById(R.id.listServices);
        serviceAgentService = new ServiceAgentService();
        ServicesAdapter servicesAdapter = new ServicesAdapter(getActivity(), (ArrayList) serviceAgentService.getServices());
        servicesList.setAdapter(servicesAdapter);
        setDate = (Button) getActivity().findViewById(R.id.buttonExpAate);
        setDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, final int year, final int month, final int dayOfMonth) {
                        Log.v("Dat e", "year:" + year + " month:" + month + " dayOfMonth:" + dayOfMonth);
                        //((Button)findViewById(R.id.day1)).setText(year+"-"+month+"-"+dayOfMonth);
                        ExpDate = dayOfMonth + "/" + (month+1) + "/" + year;
                        setDate.setText(ExpDate);
                        // time.show();
                    }
                }, mYear, mMonth, mDay);
                dialog.show();
            }
        });

        setLifetime = (Spinner) getActivity().findViewById(R.id.spinnerLifetime);
        setCategory = (Spinner) getActivity().findViewById(R.id.spinnerCategory);


        purchasePass = (Button) getActivity().findViewById(R.id.purchasePASSButton);
        purchasePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userService = new UserService();
                userService.loadUser(1);
                if(userService.getUser()==null){
                    Toast toast = Toast.makeText(getActivity(), "Generate a Pseudonym first", Toast.LENGTH_LONG);
                    toast.show();
                } else {
                    PurchasePASSTask purchasePASSTask = new PurchasePASSTask(getActivity());
                    Category = setCategory.getSelectedItem().toString();
                    Lifetime = setLifetime.getSelectedItem().toString();
                    System.out.println("Category: " + Category);
                    System.out.println("ExpDate: " + ExpDate);
                    System.out.println("Lifetime: " + Lifetime);
                    if(ExpDate!=null){
                        purchasePASSTask.execute(Category, ExpDate, Lifetime);
                    } else {
                        Toast toast = Toast.makeText(getActivity(), "Select an Expiring Date", Toast.LENGTH_LONG);
                        toast.show();
                    }
                }


            }
        });

    }
}