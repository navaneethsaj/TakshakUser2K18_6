package takshak.mace.takshak2k18;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Tab1 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    Button sendsong;
    Button login;
    EditText title,moreinfo;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    String SONGREQCOUNT = "reqcount";

    public final String NAME = "username";
    SharedPreferences.Editor editor;

    String url = "https://us-central1-takshakapp18.cloudfunctions.net/requestsong?";
    private FirebaseAuth mAuth;

    private OnFragmentInteractionListener mListener;

    public Tab1() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Tab1.
     */
    // TODO: Rename and change types and number of parameters
    public static Tab1 newInstance(String param1, String param2) {
        Tab1 fragment = new Tab1();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.frag_1, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    String UserPREFERENCES = "userinfo";
    final String USERID = "usserid";
    TextView remaining;
    SharedPreferences sharedpreferences;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sendsong = view.findViewById(R.id.sendsongbutton);
        title = view.findViewById(R.id.songtitle);
        moreinfo = view.findViewById(R.id.moreinfo);

        sharedpreferences = getActivity().getSharedPreferences(UserPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        remaining = view.findViewById(R.id.timeremaining);
        remaining.setVisibility(View.GONE);






        long takshakTimestamp = 1531486629;





        long now = System.currentTimeMillis()/1000;
        Log.d("tak", String.valueOf(takshakTimestamp));
        Log.d("now", String.valueOf(now));
        TextView servicegolive = view.findViewById(R.id.servicegolive);
        //if ()
        //remaining.setText("Service go live on Takshak2k18 day");
        /*int takshakTimeStamp = 1531655779;

        int now = (int) (System.currentTimeMillis()/1000);
        remaining.setText(Integer.toString(GetDifference(now,takshakTimeStamp)));*/

        /*FirebaseUser currentUser = mAuth.getCurrentUser();
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        alertDialogBuilder.setTitle("Authenticating User").setMessage("Please Wait");
        final AlertDialog dialog = alertDialogBuilder.create();
        dialog.setCanceledOnTouchOutside(false);
        if (currentUser == null){
            sendsong.setEnabled(false);
            Log.d("Auth","Not authenticated");
            if (!dialog.isShowing()){
                dialog.show();
            }
            mAuth.signInAnonymously().addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        sendsong.setEnabled(true);
                        Log.d("Auth","Authentication successful");
                        if (dialog.isShowing()){
                            dialog.dismiss();
                        }
                    }else {
                        Log.d("Auth","Authentication Failed");
                        if (dialog.isShowing()){
                            dialog.dismiss();
                        }
                        Toast.makeText(getActivity(),"Authentication failed",Toast.LENGTH_SHORT).show();
                    }
                }

            });
        }else {
            sendsong.setEnabled(true);
            Log.d("Auth","Authenticated user");
        }*/

        login = view.findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),LoginActivity.class);
                startActivity(intent);
            }
        });
        if (sharedpreferences.getString(USERID,null) == null){
            login.setEnabled(true);
            login.setVisibility(View.VISIBLE);
        }else {
            login.setEnabled(false);
            login.setVisibility(View.GONE);
        }

        sendsong.setText("Request Song ("+sharedpreferences.getInt(SONGREQCOUNT,5)+")");
        if (sharedpreferences.getInt(SONGREQCOUNT,5) <= 0){
            sendsong.setEnabled(false);
            sendsong.setText("REQUEST QUOTA EXCEEDED");
        }
        sendsong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConnectivityManager conMgr = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                if ( conMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED
                        || conMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED ) {
                    if (sharedpreferences.getString(USERID,null) != null) {;
                        if (!(title.getText().toString().equals(""))){
                            new SongAsyncTask().execute();
                        }else {
                            Toast.makeText(getActivity(),"Enter Song Details",Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(getActivity(), "Login to use this service", Toast.LENGTH_SHORT).show();
                    }
                }
                else if ( conMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.DISCONNECTED
                        || conMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.DISCONNECTED) {

                    Toast.makeText(getActivity(), "No network", Toast.LENGTH_SHORT).show();
                }

            }
        });
        if (now > takshakTimestamp){
            sendsong.setEnabled(true);
            title.setEnabled(true);
            servicegolive.setVisibility(View.GONE);
            moreinfo.setEnabled(true);
        }else {
            title.setEnabled(false);
            moreinfo.setEnabled(false);
            sendsong.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getActivity(),"Service go live on takshak day",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    class SongAsyncTask extends AsyncTask<String,Void,String >{

        @Override
        protected String doInBackground(String... strings) {
            OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                    .readTimeout(4, TimeUnit.SECONDS)
                    .writeTimeout(4,TimeUnit.SECONDS)
                    .connectTimeout(4,TimeUnit.SECONDS).build();
            String tit = title.getText().toString();
            String userid = sharedpreferences.getString(USERID, null)+"__"+sharedpreferences.getString(NAME,null);
            String more = moreinfo.getText().toString();
            //url = url + "id=" + sharedpreferences.getString(USERID, null) + "&title=" +  + "&artist=noValue" + "&movie=" + movie_genre.getText().toString();
            //url = "https://us-central1-takshakapp18.cloudfunctions.net/requestsong?id=1001&title=baby&artist=null&movie=null";

            url = "https://us-central1-takshakapp18.cloudfunctions.net/requestsong?"+
                    "id="+userid+
                    "&title="+tit+
                    "&moreinfo="+more;

            Request request = new Request.Builder()
                    .url(url)
                    .build();
            Response response = null;
            try {
                Log.d("start","ok");
                response = okHttpClient.newCall(request).execute();
                Log.d("stop","ok");
                String res = response.body().string();
                Log.d("res","ok");
                return res;
            } catch (IOException e) {
                Log.d("timeout","request timed out");
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            sendsong.setEnabled(false);
            sendsong.setText("SENDING REQUEST");
        }

        @Override
        protected void onPostExecute(String res) {
            super.onPostExecute(res);
            title.setText("");
            moreinfo.setText("");
            sendsong.setEnabled(true);
            sendsong.setText("Request song");
            if (res != null){
                if (res.equals("success")) {
                    //Toast.makeText(getActivity(), "Request sent", Toast.LENGTH_SHORT).show();
                    editor.putInt(SONGREQCOUNT,sharedpreferences.getInt(SONGREQCOUNT,5) - 1 );
                    editor.commit();
                    sendsong.setText("Request Song ("+sharedpreferences.getInt(SONGREQCOUNT,5)+")");
                    Toast.makeText(getActivity(), "Request Sent\nRequest left : "+sharedpreferences.getInt(SONGREQCOUNT,5), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Request failed\nTry again", Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(getActivity(), "No response\nTry again", Toast.LENGTH_SHORT).show();
            }
            if (sharedpreferences.getInt(SONGREQCOUNT,5) <= 0){
                sendsong.setEnabled(false);
                sendsong.setText("REQUEST QUOTA EXCEEDED");
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (sharedpreferences.getString(USERID,null) == null){
            login.setEnabled(true);
            login.setVisibility(View.VISIBLE);
        }else {
            login.setEnabled(false);
            login.setVisibility(View.GONE);
        }
        if (sharedpreferences.getInt(SONGREQCOUNT,5) <= 0){
            sendsong.setEnabled(false);
            sendsong.setText("REQUEST QUOTA EXCEEDED");
        }
    }
    public int GetDifference(long start,long end){
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(start);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int min = cal.get(Calendar.MINUTE);
        long t=(23-hour)*3600000+(59-min)*60000;

        t=start+t;

        int diff=0;
        if(end>t){
            diff=(int)((end-t)/ TimeUnit.DAYS.toMillis(1))+1;
        }

        return  diff;
    }
}