package takshak.mace.takshak2k18;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class WorkShopEventAdapter extends ArrayAdapter<EventObject> {

    ArrayList<EventObject> eventObjects;
    SharedPreferences sharedPreferences;
    public final String UserPREFERENCES = "userinfo";
    public final String NAME = "username";
    public final String USERID = "usserid";
    public final String MOBILENO = "mobileno";
    public final String EMAILID = "emailid";

    String uid;

    public WorkShopEventAdapter(@NonNull Context context, int resource, @NonNull ArrayList<EventObject> objects) {
        super(context, resource, objects);
        eventObjects = objects;
        sharedPreferences = context.getSharedPreferences(UserPREFERENCES, Context.MODE_PRIVATE);
        this.uid = sharedPreferences.getString(USERID,"NOT REGISTERED");
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.event_item_layout, null);

        final TextView eventNameView = (TextView) v.findViewById(R.id.eventName);
        TextView eventIdView = v.findViewById(R.id.eventId);
        TextView eventDeptView = v.findViewById(R.id.eventDept);
        TextView eventCategory = v.findViewById(R.id.eventCategory);
        TextView eventDesc = v.findViewById(R.id.eventDesc);
        TextView reg_button = v.findViewById(R.id.register);

        final ImageView imageView = (ImageView) v.findViewById(R.id.eventImage);
        final ImageView expandImg = v.findViewById(R.id.expandicon);
        final TextView knowmore = v.findViewById(R.id.knowmore);

        final LinearLayout linearLayout = v.findViewById(R.id.moreinfo);
        LinearLayout item = v.findViewById(R.id.itemlayout);

        eventNameView.setText(eventObjects.get(position).getEvent_name());
        eventIdView.setText(eventObjects.get(position).getEvent_id());
        eventDeptView.setText(eventObjects.get(position).getEvent_dept());
        eventCategory.setText(eventObjects.get(position).getEvent_category());
        eventDesc.setText(eventObjects.get(position).getEventDesc());

        imageView.setImageBitmap(eventObjects.get(position).getImgBitmap());
        final String eventid = eventObjects.get(position).getEvent_id();

        linearLayout.setVisibility(View.GONE);
        item.setOnClickListener(new View.OnClickListener() {
            boolean expanded = false;
            @Override
            public void onClick(View view) {
                if (!expanded){
                    expandImg.setImageResource(R.drawable.ic_arrow_drop_up_black_24dp);
                    knowmore.setText("less");
                    linearLayout.setVisibility(View.VISIBLE);
                    //imageView.getLayoutParams().width =(int) (200 * getContext().getResources().getDisplayMetrics().density);
                    //imageView.getLayoutParams().height =(int) (200 * getContext().getResources().getDisplayMetrics().density);
                    expanded = true;

                }else {
                    expandImg.setImageResource(R.drawable.ic_more_vert_white_24dp);
                    linearLayout.setVisibility(View.GONE);
                    knowmore.setText("more");
                    //imageView.getLayoutParams().width =(int) (110 * getContext().getResources().getDisplayMetrics().density);
                    //imageView.getLayoutParams().height =(int) (110 * getContext().getResources().getDisplayMetrics().density);
                    expanded = false;
                }
            }
        });

        if (uid.equals("NOT REGISTERED")){
            reg_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getContext(),"User not registered\nRedirecting to register page",Toast.LENGTH_LONG).show();
                }
            });
        }else {
            reg_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getContext(),"Register uid "+uid+" for event "+eventid,Toast.LENGTH_LONG).show();
                }
            });
        }

        return v;
    }
}
