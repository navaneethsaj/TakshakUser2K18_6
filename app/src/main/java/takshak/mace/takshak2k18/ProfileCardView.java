package takshak.mace.takshak2k18;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ProfileCardView extends AppCompatActivity {
    public final String UserPREFERENCES = "userinfo";
    public final String NAME = "username";
    public final String USERID = "usserid";
    public final String MOBILENO = "mobileno";
    public final String EMAILID = "emailid";
    String url = "https://us-central1-takshakapp18.cloudfunctions.net/getrank?id=";

    TextView name , mobileno , rank , name1;
    Button loginButton;

    SharedPreferences sharedpreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_card_view);
        ActionBar ab = getSupportActionBar();
        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);
        sharedpreferences = getSharedPreferences(UserPREFERENCES, Context.MODE_PRIVATE);


        loginButton = findViewById(R.id.login_button);
        name = findViewById(R.id.name);
        name1= findViewById(R.id.name1);
        mobileno = findViewById(R.id.number);
        rank = findViewById(R.id.rank);
        name1.setText(sharedpreferences.getString(NAME,"NOT REGISTERED"));
        name.setText(sharedpreferences.getString(NAME,"NOT REGISTERED"));
        mobileno.setText(sharedpreferences.getString(MOBILENO,"NOT REGISTERED"));

        if (sharedpreferences.getString(USERID,null) == null && sharedpreferences.getString(MOBILENO,null) ==null){
            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Toast.makeText(getApplicationContext(),"INtent",Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(i);
                    finish();
                }
            });
        }else {
            loginButton.setText("LOGGED IN");
            loginButton.setEnabled(false);
        }

        //url += sharedpreferences.getString(USERID,"NOID");
        if (sharedpreferences.getString(USERID,null) != null && sharedpreferences.getString(MOBILENO,null) !=null){
            ConnectivityManager conMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
            if ( conMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED
                    || conMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED ) {
                // notify user you are online
                new RankAsyncTask().execute(url + sharedpreferences.getString(USERID,"NOID"));
            }
            else if ( conMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.DISCONNECTED
                    || conMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.DISCONNECTED) {

                Toast.makeText(this,"No internet connection\nRank outdated\nConnect to internet and restart app",Toast.LENGTH_LONG).show();
            }
        }
        //new RankAsyncTask().execute(url);

    }

    class RankAsyncTask extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .readTimeout(4, TimeUnit.SECONDS)
                    .writeTimeout(4,TimeUnit.SECONDS)
                    .connectTimeout(4,TimeUnit.SECONDS).build();
            Request request = new Request.Builder()
                    .url(strings[0])
                    .build();

            Response response = null;
            try {
                response = client.newCall(request).execute();
                return response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
                Log.d("request","timedout");
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            rank.setText("UPDATING");
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s != null){
                rank.setText(s.toString());
            }else {
                rank.setText("Not Available");
                Toast.makeText(getApplicationContext(),"Request timed out",Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
