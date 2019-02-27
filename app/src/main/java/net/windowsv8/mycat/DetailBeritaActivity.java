package net.windowsv8.mycat;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Windowsv8 on 04/05/2018.
 */

public class DetailBeritaActivity extends AppCompatActivity implements View.OnClickListener{

    String judul,isi,tanggal,tempat, nomor, tanggalkelender, formattanggal;
    TextView judulBerita, isiBerita, tanggalBerita;
    ImageView kalender, maps, telpon, whatsapp;
    com.prolificinteractive.materialcalendarview.MaterialCalendarView calendarViewgithub;
    CalendarView calendarView;
    RelativeLayout rl, rl2, rl3;
    Button back;
    Date akhir, fix;
    String nomorhp ="";
    String alamat ="";
    boolean cekalender = false;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
            startActivity(new Intent(this, BeritaActivity.class));
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_berita);

        judulBerita = findViewById(R.id.judulberita);
        isiBerita = findViewById(R.id.isideskripsi_berita);
        tanggalBerita = findViewById(R.id.tanggalberita);
        kalender = findViewById(R.id.kalenderBerita);
        maps = findViewById(R.id.mapsBerita);
        telpon = findViewById(R.id.telponBerita);
        whatsapp = findViewById(R.id.waberita);
        rl = findViewById(R.id.isiContentDetailBerita);
        rl2 = findViewById(R.id.isitanggalBerita);
        rl3 = findViewById(R.id.isiContentDetailBerita2);
        back = findViewById(R.id.buttonBack);
        calendarViewgithub = findViewById(R.id.calendargtithub);

        final Toolbar toolbar = findViewById(R.id.toolbarBeritaDetailActivity);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Detail Event");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        kalender.setOnClickListener(this);
        maps.setOnClickListener(this);
        telpon.setOnClickListener(this);
        whatsapp.setOnClickListener(this);
        back.setOnClickListener(this);

        Bundle darijudul = getIntent().getExtras();
        judul = darijudul.getString("judul");

        Bundle dariisi = getIntent().getExtras();
        isi = dariisi.getString("isi");

        Bundle daritanggal = getIntent().getExtras();
        tanggal = daritanggal.getString("tanggal");

        Bundle daritempat = getIntent().getExtras();
        tempat = daritempat.getString("tempat");

        Bundle darinomor = getIntent().getExtras();
        nomor = darinomor.getString("nomor");

        tanggalkelender = tanggal.replace("-","/");
        DateFormat formatinput = new SimpleDateFormat("dd/MM/yyyy",Locale.getDefault());
        DateFormat formatakhir = new SimpleDateFormat("dd MMMM yyyy",Locale.getDefault());
        try {
            akhir = formatinput.parse(tanggalkelender);
            formattanggal = formatakhir.format(akhir);
            Log.d("testerbulan", formattanggal);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        judulBerita.setText(judul);
        isiBerita.setText(isi);
        tanggalBerita.setText(formattanggal);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.kalenderBerita:
                kelender();
                break;

            case R.id.mapsBerita:
                alamat = tempat.toString().trim();
                googlemap();
                break;

            case R.id.telponBerita:
                nomorhp = nomor.toString().trim();
                telponintent();
                break;

            case R.id.waberita:
                nomorhp = nomor.toString().trim();
                wa(nomorhp);
                break;

            case R.id.buttonBack:
                kembali();
                break;
        }

    }

    private void kembali() {
        rl.setVisibility(View.VISIBLE);
        rl2.setVisibility(View.INVISIBLE);
        rl3.setVisibility(View.VISIBLE);
        getSupportActionBar().setTitle("Detail Event");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void wa(String nomorhp) {
        StringBuilder nomorWA = new StringBuilder(nomorhp);
        nomorWA.setCharAt(0,'6');
        nomorWA.insert(1,'2');
        Intent sendMessage = new Intent("android.intent.action.MAIN");
        sendMessage.setComponent(new ComponentName("com.whatsapp", "com.whatsapp.Conversation"));
        sendMessage.setType("text/plain");
        sendMessage.putExtra("jid", nomorWA + "@s.whatsapp.net");
//        sendMessage.putExtra(Intent.EXTRA_TEXT,"Apakah hewan ini masih tersedia ");
        startActivity(sendMessage);
    }

    private void telponintent() {
        Toast.makeText(getApplicationContext(), "telpon", Toast.LENGTH_SHORT).show();
        Intent intentCall = new Intent(Intent.ACTION_DIAL);
        intentCall.setData(Uri.parse("tel:"+nomorhp));
        startActivity(intentCall);
    }

    private void googlemap() {
        Toast.makeText(getApplicationContext(), "maps", Toast.LENGTH_SHORT).show();
        Uri addressUri = Uri.parse("geo:0,0?q=" + alamat);
        Intent intentAddress = new Intent(Intent.ACTION_VIEW, addressUri);
        intentAddress.setPackage("com.google.android.apps.maps");
        startActivity(intentAddress);
    }

    private void kelender() {
        calendarViewgithub.addDecorator(new DayViewDecorator() {
            @Override
            public boolean shouldDecorate(CalendarDay day) {
                return day.getCalendar().get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY;
            }

            @Override
            public void decorate(DayViewFacade view) {
                view.addSpan(new ForegroundColorSpan(ContextCompat.getColor(getApplicationContext(), R.color.merah)));
            }
        });
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        Date hasil;
        try {
            hasil = dateFormat.parse(tanggalkelender);
            calendarViewgithub.setDateSelected(CalendarDay.from(hasil),true);
            calendarViewgithub.setCurrentDate(CalendarDay.from(hasil));

        } catch (ParseException e) {
            e.printStackTrace();
        }

        rl2.setVisibility(View.VISIBLE);
        rl.setVisibility(View.INVISIBLE);
        rl3.setVisibility(View.INVISIBLE);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setTitle("Kalender");
        cekalender = true;
    }
}
