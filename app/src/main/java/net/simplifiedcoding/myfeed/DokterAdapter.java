package net.simplifiedcoding.myfeed;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Windowsv8 on 07/12/2017.
 */

public class DokterAdapter extends RecyclerView.Adapter<DokterAdapter.ViewHolder> {

    private Context context;
    List<Dokter> dokters;
    private Date date;
    private Calendar now;
    private Date dateCompareOne;
    private Date dateCompareTwo;
    private String timeOne = "3:00";
    private String timeTwo = "22:00";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
    SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("HH:mm");
    private Calendar calendar;
    private final static String TAG = DokterAdapter.class.getSimpleName();


     public DokterAdapter(List<Dokter> dokters, Context context){
         super();
         this.dokters = dokters;
         this.context = context;
     }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dokter_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Dokter dokter = dokters.get(position);
        holder.textViewNama.setText(dokter.getNama());
        holder.textViewAlamat.setText(dokter.getAlamat());
        holder.textViewTelepon.setText(dokter.getTelpon());
//        holder.textViewJam.setText("Buka " + dokter.getJambuka() + "-" +dokter.getJamtutup());
        holder.textViewJarak.setText(dokter.getJarak() + "Km");
//        holder.textViewDeskripsi.setText(dokter.getDeskripsi());


        //membuat jambuka dan jamtutup
        now = Calendar.getInstance();
        int hour = now.get(Calendar.HOUR_OF_DAY);
        int min = now.get(Calendar.MINUTE);
//        String timeup = simpleDateFormat.format(calendar.getTime());

        date = parseDate(hour + ":" + min);
//        date = parseDate(timeup);
        dateCompareOne = parseDate(dokter.getJambuka());
        dateCompareTwo = parseDate(dokter.getJamtutup());

        if (dateCompareOne.before(date) && dateCompareTwo.after(date)){
            holder.textViewJamKondisi.setText("BUKA");
            holder.textViewJamKondisi.setTextColor(Color.parseColor("#FF00FF00"));
        }else {
            holder.textViewJamKondisi.setText("TUTUP");
            holder.textViewJamKondisi.setTextColor(Color.parseColor("#FFFF0000"));
        }


        Picasso.with(context)
                .load(dokter.getImageUrlDokter())
                .resize(220,220)
                .centerCrop()
                .error(R.drawable.ic_image_black_24dp)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return dokters.size();
//        Log.e(TAG, "size : " +String.valueOf(dokters.size()));
//        return (dokters != null) ? dokters.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView textViewNama;
        private TextView textViewAlamat;
        private TextView textViewTelepon;
//        private TextView textViewJam;
        private TextView textViewJamKondisi;
        private TextView textViewJarak;
        private TextView textViewDeskripsi;


        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageViewDokter);
            textViewJamKondisi = (TextView) itemView.findViewById(R.id.textViewDokterJamKondisi);
            textViewNama = (TextView) itemView.findViewById(R.id.textViewNameDokter);
            textViewAlamat = (TextView) itemView.findViewById(R.id.textViewAlamatDokter);
            textViewTelepon = (TextView) itemView.findViewById(R.id.textViewTeleponDokter);
//            textViewJam = (TextView) itemView.findViewById(R.id.textViewJamDokter);
            textViewJarak = itemView.findViewById(R.id.jarak);
//            textViewDeskripsi = itemView.findViewById(R.id.textViewDeskripsiDokterDetail);



            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int i = getAdapterPosition();
                    Intent ii = new Intent(context, DetailDokterActivity.class);
                    ii.putExtra("image",dokters.get(i).getImageUrlDokter());
                    ii.putExtra("nama",dokters.get(i).getNama());
                    ii.putExtra("alamat",dokters.get(i).getAlamat());
                    ii.putExtra("telpon",dokters.get(i).getTelpon());
                    ii.putExtra("rating",dokters.get(i).getRating());
                    ii.putExtra("jambuka",dokters.get(i).getJambuka());
                    ii.putExtra("jamtutup",dokters.get(i).getJamtutup());
                    ii.putExtra("deskripsi", dokters.get(i).getDeskripsi());
                    ii.putExtra("lat",dokters.get(i).getLatitude());
                    ii.putExtra("lng",dokters.get(i).getLongitude());
                    context.startActivity(ii);
                }
            });
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
