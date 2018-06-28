package net.simplifiedcoding.myfeed;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.view.CollapsibleActionView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


import static android.R.attr.noHistory;

/**
 * Created by Windowsv8 on 08/12/2017.
 */

public class DetailDokterActivity extends AppCompatActivity {

    CollapsingToolbarLayout collapsingToolbarLayoutDokter;
    TextView textViewNama, textViewAlamat, textViewTelepon, textViewRating,textViewDeskripsi, cobaTime;
    private ImageView imageView,imageViewMaps, imageViewTelpon;
    private String image;
    private RatingBar ratingBar;
    private Float b;
    private Date date,date2;
    private Calendar now;
    private Date dateCompareOne;
    private Date dateCompareTwo;
    private String timeOne = "02:00";
    private String timeTwo = "22:00";
    private String dayOne = "Sunday";
    private String dayTwo = "Tuesday";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
    SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("EEEE", Locale.getDefault());
    private Calendar calendar;
    String time;
    String title;
    public String nama;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, DokterActivity.class));
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_dokter);

        final Toolbar toolbar = findViewById(R.id.toolbarDokterDetail);
        collapsingToolbarLayoutDokter = findViewById(R.id.collapsingToolbarDokter);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

//        Log.e("kenapa", title);


        textViewNama = (TextView) findViewById(R.id.textViewNamaDokterDetail);
        textViewAlamat = (TextView) findViewById(R.id.textViewAlamatDokterDetail);
        textViewTelepon = (TextView)  findViewById(R.id.textViewTelepnDokterDetail);
        textViewRating = (TextView) findViewById(R.id.textViewRatingDokterDetail);
        textViewDeskripsi = findViewById(R.id.textViewDeskripsiDokterDetail);
        imageView = (ImageView) findViewById(R.id.imageViewCollapseDokter);
        imageViewMaps = findViewById(R.id.imageViewMapsDokterDetail);
        imageViewTelpon = findViewById(R.id.imageViewTelponDokterDetail);
        ratingBar = (RatingBar) findViewById(R.id.ratingBarDokter);


//        collapsingToolbarLayout.setTitle(name);
//        collapsingToolbarLayout.setExpandedTitleColor(Color.parseColor("#FF0000FF"));

        Bundle extraname = getIntent().getExtras();
        nama = extraname.getString("nama");
        textViewNama.setText(nama);
        title = textViewNama.getText().toString();
        Log.e("apakah dapat",title);

        Bundle extraalamat = getIntent().getExtras();
        String alamat = extraalamat.getString("alamat");
        textViewAlamat.setText(alamat);

        Bundle extratelepon = getIntent().getExtras();
        final String telepon = extratelepon.getString("telpon");
        textViewTelepon.setText(telepon);

        Bundle extradeskripsi = getIntent().getExtras();
        String deskripsi = extradeskripsi.getString("deskripsi");
        textViewDeskripsi.setText(deskripsi);

        Bundle extralatitude = getIntent().getExtras();
        final String lat = extralatitude.getString("lat");

        Bundle extralongitude = getIntent().getExtras();
        final String lng = extralongitude.getString("lng");

        Bundle extrarating = getIntent().getExtras();
        String rating = extrarating.getString("rating");
        textViewRating.setText(rating);
        b = Float.valueOf(Float.parseFloat(rating));
        ratingBar.setRating(b/2);
        ratingBar.setIsIndicator(true);

        LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(Color.parseColor("#ffd700"), PorterDuff.Mode.SRC_ATOP);

        Bundle extraimage = getIntent().getExtras();
        image = extraimage.getString("image");

        Picasso.with(this)
                .load(image)
                .centerCrop()
                .resize(220, 120)
                .error(R.drawable.ic_image_black_24dp)
                .into(imageView);

        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setSubtitleTextColor(Color.WHITE);
        collapsingToolbarLayoutDokter.setTitle(nama);
        collapsingToolbarLayoutDokter.setExpandedTitleColor(Color.WHITE);
        collapsingToolbarLayoutDokter.setCollapsedTitleTextColor(Color.WHITE);
        collapsingToolbarLayoutDokter.setContentScrimColor(getResources().getColor(R.color.colorPrimary));
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        Log.e("coba",image);

        imageViewMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intentMaps(lat,lng,nama);
            }
        });

        imageViewTelpon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contactPerson(telepon);
            }
        });

//        calendar = Calendar.getInstance();
//        simpleDateFormat1 = new SimpleDateFormat("HH:mm:ss");
//        time = simpleDateFormat1.format(calendar.getTime());
//
//        cobaTime = (TextView) findViewById(R.id.cobawaktu);
//        cobaTime.setText(time);


        compareTime();

//        dynamicToolbarColor();
//        toolbarTextApperance();
    }

    private void contactPerson(String nomor) {
        Intent intentCall = new Intent(Intent.ACTION_DIAL);
        intentCall.setData(Uri.parse("tel:"+nomor));
        startActivity(intentCall);
    }

    private void intentMaps(String latitude, String longitude, String nama) {
        String sTatic = "geo:-6.1645109,106.810026?z=18&q=JakartaVetCideng";
        String latlng = "geo:" + latitude + "," + longitude+"?z=18&q="+ nama;
        Uri uri = Uri.parse(latlng);
        Log.e("hasil",latlng);
        Intent intentAddress = new Intent(Intent.ACTION_VIEW, uri);
        intentAddress.setPackage("com.google.android.apps.maps");
        startActivity(intentAddress);
    }

    private void compareTime(){
        now = Calendar.getInstance();
        int hour = now.get(Calendar.HOUR_OF_DAY);
        int min = now.get(Calendar.MINUTE);
//        int day = now.get(Calendar.DAY_OF_WEEK);
//        String timeup = simpleDateFormat.format(calendar.getTime());

        date = parseDate(hour + ":" + min);

//        date2 = parseDate(day);
//        date2 = parseDate(timeup);
        dateCompareOne = parseDate(timeOne);
        dateCompareTwo = parseDate(timeTwo);

        if (dateCompareOne.before(date) && dateCompareTwo.after(date)){
            Toast.makeText(this, "Work  Now", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "Not Work  Now", Toast.LENGTH_SHORT).show();
        }

    }


    private Date parseDate(String date){
        try{
            return simpleDateFormat.parse(date);
        } catch (ParseException e) {
            return new Date(0);
        }
    }

//    private void toolbarTextApperance() {
//
//    }
//
//    private void dynamicToolbarColor() {
//        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.id.imageViewDokterDetail);
//        Palette.from(bmp).generate(new Palette.PaletteAsyncListener() {
//            @Override
//            public void onGenerated(Palette palette) {
//                collapsingToolbarLayout.setContentScrimColor(palette.getMutedColor(R.attr.colorPrimary));
//
//            }
//        });
//    }
}
