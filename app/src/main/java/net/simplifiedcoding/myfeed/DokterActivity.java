package net.simplifiedcoding.myfeed;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
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

/**
 * Created by Windowsv8 on 07/12/2017.
 */

public class DokterActivity extends AppCompatActivity implements LocationListener{

    private RecyclerView recyclerViewDokter;
    private RecyclerView.LayoutManager layoutManager;
    private RequestQueue requestQueue;
    private SwipeRefreshLayout swipeRefreshLayout;
    private PermissionHelper permissionHelper;


    ArrayList<Dokter> dokterArrayList = new ArrayList<>();
    Double latitude, longitude;
    LocationManager locationManager;
    Location location;
    Criteria criteria;
    String provider;
    DokterAdapter dokterAdapter;

    private static final String TAG = DokterActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dokter);
        permissionHelper = new PermissionHelper(this);


        swipeRefreshLayout = findViewById(R.id.swiperefreshDokter);
        recyclerViewDokter = findViewById(R.id.recyclerViewDokter);
        recyclerViewDokter.setHasFixedSize(true);


        layoutManager = new LinearLayoutManager(this);
        recyclerViewDokter.setLayoutManager(layoutManager);

//        getData();
//        getData(latitude, longitude);

//        dokterArrayList = new ArrayList<>();
//        requestQueue = Volley.newRequestQueue(this);

        dokterAdapter = new DokterAdapter(dokterArrayList, this);
        recyclerViewDokter.setAdapter(dokterAdapter);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        criteria = new Criteria();

        provider = locationManager.getBestProvider(criteria, false);

        lokasi();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                lokasi();
                swipeRefreshLayout.setRefreshing(false);
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
//            onLocationChanged(location);
        }
    }
//private getData(DOuble lat, Double lng)
    private void getData(Double lat, Double lng) {
        final ProgressBar progresBar =findViewById(R.id.progressBarDokter);
        progresBar.setVisibility(View.VISIBLE);
        setProgressBarIndeterminateVisibility(true);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Config.DOKTER_URL2 +lat+ "&lng=" +lng,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        parseData(response);
                        Log.e(TAG, "Test"+response);
                        progresBar.setVisibility(View.GONE);
                        swipeRefreshLayout.setRefreshing(false);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(DokterActivity.this,error.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "error listener : " + error);
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    private void parseData(JSONArray jsonArray) {
        dokterArrayList.clear();
        for (int i=0;i < jsonArray.length();i++){
//            Dokter dokter = new Dokter();
//            JSONObject jObject = null;

            try {
                Dokter dokter = new Dokter();
                JSONObject jObject = jsonArray.getJSONObject(i);
//                jObject = jsonArray.getJSONObject(i);

                dokter.setImageUrlDokter(jObject.getString(Config.TAG_IMAGE_URL));
                dokter.setNama(jObject.getString(Config.TAG_NAME_DOKTER));
                dokter.setAlamat(jObject.getString(Config.TAG_ALAMAT_DOKTER));
                dokter.setTelpon(jObject.getString(Config.TAG_TELEPON_DOKTER));
                dokter.setRating(jObject.getString(Config.TAG_RATING_DOKTER));
                dokter.setJambuka(jObject.getString(Config.TAG_JAMBUKA_DOKTER));
                dokter.setJamtutup(jObject.getString(Config.TAG_JAMTUTUP_DOKTER));
                double jarak = Double.parseDouble(jObject.getString(Config.TAG_JARAK));
                dokter.setJarak("" + round(jarak,2));
//                dokter.setJarak(jObject.getString(Config.TAG_JARAK));
                dokterArrayList.add(dokter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
//            dokterArrayList.add(dokter);
            //dokterArrayList.add(dokter);
        }
        dokterAdapter.notifyDataSetChanged();
//        dokterAdapter.notifyDataSetChanged();
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

        Log.e(TAG, "User location latitude:" + latitude + ",longitude" + longitude);
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
