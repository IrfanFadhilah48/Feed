package net.simplifiedcoding.myfeed;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Windowsv8 on 04/05/2018.
 */

public class BeritaAdapter extends RecyclerView.Adapter<BeritaAdapter.ViewHolder> {
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.berita_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Berita berita = beritas.get(position);
        holder.textViewJudul.setText(berita.getJudul());
        holder.textViewIsi.setText(berita.getIsi());
        holder.textViewTanggal.setText(berita.getTanggal());

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
