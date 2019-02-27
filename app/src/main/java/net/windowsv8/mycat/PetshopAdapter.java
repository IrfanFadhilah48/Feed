package net.windowsv8.mycat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Windowsv8 on 07/12/2017.
 */

public class PetshopAdapter extends RecyclerView.Adapter<PetshopAdapter.ViewHolder> {

    private Context context;
    List<Petshop> dokters;
    private Date date;
    private Calendar now;
    private Date dateCompareOne;
    private Date dateCompareTwo;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
    private final static String TAG = PetshopAdapter.class.getSimpleName();


     public PetshopAdapter(List<Petshop> dokters, Context context){
         super();
         this.dokters = dokters;
         this.context = context;
     }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_petshop, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Petshop dokter = dokters.get(position);
        holder.textViewNama.setText(dokter.getNama());
        holder.textViewAlamat.setText(dokter.getAlamat());
        holder.textViewTelepon.setText(dokter.getTelpon());
        holder.textViewJarak.setText(dokter.getJarak() + "Km");


        now = Calendar.getInstance();
        int hour = now.get(Calendar.HOUR_OF_DAY);
        int min = now.get(Calendar.MINUTE);

        date = parseDate(hour + ":" + min);
        dateCompareOne = parseDate(dokter.getJambuka());
        dateCompareTwo = parseDate(dokter.getJamtutup());

        if (dateCompareOne.before(date) && dateCompareTwo.after(date)){
            holder.textViewJamKondisi.setText("BUKA");
            holder.textViewJamKondisi.setTextColor(Color.parseColor("#FF00FF00"));
        }else {
            holder.textViewJamKondisi.setText("TUTUP");
            holder.textViewJamKondisi.setTextColor(Color.parseColor("#FFFF0000"));
//            holder.imageView.getBackground().setAlpha(40);
//            holder.textViewNama.getBackground().setAlpha(40);
//            holder.textViewAlamat.getBackground().setAlpha(40);
//            holder.textViewTelepon.getBackground().setAlpha(40);
//            holder.textViewJarak.getBackground().setAlpha(40);
            holder.itemView.getBackground().setAlpha(95);
        }


        Picasso.with(context)
                .load(dokter.getImageUrlpetshop())
                .error(R.drawable.ic_image_black_24dp)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return dokters.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView textViewNama;
        private TextView textViewAlamat;
        private TextView textViewTelepon;
        private TextView textViewJamKondisi;
        private TextView textViewJarak;
        private TextView textViewDeskripsi;
        private TextView textViewJadwal;


        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageViewDokter);
            textViewJamKondisi = (TextView) itemView.findViewById(R.id.textViewDokterJamKondisi);
            textViewNama = (TextView) itemView.findViewById(R.id.textViewNameDokter);
            textViewAlamat = (TextView) itemView.findViewById(R.id.textViewAlamatDokter);
            textViewTelepon = (TextView) itemView.findViewById(R.id.textViewTeleponDokter);
//            textViewJadwal  =itemView.findViewById(R.id.textViewJadwalDokterDetail);
            textViewJarak = itemView.findViewById(R.id.jarak);
//            textViewDeskripsi = itemView.findViewById(R.id.textViewDeskripsiDokterDetail);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int i = getAdapterPosition();
                    Intent ii = new Intent(context, DetailPetshopActivity.class);
                    ii.putExtra("image",dokters.get(i).getImageUrlpetshop());
                    ii.putExtra("nama",dokters.get(i).getNama());
                    ii.putExtra("alamat",dokters.get(i).getAlamat());
                    ii.putExtra("telpon",dokters.get(i).getTelpon());
                    ii.putExtra("rating",dokters.get(i).getRating());
                    ii.putExtra("jambuka",dokters.get(i).getJambuka());
                    ii.putExtra("jamtutup",dokters.get(i).getJamtutup());
                    ii.putExtra("deskripsi", dokters.get(i).getDeskripsi());
                    ii.putExtra("lat",dokters.get(i).getLatitude());
                    ii.putExtra("lng",dokters.get(i).getLongitude());
                    ii.putExtra("jadwal",dokters.get(i).getJadwal());
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
