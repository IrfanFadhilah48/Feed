package net.windowsv8.mycat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

public class MainActivity extends AppCompatActivity {

    SuperHero superHero;
    SuperHeroUser superHeroUser;
    private ArrayList<SuperHero> listSuperHeroes;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    SwipeRefreshLayout refresh;
    CardAdapter adapter;
    boolean grid = true;
    boolean list = true;
    Menu menu;
    SharedPreferences sharedPreferences;
    String cekadmin;
    public static String TAG_ID = "id";


    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(MainActivity.this, HomeActivity.class));
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        super.onBackPressed();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences(LoginActivity.my_shared_preferences, Context.MODE_PRIVATE);
        cekadmin = sharedPreferences.getString(TAG_ID,"id");
        Log.e("tester admin:", cekadmin);

        recyclerView = findViewById(R.id.recyclerView);
        final Toolbar toolbar = findViewById(R.id.toolbarFJB);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Forum Jual Beli");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });

        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        listSuperHeroes = new ArrayList<>();
        adapter = new CardAdapter(listSuperHeroes, this);
        recyclerView.setAdapter(adapter);

        getData();

        refresh = findViewById(R.id.swiperefresh);

        refresh.setColorSchemeColors(getResources().getColor(R.color.colorAccent),
                getResources().getColor(R.color.colorPrimary),
                getResources().getColor(R.color.colorPrimaryDark));

        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshItem();
            }
            void refreshItem(){
                getData();
                refresh.setRefreshing(false);
            }
        });

    }

    private void getData() {
        final ProgressBar progressBar = findViewById(R.id.progressBar1);
        progressBar.setVisibility(View.VISIBLE);
        setProgressBarIndeterminateVisibility(true);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Config.DATA_URL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        parseData(response);
                        progressBar.setVisibility(View.INVISIBLE);
//                        progressDialog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(MainActivity.this, error.getMessage().toString(),Toast.LENGTH_SHORT).show();
                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(jsonArrayRequest);
    }

    //This method will parse json data
    private void parseData(JSONArray array){
        listSuperHeroes.clear();
        for (int i = 0; i < array.length(); i++) {
            superHero = new SuperHero();
            superHeroUser = new SuperHeroUser();
            JSONObject json = null;

            try {
                //Getting json
                json = array.getJSONObject(i);
                JSONObject jsonObject = new JSONObject(String.valueOf(json)).getJSONObject("user");
                //Adding data to the superhero object
                superHero.setId(json.getString(Config.TAG_ID_IMAGE));
                superHero.setImageUrl(json.getString(Config.TAG_IMAGE_URL));
                superHero.setName(json.getString(Config.TAG_NAME));
                superHero.setPublisher(json.getString(Config.TAG_PUBLISHER));
                superHero.setDescription(json.getString(Config.TAG_DESCRIPTION));
                superHero.setTelephone(json.getString(Config.TAG_TELEPHONE));
                superHero.setAddress(json.getString(Config.TAG_ADDRESS));
                superHero.setJenis(json.getString(Config.TAG_JENIS));
                superHero.setUsername(json.getString(Config.KEY_USERNAME));
                superHero.setSuperHeroUser(superHeroUser);
                superHeroUser.setNama(jsonObject.getString("nama"));
                superHeroUser.setEmail(jsonObject.getString("email"));
                String a = superHeroUser.getNama();
                String b = superHeroUser.getEmail();
                Log.e("testernama", a);
                Log.e("testeremail", b);

//                superHero.setSuperHeroUser(superHero.setSuperHeroUser());
//                superHeroUser.setNama(json.getString("nama"));
//                superHeroUser.setEmail(json.getString("email"));

            } catch (JSONException e) {
                e.printStackTrace();
            }
            listSuperHeroes.add(superHero);
        }
        adapter.notifyDataSetChanged();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        MenuItem searchViewItem = menu.findItem(R.id.search);
        MenuItem tes = menu.findItem(R.id.upload);
        SearchView searchView = (SearchView)MenuItemCompat.getActionView(searchViewItem);
        searchViewItem(searchView);
        this.menu = menu;
        if (cekadmin.contains("id")){
            tes.setVisible(false);
        }
        return true;

    }

    private void searchViewItem(SearchView searchView) {

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return true;
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id){
            case R.id.itemlist:
                if (grid == true){
                    showRecylerGrid();
                    menu.getItem(1).setIcon(getResources().getDrawable(R.drawable.ic_view_module_white_24dp));
                    list = true;
                    grid = false;
                    refresh.setRefreshing(false);
                    break;
                }else if(list == true) {
                    showRecyclerList();
                    menu.getItem(1).setIcon(getResources().getDrawable(R.drawable.ic_view_list_white_24dp));
                    grid = true;
                    list = false;
                    break;
                }
            case R.id.upload:
                startActivity(new Intent(MainActivity.this, UploadActivity.class));
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void showRecyclerList(){
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void showRecylerGrid(){
        layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

}