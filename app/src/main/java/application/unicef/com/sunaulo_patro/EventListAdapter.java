package application.unicef.com.sunaulo_patro;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by Bibesh on 11/16/16.
 */
public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.EventListAdapter_ViewHolder> {

    LayoutInflater inflate;
    List<EventInformation> information;
    Activity activity;
    Calendar calendar = Calendar.getInstance();

    public EventListAdapter(Context applicationContext, List<EventInformation> data, Activity avt) {
        this.information = data;
        inflate = LayoutInflater.from(applicationContext);
        this.activity = avt;
    }

    @Override
    public EventListAdapter_ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflate.inflate(R.layout.design_layout, parent, false);
        EventListAdapter_ViewHolder vi = new EventListAdapter_ViewHolder(v);
        return vi;
    }

    @Override
    public void onBindViewHolder(EventListAdapter_ViewHolder holder, final int position) {
        final EventInformation inf = information.get(position);
        holder.eventList_date.setText(inf.date);
        holder.eventList_title.setText(inf.eventTitle);

        holder.eventListCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    final Dialog dialog = new Dialog(activity);
                    dialog.setContentView(R.layout.event_page);

                    TextView title = (TextView) dialog.findViewById(R.id.event);
                    TextView date = (TextView) dialog.findViewById(R.id.date);
                    TextView description = (TextView) dialog.findViewById(R.id.eventdescription);
                    Button btn = (Button) dialog.findViewById(R.id.floatingActionButton3);
                    Button btn2 = (Button) dialog.findViewById(R.id.map);

                    EventInformation inf = information.get(position);
                    title.setText(inf.eventTitle);
                    date.setText(inf.date);
                    description.setText(inf.description);

                    btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            sentToCalendar(position, dialog);
                        }
                    });
                    btn2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent inte=new Intent(activity.getApplicationContext(),MapsActivity.class);
                            activity.startActivity(inte);

                        }
                    });
                    dialog.show();
                } catch (Exception err) {
                    Toast.makeText(view.getContext(), "" + err.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return information.size();
    }

    private void sentToCalendar(int position, Dialog dialog) {

        EventInformation info = information.get(position);
        // Toast.makeText(activity, "hello", Toast.LENGTH_SHORT).show();
        TimeZone timeZone = TimeZone.getDefault();

        long calID = 3;
        long startMillis = 0;
        long endMillis = 0;

        Calendar cal = Calendar.getInstance();
        cal.set(2016, calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE)+1);
        startMillis = cal.getTimeInMillis();
        Log.v("data",String.valueOf(calendar.get(Calendar.MINUTE)));

        Calendar endTime = Calendar.getInstance();
        cal.set(2016, calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE)+2 );
        endMillis = endTime.getTimeInMillis();
        Log.v("data",String.valueOf(calendar.get(Calendar.HOUR)+2));



        ContentResolver cr = activity.getContentResolver();
        ContentValues values = new ContentValues();
        values.put(CalendarContract.Events.HAS_ALARM, true);

        values.put(CalendarContract.Events.DTSTART, startMillis);
        values.put(CalendarContract.Events.DTEND, endMillis);

        values.put(CalendarContract.Events.TITLE, info.eventTitle);
        values.put(CalendarContract.Events.DESCRIPTION, info.description);
        values.put(CalendarContract.Events.CALENDAR_ID, calID);
        values.put(CalendarContract.Events.EVENT_TIMEZONE, timeZone.getID()); //returns the string "Asia/Kathmandu"
        if (ActivityCompat.checkSelfPermission(activity, android.Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, values);
        dialog.dismiss();
        Toast.makeText(activity,"Information Saved",Toast.LENGTH_LONG).show();

    }

    public class EventListAdapter_ViewHolder extends RecyclerView.ViewHolder {
        TextView eventList_date;
        TextView eventList_title;
        CardView eventListCV;

        public EventListAdapter_ViewHolder(View itemView) {
            super(itemView);
            eventList_date = (TextView) itemView.findViewById(R.id.eventList_date);
            eventList_title = (TextView) itemView.findViewById(R.id.eventList_title);
            eventListCV = (CardView) itemView.findViewById(R.id.eventListCV);
        }
    }
}

