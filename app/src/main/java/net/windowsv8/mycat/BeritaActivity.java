package net.windowsv8.mycat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Windowsv8 on 04/05/2018.
 */

public class BeritaActivity extends AppCompatActivity {

    public static final String TAG_USERNAME = "username";
    SharedPreferences sharedPreferences;
    String username;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RequestQueue requestQueue;
    private SwipeRefreshLayout refreshLayout;
    private Calendar calendar;
    SimpleDateFormat dateFormat;
    BeritaAdapter adapterBerita;
    public String now;
    public String outputdate;

    ArrayList<Berita> beritaArrayList = new ArrayList<>();

    private static final String TAG = BeritaActivity.class.getSimpleName();

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, HomeActivity.class));
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        finish();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_berita);

        refreshLayout = findViewById(R.id.swiperefreshBerita);
        recyclerView = findViewById(R.id.recyclerViewBerita);
        final Toolbar toolbar = findViewById(R.id.toolbarBertaActivity);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Event");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        adapterBerita = new BeritaAdapter(beritaArrayList,this);
        recyclerView.setAdapter(adapterBerita);

        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        now = dateFormat.format(calendar.getTime());
        Log.e(TAG,"dapet tanggal :" + now);

        sharedPreferences = getSharedPreferences(LoginAdminActivity.my_shared_preferences_admin, Context.MODE_PRIVATE);
        username = sharedPreferences.getString(TAG_USERNAME, "username");
        Log.e("tes",username);

        getData(now);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }

            private void refresh() {
                getData(now);
                refreshLayout.setRefreshing(false);
            }
        });
    }

    private void getData(String tanggal) {
        final ProgressBar progressBar = findViewById(R.id.progressBarBerita);
        progressBar.setVisibility(View.VISIBLE);
        setProgressBarIndeterminateVisibility(true);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Config.BERITA_URL + tanggal,
                new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                parseData(response);
                Log.e(TAG,"Berita : " +response);
                progressBar.setVisibility(View.INVISIBLE);
                refreshLayout.setRefreshing(false);
            }
        },
        new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
                refreshLayout.setRefreshing(false);
                Log.e(TAG, "error listener : " + error);
            }
        });
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    private void parseData(JSONArray jsonArray) {
        beritaArrayList.clear();

        for (int i = 0;i < jsonArray.length();i++){
            try{
                Berita berita = new Berita();
                JSONObject jObject = jsonArray.getJSONObject(i);

                berita.setJudul(jObject.getString(Config.KEY_JUDUL));
                berita.setIsi(jObject.getString(Config.KEY_ISI));
                SimpleDateFormat input= new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat output = new SimpleDateFormat("dd-MM-yyyy");
                try {
                    Date date = input.parse(jObject.getString("tanggal"));
                    outputdate = output.format(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                berita.setTanggal(outputdate);
                berita.setTempat(jObject.getString(Config.KEY_TEMPAT));
                berita.setContact(jObject.getString(Config.KEY_CONTACT));
                beritaArrayList.add(berita);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        adapterBerita.notifyDataSetChanged();
    }

}
