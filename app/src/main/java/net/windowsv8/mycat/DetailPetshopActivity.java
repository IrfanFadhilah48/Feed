package net.windowsv8.mycat;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Windowsv8 on 08/12/2017.
 */

public class DetailPetshopActivity extends AppCompatActivity {

    private static final String TAG = DetailPetshopActivity.class.getSimpleName();
    CollapsingToolbarLayout collapsingToolbarLayoutDokter;
    TextView textViewNama, textViewAlamat, textViewTelepon, textViewRating,textViewDeskripsi;
    TextView textViewJadwal;
    private ImageView imageView,imageViewMaps, imageViewTelpon;
    private String image, jambuka, jamtutup;
    private RatingBar ratingBar;
    private Float b;
    private Date date;
    private Calendar now;
    private Date dateCompareOne, dateCompareTwo;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
    public String nama, title;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, PetshopActivity.class));
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_petshop);

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

//        textViewNama = findViewById(R.id.textViewNamaDokterDetail);
        textViewAlamat = findViewById(R.id.textViewAlamatDokterDetail);
        textViewTelepon = findViewById(R.id.textViewTelepnDokterDetail);
        textViewRating = findViewById(R.id.textViewRatingDokterDetail);
        textViewDeskripsi = findViewById(R.id.textViewDeskripsiDokterDetail);
        textViewJadwal = findViewById(R.id.textViewJadwalDokterDetail);
        imageView = findViewById(R.id.imageViewCollapseDokter);
        imageViewMaps = findViewById(R.id.imageViewMapsDokterDetail);
        imageViewTelpon = findViewById(R.id.imageViewTelponDokterDetail);
        ratingBar = findViewById(R.id.ratingBarDokter);

        Bundle extraname = getIntent().getExtras();
        nama = extraname.getString("nama");
//        textViewNama.setText(nama);
        title = nama;

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

        Bundle extrabuka = getIntent().getExtras();
        jambuka = extrabuka.getString("jambuka");

        Bundle extratutup = getIntent().getExtras();
        jamtutup = extratutup.getString("jamtutup");

        Bundle extrajadwal = getIntent().getExtras();
        String jadwal = extrajadwal.getString("jadwal");
        textViewJadwal.setText(jadwal);

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
        collapsingToolbarLayoutDokter.setExpandedTitleTextAppearance(R.style.TextSize);
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

        compareTime();
    }

    private void contactPerson(String nomor) {
        Intent intentCall = new Intent(Intent.ACTION_DIAL);
        intentCall.setData(Uri.parse("tel:"+nomor));
        startActivity(intentCall);
    }

    private void intentMaps(String latitude, String longitude, String nama) {
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

        date = parseDate(hour + ":" + min);


        dateCompareOne = parseDate(jambuka);
        dateCompareTwo = parseDate(jamtutup);

        Log.d(TAG, jambuka +"\n"+jamtutup);
        Log.d(TAG,"tester" + String.valueOf(date));
        if (dateCompareOne.before(date) && dateCompareTwo.after(date)){
            Toast.makeText(this, "Buka Sekarang", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "Tutup Sekarang", Toast.LENGTH_SHORT).show();
        }

    }


    private Date parseDate(String date){
        try{
            return simpleDateFormat.parse(date);
        } catch (ParseException e) {
            return new Date(0);
        }
    }

}
