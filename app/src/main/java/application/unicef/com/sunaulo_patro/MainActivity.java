package application.unicef.com.sunaulo_patro;

import android.app.Activity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    RecyclerView eventList;
    RequestQueue queue;

    ArrayList<String> date;
    ArrayList<String> eventTitle;
    ArrayList<String> description;
    ArrayList<String> _id;
    Toolbar bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bar = (Toolbar) findViewById(R.id.toolbar);

        eventList = (RecyclerView) findViewById(R.id.eventList);
        String url = "http://192.168.5.173/sunaulo/";
        queue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    eventList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    eventList.setAdapter(new EventListAdapter(MainActivity.this.getApplicationContext(), getData(response), MainActivity.this));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("ERROR", "" + error.getMessage());
            }
        });
        queue.add(req);

    }


    private List<EventInformation> getData(JSONArray response) throws JSONException {
        date = new ArrayList<>();
        eventTitle = new ArrayList<>();
        description = new ArrayList<>();
        _id = new ArrayList<>();

        List<EventInformation> infos = new ArrayList<>();
        for (int i = 0; i < response.length(); i++) {
            JSONObject obj = response.getJSONObject(i);
            date.add(obj.getString("event_date"));
            eventTitle.add(obj.getString("event_title"));
            description.add(obj.getString("event_description"));
            _id.add(obj.getString("event_id"));
        }
        for (int i = 0; i < date.size(); i++) {
            EventInformation info = new EventInformation();
            info.date = date.get(i);
            info.eventTitle = eventTitle.get(i);
            info.description = description.get(i);
            info._id = _id.get(i);
            infos.add(info);
        }
        return infos;
    }


}