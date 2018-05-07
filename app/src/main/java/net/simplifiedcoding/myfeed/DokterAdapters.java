package net.simplifiedcoding.myfeed;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Windowsv8 on 26/04/2018.
 */

public class DokterAdapters extends RecyclerView.Adapter<DokterAdapters.ViewHolder> {

    private Context context;
    private ArrayList<TesDokter> dokterArrayList;
    private Date date;
    private Calendar now;
    private Date dateCompareOne;
    private Date dateCompareTwo;
    private String timeOne = "3:00";
    private String timeTwo = "22:00";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
    SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("HH:mm");
    private Calendar calendar;

    public DokterAdapters(ArrayList<TesDokter>dokters, Context context){
        super();
        this.dokterArrayList = dokters;
        this.context = context;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dokter_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TesDokter tesDokter = dokterArrayList.get(position);

        holder.nama.setText(tesDokter.getNama());
        holder.alamat.setText(tesDokter.getAlamat());
        holder.telepon.setText(tesDokter.getTelpon());
        holder.jam.setText(tesDokter.getJambuka() + "-" + tesDokter.getJamtutup());

        Picasso.with(context)
                .load(tesDokter.getImage())
                .error(R.drawable.ic_image_black_24dp)
                .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return dokterArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView nama;
        TextView alamat;
        TextView telepon;
        TextView jam;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageViewDokter);
            nama = itemView.findViewById(R.id.textViewNameDokter);
            alamat = itemView.findViewById(R.id.textViewAlamatDokter);
            telepon = itemView.findViewById(R.id.textViewTeleponDokter);
//            jam = itemView.findViewById(R.id.textViewJamDokter);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int i = getAdapterPosition();
                    Intent ii = new Intent(context, DetailDokterActivity.class);
                    ii.putExtra("image", dokterArrayList.get(i).getImage());
                    ii.putExtra("nama", dokterArrayList.get(i).getNama());
                    ii.putExtra("alamat", dokterArrayList.get(i).getAlamat());
                    ii.putExtra("telpon", dokterArrayList.get(i).getTelpon());
                    ii.putExtra("jambuka", dokterArrayList.get(i).getJambuka());
                    ii.putExtra("jamtutup", dokterArrayList.get(i).getJamtutup());
                    ii.putExtra("rating",dokterArrayList.get(i).getRating());
                    context.startActivity(ii);
                }
            });
        }

    }
}
