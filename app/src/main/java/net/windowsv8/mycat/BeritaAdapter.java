package net.windowsv8.mycat;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Windowsv8 on 04/05/2018.
 */

public class BeritaAdapter extends RecyclerView.Adapter<BeritaAdapter.ViewHolder> {

    String formattanggal;
    private Context context;
    ArrayList<Berita> beritas;
    public static final String TAG = BeritaAdapter.class.getSimpleName();


    public BeritaAdapter(ArrayList<Berita>beritas, Context context){
        super();
        this.beritas = beritas;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_berita, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Berita berita = beritas.get(position);
        String replace = berita.getTanggal().replace("-","/");
        holder.textViewJudul.setText(berita.getJudul());
        holder.textViewIsi.setText(berita.getIsi());
        DateFormat formatinput = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        DateFormat formatakhir = new SimpleDateFormat("dd MMMM yyyy",Locale.getDefault());
        try {
            Date akhir = formatinput.parse(replace);
            formattanggal = formatakhir.format(akhir);
            holder.textViewTanggal.setText(formattanggal);
            Log.e("format", formattanggal);
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return beritas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView textViewJudul, textViewIsi, textViewTanggal;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewJudul = itemView.findViewById(R.id.textViewJudulBerita);
            textViewIsi = itemView.findViewById(R.id.textViewIsiBerita);
            textViewTanggal = itemView.findViewById(R.id.textViewTanggalBerita);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int i = getAdapterPosition();
                    Intent ii = new Intent(context, DetailBeritaActivity.class);
                    Toast.makeText(context, beritas.get(i).getJudul(),Toast.LENGTH_SHORT).show();
                    ii.putExtra("judul",beritas.get(i).getJudul());
                    ii.putExtra("isi", beritas.get(i).getIsi());
                    ii.putExtra("tanggal", beritas.get(i).getTanggal());
                    ii.putExtra("tempat", beritas.get(i).getTempat());
                    ii.putExtra("nomor", beritas.get(i).getContact());
                    context.startActivity(ii);
                }
            });
        }
    }
}
