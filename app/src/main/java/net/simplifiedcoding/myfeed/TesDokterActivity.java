package net.simplifiedcoding.myfeed;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Windowsv8 on 26/04/2018.
 */

public class TesDokterActivity extends AppCompatActivity {

    private RecyclerView rv;
    private RecyclerView.LayoutManager layoutManager;
    DokterAdapters dokterAdapters;
    ArrayList<TesDokter>tesDokters;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dokter);

        rv = findViewById(R.id.recyclerViewDokter);
        rv.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(layoutManager);

        getData();

        dokterAdapters = new DokterAdapters(tesDokters,this);
        rv.setAdapter(dokterAdapters);
    }

    private void getData() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Config.DOKTER_URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                parseData(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "No More Item Available",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void parseData(JSONArray response) {
        for (int i =0; i<response.length(); i++){
            try{
                TesDokter tesDokter = new TesDokter();
                JSONObject object = response.getJSONObject(i);
                tesDokter.setImage(object.getString("image"));
                tesDokter.setNama(object.getString("nama"));
                tesDokter.setAlamat(object.getString("alamat"));
                tesDokter.setTelpon(object.getString("telpon"));
                tesDokter.setJambuka(object.getString("jambuka"));
                tesDokter.setJamtutup(object.getString("jamtutup"));
                tesDokter.setRating(object.getString("rating"));
                tesDokters.add(tesDokter);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        dokterAdapters.notifyDataSetChanged();
    }
}
