package secom.accestur.mcoupon.activity.Queries;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

//import com.example.sebasti.basic.Activities.AnswerEventActivity;
//import com.example.sebasti.basic.Activities.BasicActivity;
//import com.example.sebasti.basic.Adapters.EventListAdapter;
//import com.example.sebasti.basic.Classes.Event;
//import com.example.sebasti.basic.Classes.Person;
//import com.example.sebasti.basic.Classes.Resource;
//import com.example.sebasti.basic.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Sebastià on 4/12/2016.
 */

public class EventListQuery extends AsyncTask<String, Void, String> {
    private final String queryUrl = "manufacturer/getParamsCoupon/";

    private Context context;
    private boolean showScreen;

    public EventListQuery(Context context, boolean showScreen) {
        this.context = context;
        this.showScreen = showScreen;
    }

    @Override
    protected String doInBackground(String... params) {
        return QueryUtils.doGet(queryUrl);
    }


//    @Override
//    protected void onPostExecute(String json) {
//        BasicActivity.events = new ArrayList<>();
//        try {
//            JSONObject root = new JSONObject(json);
//            JSONArray eventArray = root.getJSONArray("events");
//            for (int i = 0; i < eventArray.length(); i++) {
//                JSONObject event = eventArray.getJSONObject(i);
//                int pk = event.getInt("pk");
//                String NAME = event.getString("name");
//                String CREATEDATE = event.getString("creationDate");
//                String USERADMIN = event.getString("userAdmin");
//                String DEADLINE = event.getString("deadline");
//                String LOC = event.getString("loc");
//                String DATESTART = event.getString("dateStart");
//                String DATEFINISH = event.getString("dateFinish");
//                JSONArray LOCATIONS = event.getJSONArray("locations");
//                String[] POSSIBLELOCATION = new String[3];
//                for (int j = 0; j < LOCATIONS.length(); j++) {
//                    POSSIBLELOCATION[j] = LOCATIONS.getString(j);
//                }
//                JSONArray DATES = event.getJSONArray("possibleDates");
//                String[] POSSIBLEDATES = new String[3];
//                for (int j =0; j < DATES.length(); j++){
//                    POSSIBLEDATES[j]=DATES.getString(j);
//                }
//                ArrayList<Person> GUESTS = new ArrayList<>();
//                JSONArray jsonArrayGuests = event.getJSONArray("guests");
//                JSONObject jsonObjectGuest;
//                for(int j = 0; j< jsonArrayGuests.length(); j++) {
//                    jsonObjectGuest = jsonArrayGuests.getJSONObject(j);
//                    String USERNAME = jsonObjectGuest.getString("username");
//                    String LASTNAME = jsonObjectGuest.getString("lastName");
//                    String FIRSTNAME = jsonObjectGuest.getString("firstName");
//                    int pkGUEST = jsonObjectGuest.getInt("pk");
//                    GUESTS.add(new Person(FIRSTNAME, pkGUEST,  LASTNAME, USERNAME ));
//                }
//                ArrayList<Resource> RESOURCES = new ArrayList<>();
//                JSONArray jsonArrayRESOURCES =  event.getJSONArray("resourcesNotAssigned");
//                Event e = new Event(pk, "sad", USERADMIN, NAME, CREATEDATE, DEADLINE, LOC, DATEFINISH, POSSIBLELOCATION,POSSIBLEDATES);
//                e.setGuests(GUESTS);
//                Log.v("Cast event", e.toString());
//                BasicActivity.events.add(e);
//            }
//            //for(int j = 0; j< BasicActivity.events.size(); j++){
//            //  Log.v("Event Casting", BasicActivity.events.get(j).toString());
//            //}
//            if (showScreen) {
//                EventListAdapter eventListAdapter = new EventListAdapter((Activity) context, BasicActivity.events);
//                ListView listView = (ListView) ((Activity) context).findViewById(R.id.list_events);
//                listView.setAdapter(eventListAdapter);
//                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        BasicActivity.answerEvent = position;
//                        Intent i = new Intent(context, AnswerEventActivity.class);
//                        context.startActivity(i);
//                    }
//                });
//
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }

    /*
    @Override
    protected void onPostExecute(String json) {
        Gson gson =  new Gson();
        //List<Event> EVENT = gson.fromJson(json, Event.class);
        //BasicActivity.events = gson.fromJson(json, Event.class);

    }*/

}