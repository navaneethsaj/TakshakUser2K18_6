package takshak.mace.takshak2k18;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;

public class EventObject {
    public String event_name;
    public String event_id;
    public String event_dept;
    public String event_link;
    public String event_category;
    public String imageUniqueName;
    public String eventDesc;
    public Context context;
    public String URL = "domain.com/eventreg?id=";

    public EventObject(String event_name, String event_id, String event_dept, String event_link, String event_category, String imageUniqueName,String eventDesc, Context context) {
        this.event_name = event_name;
        this.event_id = event_id;
        this.eventDesc = eventDesc;
        this.event_dept = event_dept;
        this.event_link = event_link;
        this.event_category = event_category;
        this.imageUniqueName = imageUniqueName;
        this.context = context;
    }

    public String getEventURL(){
        return URL+getEvent_id();
    }

    public String getEventDesc() {
        return eventDesc;
    }

    public String getEvent_name() {
        return event_name;
    }

    public String getEvent_id() {
        return event_id;
    }

    public String getEvent_dept() {
        return event_dept;
    }

    public String getEvent_link() {
        return event_link;
    }

    public String getEvent_category() {
        return event_category;
    }

    public Bitmap getImgBitmap(){
        AssetManager assetManager = context.getAssets();
        InputStream is = null;
        try {
            is = assetManager.open(imageUniqueName);
        } catch (IOException e) {
            e.printStackTrace();
            try {
                is = assetManager.open("noimg.jpg");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        Bitmap bitmap = BitmapFactory.decodeStream(is);
        return bitmap;
    }
}
