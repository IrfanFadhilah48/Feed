package net.windowsv8.mycat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Windowsv8 on 07/12/2017.
 */

public class PetshopActivity extends AppCompatActivity implements LocationListener {

    private RecyclerView recyclerViewpetshop;
    private RecyclerView.LayoutManager layoutManager;
    private RequestQueue requestQueue;
    private SwipeRefreshLayout swipeRefreshLayout;
    private PermissionHelper permissionHelper;


    ArrayList<Petshop> petshopArrayList = new ArrayList<>();
    Double latitude, longitude;
    LocationManager locationManager;
    Location location;
    Criteria criteria;
    String provider;
    PetshopAdapter petshopAdapter;
    Toolbar toolbar;

    private static final String TAG = PetshopActivity.class.getSimpleName();

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_petshop);

//        permissionHelper = new PermissionHelper(this);

        //Toolbar
        toolbar = findViewById(R.id.toolbarPetshopActivity);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Pet Shop");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });

        swipeRefreshLayout = findViewById(R.id.swiperefreshPetshop);
        recyclerViewpetshop = findViewById(R.id.recyclerViewPetshop);
        recyclerViewpetshop.setHasFixedSize(true);
        recyclerViewpetshop.setNestedScrollingEnabled(false);
        layoutManager = new LinearLayoutManager(this);
        recyclerViewpetshop.setLayoutManager(layoutManager);

        petshopAdapter = new PetshopAdapter(petshopArrayList, this);
        recyclerViewpetshop.setAdapter(petshopAdapter);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        criteria = new Criteria();

        provider = locationManager.getBestProvider(criteria, false);
        Log.e("tesptro", provider);
        lokasi();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        lokasi();
                    }
                });
            }
        });
    }


    private void lokasi() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        location = locationManager.getLastKnownLocation(provider);

        Log.v(TAG, "lokasi : " + location);
        locationManager.requestLocationUpdates(provider, 1000, 1, this);

        if (location != null){
            onLocationChanged(location);
        }else {
            onLocationChanged(location);
        }
        swipeRefreshLayout.setRefreshing(true);
    }

    private void getData(Double lat, Double lng) {
        final ProgressBar progressBar = findViewById(R.id.progressBarPetshop);
        progressBar.setVisibility(View.VISIBLE);
        setProgressBarIndeterminateVisibility(true);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Config.PETSHOP_URL +lat+ "&lng=" +lng,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        parseData(response);
                        Log.e(TAG, "Test : "+response);
                        progressBar.setVisibility(View.INVISIBLE);
                        swipeRefreshLayout.setRefreshing(false);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(PetshopActivity.this,error.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "error listener : " + error);
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    private void parseData(JSONArray jsonArray) {
        petshopArrayList.clear();
        for (int i=0;i < jsonArray.length();i++){
            try {
                Petshop petshop = new Petshop();
                JSONObject jObject = jsonArray.getJSONObject(i);

                petshop.setImageUrlpetshop(jObject.getString(Config.TAG_IMAGE_URL));
                petshop.setNama(jObject.getString(Config.TAG_NAME_PETSHOP));
                petshop.setAlamat(jObject.getString(Config.TAG_ALAMAT_PETSHOP));
                petshop.setTelpon(jObject.getString(Config.TAG_TELEPON_PETSHOP));
                petshop.setRating(jObject.getString(Config.TAG_RATING_PETSHOP));
                petshop.setJambuka(jObject.getString(Config.TAG_JAMBUKA_PETSHOP));
                petshop.setJamtutup(jObject.getString(Config.TAG_JAMTUTUP_PETSHOP));
                petshop.setDeskripsi(jObject.getString(Config.TAG_DESKRIPSI_PETSHOP));
                petshop.setLatitude(jObject.getString(Config.TAG_LAT_PETSHOP));
                petshop.setLongitude(jObject.getString(Config.TAG_LNG_PETSHOP));
                petshop.setJadwal(jObject.getString(Config.TAG_JADWAL_PETSHOP));
                double jarak = Double.parseDouble(jObject.getString(Config.TAG_JARAK_PETSHOP));
                petshop.setJarak("" + round(jarak,3));
                petshopArrayList.add(petshop);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        petshopAdapter.notifyDataSetChanged();
//        locationManager.removeUpdates(this);
    }

    private double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    @Override
    public void onLocationChanged(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();

        Log.e(TAG, "Userlocation latitude:" + latitude + ",longitude" + longitude);
        getData(latitude,longitude);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

}
