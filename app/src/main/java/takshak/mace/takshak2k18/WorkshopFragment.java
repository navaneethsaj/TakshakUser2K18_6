package takshak.mace.takshak2k18;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link WorkshopFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link WorkshopFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WorkshopFragment extends Fragment {

    ListView listView;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    String dept;

    private OnFragmentInteractionListener mListener;

    public WorkshopFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WorkshopFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WorkshopFragment newInstance(String param1, String param2) {
        WorkshopFragment fragment = new WorkshopFragment();
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
        EventListActivity activity = (EventListActivity)getActivity();
        dept = activity.getDept();
        return inflater.inflate(R.layout.fragment_workshop, container, false);
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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = view.findViewById(R.id.listviewWorkshop);
        String filename = null;

        switch (dept)
        {
            case "mca":
                filename = "mca_workshop.json";
                break;
            case "ece":
                filename = "ece_workshop.json";
                break;
            case "eee":
                filename = "eee_workshop.json";
                break;
            case "mech":
                filename = "mech_workshop.json";
                break;
            case "cse":
                filename = "cse_workshop.json";
                break;
            case "core":
                filename = "core_workshop.json";
                break;
            case "civil":
                filename = "civil_workshop.json";
                break;
            default:
                Log.d("Dept:","No such dept");
        }

        //filename = "civil_workshop.json";

        ArrayList<EventObject> eventObjects = new ArrayList<>();
        String json = null;
        InputStream is = null;
        try {
            is = getActivity().getAssets().open(filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int size = 0;
        try {
            size = is.available();
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] buffer = new byte[size];
        try {
            is.read(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            json = new String(buffer, "UTF-8");
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0 ; i<jsonArray.length() ; ++i){
                String name = jsonArray.getJSONObject(i).getString("event_name");
                String id = jsonArray.getJSONObject(i).getString("event_id");
                String dept = jsonArray.getJSONObject(i).getString("event_dept");
                String link = jsonArray.getJSONObject(i).getString("event_link");
                String cat = jsonArray.getJSONObject(i).getString("category");
                String imgname = jsonArray.getJSONObject(i).getString("imagename");
                String desc = jsonArray.getJSONObject(i).getString("eventdesc");
                if (name == null){
                    name = "EventName";
                }if (id == null){
                    id = "NOID";
                }if (dept == null){
                    dept = "Department";
                }if (link == null){
                    link = "Link";
                }if (cat == null){
                    cat = "Category";
                }if (imgname == null){
                    imgname = "NoImg";
                }if (desc == null){
                    desc = "Description";
                }
                eventObjects.add(new EventObject(name,id,dept,link,cat,imgname,desc,getContext()));
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //eventObjects.add(new EventObject("Event1","1001","CS","nolink","Action","dog.jpg",getContext()));
        //eventObjects.add(new EventObject("Event2","1002","CS","nolink","Action","cat.png",getContext()));

        WorkShopEventAdapter adapter = new WorkShopEventAdapter(getActivity(),R.layout.event_item_layout,eventObjects);
        listView.setAdapter(adapter);
    }
}
