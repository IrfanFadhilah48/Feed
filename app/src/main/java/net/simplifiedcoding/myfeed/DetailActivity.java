package net.simplifiedcoding.myfeed;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import static android.R.attr.bottomLeftRadius;
import static android.R.attr.name;
import static android.R.attr.textAppearanceLargePopupMenu;
import static android.R.id.message;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;


/**
 * Created by Windowsv8 on 24/08/2017.
 */

public class DetailActivity extends AppCompatActivity {

    String numberTelephone = "";
    String AddressAlamat = "";
    ImageView imageViewHeroDetail,imageViewCollapse;
    private ImageView imageViewDetailMaps, imageViewDetailTelepone, imageViewDetailSMS, imageViewDetailWhatsapp;
    TextView textViewNameDetail, textViewPublisherDetail, textViewDeskripsiDetail, textViewTelephoneDetail;
    TextView textViewLocationLat, textViewLocationLng, textViewAddressDetail, textViewUsernameDetail;
    Context context2;
    String title;
    AppCompatButton buttonMaps, buttonTelephone, buttonSms;
    BottomNavigationView bottomNavigationView;
    CollapsingToolbarLayout collapsingToolbarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fjbdetail);
        AppBarLayout appBarLayout = findViewById(R.id.appbar);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        collapsingToolbarLayout = findViewById(R.id.collapsingToolbar);
        collapsingToolbarLayout.setTitle(title);
        collapsingToolbarLayout.setExpandedTitleMarginStart(0);
        collapsingToolbarLayout.setExpandedTitleMarginBottom(0);
        collapsingToolbarLayout.setExpandedTitleGravity(200);

        Bundle extraimage = getIntent().getExtras();
        String image = extraimage.getString("image");
        imageViewHeroDetail = findViewById(R.id.imageViewHeroDetail);



        Picasso.with(context2)
                .load(image)
                .error(R.drawable.image)
                .into(imageViewHeroDetail);

        imageViewCollapse = findViewById(R.id.imageViewCollapse);
        Picasso.with(context2)
                .load(image)
                .error(R.drawable.image)
                .into(imageViewCollapse);

        /*Bundle extraname = getIntent().getExtras();
        String name = extraname.getString("name");
        textViewNameDetail = (TextView)findViewById(R.id.textViewPublisherDetail);
        textViewNameDetail.setText(name);*/

        Bundle extraname = getIntent().getExtras();
        String name = extraname.getString("name");
        textViewNameDetail = (TextView) findViewById(R.id.textViewNameDetail);
        textViewNameDetail.setText(name);

        title = name;

        Bundle extraprice = getIntent().getExtras();
        String price = extraprice.getString("price");
        textViewPublisherDetail = (TextView) findViewById(R.id.textViewPublisherDetail);
        textViewPublisherDetail.setText(price);

        Bundle extradescription = getIntent().getExtras();
        String description = extradescription.getString("description");
        textViewDeskripsiDetail = (TextView) findViewById(R.id.textviewDeskripsiDetail);
        textViewDeskripsiDetail.setText(description);

        Bundle extralat = getIntent().getExtras();
        final String lat = extralat.getString("lat");

        Bundle extralng = getIntent().getExtras();
        final String lng = extralng.getString("lng");

        Bundle extratelephone = getIntent().getExtras();
        final String telephone = extratelephone.getString("telephone");
        textViewTelephoneDetail = (TextView) findViewById(R.id.textviewTeleponDetail);
        textViewTelephoneDetail.setText(telephone);

        final Bundle extraaddress = getIntent().getExtras();
        final String address = extraaddress.getString("address");
        textViewAddressDetail = (TextView)findViewById(R.id.textviewAlamatDetail);
        textViewAddressDetail.setText(address);

        Bundle extrausername = getIntent().getExtras();
        String username = extrausername.getString("username");
        textViewUsernameDetail = (TextView)findViewById(R.id.textViewUsernameDetail);
        textViewUsernameDetail.setText(username);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomnavigation);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);

        collapsingToolbarLayout = findViewById(R.id.collapsingToolbar);
        collapsingToolbarLayout.setTitle(title);
        collapsingToolbarLayout.setExpandedTitleGravity(100);
        collapsingToolbarLayout.setExpandedTitleColor(Color.parseColor("#ffffffff"));

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.telepon:
                        numberTelephone = telephone.toString().trim();
                        call();
                        break;

                    case R.id.sms:
                        numberTelephone = telephone.toString().trim();
                        sms();
                        break;

                    case R.id.maps1:
                        AddressAlamat = address.toString().trim();
                        maps();
                        break;

                    case R.id.whatsapp:
                        Toast.makeText(getApplicationContext(),"Berhasil",Toast.LENGTH_SHORT).show();
                        break;
                }
                return false;
            }
        });


//        imageViewDetailMaps.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                Intent ii = new Intent(DetailActivity.this, MapsActivity.class);
////                ii.putExtra("lat", lat);
////                ii.putExtra("lng", lng);
////                startActivity(ii);
//                AddressAlamat = address.toString().trim();
//                maps();
//            }
//        });
//        imageViewDetailTelepone.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                numberTelephone = telephone.toString().trim();
//                call();
//            }
//        });
//
//        imageViewDetailSMS.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                numberTelephone = telephone.toString().trim();
//                sms();
//            }
//        });
//
//        imageViewDetailWhatsapp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                whatsApp();
//            }
//        });
     }

    private void sms() {
        Intent intentSms = new Intent(Intent.ACTION_VIEW);
        intentSms.setData(Uri.parse("sms:"+numberTelephone));
        intentSms.putExtra("sms_body",message);
        startActivity(intentSms);
    }

    private void call() {
        Intent intentCall = new Intent(Intent.ACTION_DIAL);
        intentCall.setData(Uri.parse("tel:"+numberTelephone));
        startActivity(intentCall);
    }

    private void maps(){
        Uri addressUri = Uri.parse("geo:0,0?q=" + AddressAlamat);
        Intent intentAddress = new Intent(Intent.ACTION_VIEW, addressUri);
        startActivity(intentAddress);
    }

    public void whatsApp(){
        Toast.makeText(this, "Tester", Toast.LENGTH_SHORT).show();
    }

}
