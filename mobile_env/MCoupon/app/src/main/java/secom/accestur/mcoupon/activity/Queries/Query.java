package secom.accestur.mcoupon.activity.Queries;

import android.os.AsyncTask;

/**
 * Created by jaimetorres on 13/12/16.
 */

public class Query extends AsyncTask<String,Void,String> {

    public static final int GET = 1;
    public static final int POST = 2;

    @Override
    protected String doInBackground(String... params) {

        if (params[0].equals("get")) {
            return QueryUtils.doGet(params[1]);
        }
//        if(params[0].equals("post")){
//            return QueryUtils.doPost(params[1],params[2]);
//        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}
