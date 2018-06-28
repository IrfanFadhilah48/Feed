package net.simplifiedcoding.myfeed;

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


import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
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
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static android.R.attr.breadCrumbShortTitle;
import static android.R.attr.editable;
import static android.R.attr.filter;
import static android.R.attr.switchMinWidth;
import static android.os.Build.VERSION_CODES.N;

public class MainActivity extends AppCompatActivity/*implements RecyclerView.OnScrollChangeListener*/ {

    //Creating a List of superheroes
    SuperHero superHero;
    private ArrayList<SuperHero> listSuperHeroes;

    //Creating Views
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    SwipeRefreshLayout refresh;
    //private RecyclerView.Adapter adapter;
    EditText inputSearch;
    SearchView sv;
    //Volley Request Queue
    private RequestQueue requestQueue;
    CardAdapter adapter;
    boolean grid = true;
    boolean grid1 = true;
    Menu menu;
    SharedPreferences sharedPreferences;
    String cekadmin;
    public static String TAG_ID = "id";

    //The request counter to send ?page=1, ?page=2  requests
    private int requestCount = 1;

    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(MainActivity.this, HomeActivity.class));
//        sharedPreferences = getSharedPreferences(LoginActivity.my_shared_preferences, Context.MODE_PRIVATE);
//        String id = sharedPreferences.getString(TAG_ID,"id");
//        String username = sharedPreferences.getString("username","username");
//        Log.e("data",id+"\n"+username);
        super.onBackPressed();
    }

//    @Override
//    public void finish() {
//        super.finish();
//        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences(LoginActivity.my_shared_preferences, Context.MODE_PRIVATE);
        cekadmin = sharedPreferences.getString(TAG_ID,"id");
        Log.e("tester admin:", cekadmin);
        //Initializing Views
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
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

        /*sv = (SearchView)findViewById(R.id.searchview);
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });*/
        /*inputSearch = (EditText)findViewById(R.id.editTextSearch);

        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());
            }
        });*/

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //Initializing our superheroes list
        listSuperHeroes = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(this);

        //Calling method to get data to fetch data
        getData();
        Collections.reverse(listSuperHeroes);

        //Adding an scroll change listener to recyclerview
        //recyclerView.setOnScrollChangeListener(this);

        //initializing our adapter
        adapter = new CardAdapter(listSuperHeroes, this);

        //Adding adapter to recyclerview
        recyclerView.setAdapter(adapter);

        refresh = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);

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

    //Request to get json from server we are passing an integer here
    //This integer will used to specify the page number for the request ?page = requestcount
    //This method would return a JsonArrayRequest that will be added to the request queue


    /*private JsonArrayRequest getDataFromServer(int requestCount) {
        //Initializing ProgressBar
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar1);

        //Displaying Progressbar
        progressBar.setVisibility(View.VISIBLE);
        setProgressBarIndeterminateVisibility(true);

        //JsonArrayRequest of volley
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Config.DATA_URL + String.valueOf(requestCount),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //Calling method parseData to parse the json response
                        parseData(response);
                        //Hiding the progressbar
                        progressBar.setVisibility(View.GONE);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);
                        //If an error occurs that means end of the list has reached
                        Toast.makeText(MainActivity.this, "No More Items Available", Toast.LENGTH_SHORT).show();
                    }
                });

        //Returning the request
        return jsonArrayRequest;
    }*/

    //This method will get data from the web api
    private void getData() {
        //Adding the method to the queue by calling the method getDataFromServer
        final ProgressBar progressBar = (ProgressBar)findViewById(R.id.progressBar1);
        progressBar.setVisibility(View.VISIBLE);
        setProgressBarIndeterminateVisibility(true);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Config.DATA_URL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        parseData(response);
                        progressBar.setVisibility(View.GONE);
//                        refresh.setRefreshing(false);
                    }
                },
                new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(MainActivity.this, error.getMessage().toString(),Toast.LENGTH_SHORT).show();
//                    refresh.setRefreshing(false);
                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(jsonArrayRequest);
        /*requestQueue.add(getDataFromServer(requestCount));
        //Incrementing the request counter
        requestCount++;*/
    }

    //This method will parse json data
    private void parseData(JSONArray array) {
        for (int i = 0; i < array.length(); i++) {
            //Creating the superhero object
//            SuperHero superHero = new SuperHero();
            superHero = new SuperHero();
            JSONObject json = null;
            try {
                //Getting json
                json = array.getJSONObject(i);

                //Adding data to the superhero object
                superHero.setImageUrl(json.getString(Config.TAG_IMAGE_URL));
                superHero.setName(json.getString(Config.TAG_NAME));
                superHero.setPublisher(json.getString(Config.TAG_PUBLISHER));
                superHero.setDescription(json.getString(Config.TAG_DESCRIPTION));
//                superHero.setLat(json.getString(Config.TAG_LOCATIONLAT));
//                superHero.setLng(json.getString(Config.TAG_LOCATIONLNG));
                superHero.setTelephone(json.getString(Config.TAG_TELEPHONE));
                superHero.setAddress(json.getString(Config.TAG_ADDRESS));
                superHero.setUsername(json.getString(Config.KEY_USERNAME));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //Adding the superhero object to the list
            listSuperHeroes.add(superHero);
        }

        //Notifying the adapter that data has been added or changed
        adapter.notifyDataSetChanged();
    }



    //This method would check that the recyclerview scroll has reached the bottom or not
    private boolean isLastItemDisplaying(RecyclerView recyclerView) {
        if (recyclerView.getAdapter().getItemCount() != 0) {
            int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
            if (lastVisibleItemPosition != RecyclerView.NO_POSITION && lastVisibleItemPosition == recyclerView.getAdapter().getItemCount() - 1)
                return true;
        }
        return false;
    }

    //untuk implement onScroll Changed listener
    //Overriden method to detect scrolling
    /*@Override
    public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        //Ifscrolled at last then
        if (isLastItemDisplaying(recyclerView)) {
            //Calling the method getdata again
            getData();
        }
    }*/

    /*public void filter(String text){
        List<SuperHero> filterdNames = new ArrayList<>();
        for (SuperHero s: listSuperHeroes){
            if (s.getName().toLowerCase().contains(text)){
                filterdNames.add(s);
            }
        }
        adapter.update(filterdNames);
    }*/



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
                /*adapter.getFilter().filter(query);
                return true;*/
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return true;

                //return false;
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id){
//            case R.id.exit:
//                finish();
//                break;
            case R.id.itemlist:
                if (grid == true){
                    showRecylerGrid();
                    menu.getItem(1).setIcon(getResources().getDrawable(R.drawable.ic_view_module_white_24dp));
                    grid1 = true;
                    grid = false;
                    break;
                }else if(grid1 == true) {
                    showRecyclerList();
                    menu.getItem(1).setIcon(getResources().getDrawable(R.drawable.ic_view_list_white_24dp));
                    grid = true;
                    grid1 = false;
                    break;
                }
            case R.id.upload:
                startActivity(new Intent(MainActivity.this, UploadPilihActivity.class));
                break;
//            case R.id.about:
//                startActivity(new Intent(MainActivity.this, AboutActivity.class));
//                Toast.makeText(getApplicationContext(), "test",Toast.LENGTH_SHORT).show();
//                break;



        }
//        if (id == R.id.about){
//            startActivity(new Intent(MainActivity.this,AboutActivity.class));
//        }else if (id == R.id.exit){
//            finish();
//        }else if (id == R.id.itemlist){
//            showRecyclerList();
//
//        }else if (id == R.id.itemgrid){
//            showRecylerGrid();
//        }
        return super.onOptionsItemSelected(item);
    }

    private void showRecyclerList(){
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        //Calling method to get data to fetch data
//        getData();

        //Adding an scroll change listener to recyclerview
        //recyclerView.setOnScrollChangeListener(this);

        //initializing our adapter
//        adapter = new CardAdapter(listSuperHeroes, this);

        //Adding adapter to recyclerview
        recyclerView.setAdapter(adapter);
    }

    private void showRecylerGrid(){
        layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

//        getData();
//        adapter = new CardAdapter(listSuperHeroes, this);
        recyclerView.setAdapter(adapter);
    }

    private void adminupload() {
        if (superHero.getUsername().isEmpty()){

        }
    }

}