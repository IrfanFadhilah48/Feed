package net.simplifiedcoding.myfeed;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Windowsv8 on 04/05/2018.
 */

public class DetailBeritaActivity extends AppCompatActivity{

    String judul,isi,tanggal,tempat, nomor, tanggalkelender;
    TextView judulBerita, isiBerita, tanggalBerita;
    ImageView kalender, maps, telpon, whatsapp;
    CalendarView calendarView;
    RelativeLayout rl, rl2, rl3;
    Button back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_berita);

        judulBerita = findViewById(R.id.judulberita);
        isiBerita = findViewById(R.id.isideskripsi_berita);
        tanggalBerita = findViewById(R.id.tanggalberita);
        kalender = findViewById(R.id.kalenderBerita);
        calendarView = findViewById(R.id.calenderView);
        rl = findViewById(R.id.isiContentDetailBerita);
        rl2 = findViewById(R.id.isitanggalBerita);
        rl3 = findViewById(R.id.isiContentDetailBerita2);
        back = findViewById(R.id.buttonBack);

//        calendarView.setVisibility(View.INVISIBLE);
//        back.setVisibility(View.INVISIBLE);

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

//        isi = getIntent().getExtras().getString("isi");
//        tanggal = getIntent().getExtras().getString("tanggal");

        judulBerita.setText(judul);
        isiBerita.setText(isi);
        tanggalBerita.setText(tanggal);

        Log.e("hasil",tempat +  nomor);
        kalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String date = "16/06/2018";
                try {
                    calendarView.setDate(new SimpleDateFormat("dd/MM/yyyy").parse(tanggalkelender).getTime(),true,true);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                rl2.setVisibility(View.VISIBLE);
                rl.setVisibility(View.INVISIBLE);
                rl3.setVisibility(View.INVISIBLE);
//
//                Bundle bundle = new Bundle();
//                bundle.putString("data",date);
//                FragmentCalendar fragmentCalendar = new FragmentCalendar();
//                fragmentCalendar.setArguments(bundle);
//                getSupportFragmentManager()
//                        .beginTransaction()
//                        .add(R.id.framelayout, fragmentCalendar)
//                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
//                        .commit();
//
//                rl.setVisibility(View.INVISIBLE);


//                Date date1;
//                String date = "16/06/2018";
//                CalendarView calendarView = new CalendarView(DetailBeritaActivity.this);
//                Toast.makeText(getApplicationContext(), "berhasil", Toast.LENGTH_SHORT).show();
//                try {
////                    calendarView.setDate(new SimpleDateFormat("dd/MM/yyyy").parse(date).getTime(),true,true);
//                    date1 = new SimpleDateFormat("dd/MM/yyyy").parse(date);
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//                calendarView.setDate(date1.getTime());
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rl.setVisibility(View.VISIBLE);
                rl2.setVisibility(View.INVISIBLE);
                rl3.setVisibility(View.VISIBLE);
            }
        });
    }

}
