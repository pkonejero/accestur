package accestur.secom.mcitypass.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import accestur.secom.mcitypass.R;
import accestur.secom.mcitypass.tasks.InfiniteReusableTask;
import accestur.secom.mcitypass.tasks.MTimesReusableTask;
import accestur.secom.mcitypass.tasks.NonReusableTask;

public class MCPassFragment extends Fragment {

    public MCPassFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mcpass, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
       int counter=0;
       NonReusableTask nonReusableTask = new NonReusableTask();
       nonReusableTask.execute();

       MTimesReusableTask mTimesReusableTask = new MTimesReusableTask();
       mTimesReusableTask.execute(counter);

       InfiniteReusableTask infiniteReusableTask = new InfiniteReusableTask();
       infiniteReusableTask.execute();
    }

}