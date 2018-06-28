package net.simplifiedcoding.myfeed;

import android.content.ComponentName;
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
import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import static android.R.attr.bottomLeftRadius;
import static android.R.attr.name;
import static android.R.attr.textAppearanceLargePopupMenu;
import static android.R.id.message;
import static android.R.id.replaceText;
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
    BottomNavigationView bottomNavigationView;
    CollapsingToolbarLayout collapsingToolbarLayout;
    private static final String TAG = DetailActivity.class.getSimpleName();

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fjbdetail);
        final Toolbar toolbar = findViewById(R.id.toolbar);
        collapsingToolbarLayout = findViewById(R.id.collapsingToolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitleTextColor(Color.WHITE);
//        collapsingToolbarLayout.setExpandedTitleColor(Color.parseColor("#3F51B5"));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

//        collapsingToolbarLayout.setExpandedTitleMarginStart(0);
//        collapsingToolbarLayout.setExpandedTitleMarginBottom(0);
//        collapsingToolbarLayout.setExpandedTitleMarginEnd(0);

        Bundle extraimage = getIntent().getExtras();
        String image = extraimage.getString("image");
//        imageViewHeroDetail = findViewById(R.id.imageViewHeroDetail);



//        Picasso.with(context2)
//                .load(image)
//                .error(R.drawable.image)
//                .into(imageViewHeroDetail);

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
        final String name = extraname.getString("name");
        textViewNameDetail = (TextView) findViewById(R.id.textViewNameDetail);
        textViewNameDetail.setText(name);

        title = name;

        Bundle extraprice = getIntent().getExtras();
        String price = extraprice.getString("price");

        //idr
        DecimalFormat format = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols idr = new DecimalFormatSymbols();
        idr.setCurrencySymbol("Rp ");
        idr.setGroupingSeparator('.');
        format.setDecimalFormatSymbols(idr);

        textViewPublisherDetail = (TextView) findViewById(R.id.textViewPublisherDetail);
        textViewPublisherDetail.setText(format.format(Long.valueOf(price)));
//        textViewPublisherDetail.setText(price);

        Bundle extradescription = getIntent().getExtras();
        String description = extradescription.getString("description");
        textViewDeskripsiDetail = (TextView) findViewById(R.id.textviewDeskripsiDetail);
        textViewDeskripsiDetail.setText(description);

//        Bundle extralat = getIntent().getExtras();
//        final String lat = extralat.getString("lat");
//
//        Bundle extralng = getIntent().getExtras();
//        final String lng = extralng.getString("lng");

        Bundle extratelephone = getIntent().getExtras();
        final String telephone = extratelephone.getString("telephone");
        textViewTelephoneDetail = (TextView) findViewById(R.id.textviewTeleponDetail);
        textViewTelephoneDetail.setText(telephone);

        Bundle extraaddress = getIntent().getExtras();
        final String address = extraaddress.getString("address");
        textViewAddressDetail = (TextView)findViewById(R.id.textviewAlamatDetail);
        textViewAddressDetail.setText(address);

        Bundle extrausername = getIntent().getExtras();
        String username = extrausername.getString("username");
//        textViewUsernameDetail = (TextView)findViewById(R.id.textViewUsernameDetail);
//        textViewUsernameDetail.setText(username);/kj

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomnavigation);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);

//        collapsingToolbarLayout = findViewById(R.id.collapsingToolbar);
        collapsingToolbarLayout.setTitle(title);
//        collapsingToolbarLayout.setExpandedTitleGravity(100);
        collapsingToolbarLayout.setExpandedTitleColor(Color.parseColor("#ffffffff"));
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
        collapsingToolbarLayout.setContentScrimColor(getResources().getColor(R.color.colorPrimary));
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.TextSize);
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));

        Log.e(TAG,"datadetail : " + image +"\n"+ name +"\n"+ price +"\n"+ description +"\n"+ telephone +"\n"+ address +"\n"+ username);

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
                        whatsApp(telephone,name);
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
        intentAddress.setPackage("com.google.android.apps.maps");
        startActivity(intentAddress);
    }

    public void whatsApp(String number, String barang){
        StringBuilder nomorWA = new StringBuilder(number);
        nomorWA.setCharAt(0,'6');
        nomorWA.insert(1,'2');
        Intent sendMessage = new Intent("android.intent.action.MAIN");
        sendMessage.setComponent(new ComponentName("com.whatsapp", "com.whatsapp.Conversation"));
        sendMessage.setType("text/plain");
        sendMessage.putExtra("jid", nomorWA + "@s.whatsapp.net");
        sendMessage.putExtra(Intent.EXTRA_TEXT,"Apakah hewan ini masih tersedia " + barang);
        startActivity(sendMessage);
//        Intent sendMessage = new Intent(Intent.ACTION_SEND);
//        sendMessage.setType("text/plain");
//        sendMessage.putExtra(Intent.EXTRA_TEXT,"Apakah hewan ini masih tersedia " + barang);
//        sendMessage.putExtra("jid", number + "@s.whatsapp.net");
//        sendMessage.setPackage("com.whatsapp");
//        startActivity(sendMessage);
//        Uri uri = Uri.parse("smsto:" + number);
//        Intent i = new Intent(Intent.ACTION_SENDTO, uri);
////        i.putExtra(Intent.EXTRA_TEXT,"apakah hewan " + barang + " masih tersedia?");
////        i.setType("text/plain");
//        i.setPackage("com.whatsapp");
//        i.putExtra("chat", true);
//        startActivity(Intent.createChooser(i, "Share With"));
    }

}
