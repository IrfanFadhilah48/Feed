package net.windowsv8.mycat;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;


public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> implements Filterable {

    private Context context;
    SuperHeroUser superHeroUser;
    ArrayList<SuperHero> superHeroes;
    ArrayList<SuperHero> mArrayList;

    public CardAdapter(ArrayList<SuperHero> superHeroes, Context context){
        super();
        this.superHeroes = superHeroes;
        this.context = context;
        mArrayList = superHeroes;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_fjb, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final SuperHero superHero =  superHeroes.get(position);

        DecimalFormat format = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols idr = new DecimalFormatSymbols();
        idr.setCurrencySymbol("Rp ");
        idr.setGroupingSeparator('.');
        format.setDecimalFormatSymbols(idr);

        holder.textViewPublisher.setText(format.format(Long.valueOf(superHero.getPublisher())));
        holder.textViewName.setText(superHero.getName());
        holder.textViewJenis.setText(superHero.getJenis());

        Glide.with(context)
                .load(superHero.getImageUrl())
                .into(holder.imageView);
        /*holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopUpMenu(holder.overflow);
            }
        });*/

//        holder.buttonViewOption.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                PopupMenu popupMenu = new PopupMenu(context, holder.buttonViewOption);
//                popupMenu.inflate(R.menu.menu_detail);
//                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                    @Override
//                    public boolean onMenuItemClick(MenuItem item) {
//                        switch (item.getItemId()){
//                            case R.id.item_bookmark:
//                                Toast.makeText(context, "Anda Menambahkan  "+superHero.getName()+"ke Favourite",Toast.LENGTH_SHORT).show();
//                                break;
//                            case R.id.item_bookmark2:
//
//                                break;
//                        }
//                        return false;
//                    }
//                });
//                popupMenu.show();
//            }
//        });



        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Anda Memilih " + superHero.getName(), Toast.LENGTH_SHORT).show();
                Intent i = new Intent(context,DetailActivity.class);
                String getId = superHero.getId();
                i.putExtra("id",getId);
                String getName = superHero.getName();
                i.putExtra("name", getName);
                String getImage = superHero.getImageUrl();
                i.putExtra("image", getImage);
                String getPrice = superHero.getPublisher();
                i.putExtra("price", getPrice);
                String getDescription = superHero.getDescription();
                i.putExtra("description", getDescription);
                String getTelephone = superHero.getTelephone();
                i.putExtra("telephone", getTelephone);
                String getAddress = superHero.getAddress();
                i.putExtra("address",getAddress);
                String getUsername = superHero.getUsername();
                i.putExtra("username",getUsername);
                String getUserName = superHero.getSuperHeroUser().getNama();
                i.putExtra("nama",getUserName);
                String getEmail = superHero.getSuperHeroUser().getEmail();
                i.putExtra("email",getEmail);

                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return superHeroes.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()){
                    superHeroes = mArrayList;
                }else {
                    ArrayList<SuperHero> filteredList = new ArrayList<>();
                    for (SuperHero superHero2 : mArrayList){
                        if (superHero2.getName().toLowerCase().contains(charString) || superHero2.getJenis().toLowerCase().contains(charString)){
//                        if (superHero2.getDescription().toLowerCase().contains(charString)){
                            filteredList.add(superHero2);
                        }
                    }
                    superHeroes = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = superHeroes;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                superHeroes = (ArrayList<SuperHero>)filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        //Views

        public ImageView imageView;
        public TextView textViewName;
        public TextView textViewPublisher;
        public TextView textViewJenis;


        //Initializing Views
        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageViewHero);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewPublisher = itemView.findViewById(R.id.textViewPublisher);
            textViewJenis = itemView.findViewById(R.id.textviewDeskripsi);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int i = getAdapterPosition();
                    Toast.makeText(context, "Anda Memilih " + superHeroes.get(i).getName(),Toast.LENGTH_SHORT).show();
                    Intent ii = new Intent(context ,DetailActivity.class);
                    ii.putExtra("id", superHeroes.get(i).getId());
                    ii.putExtra("name",superHeroes.get(i).getName());
                    ii.putExtra("image",superHeroes.get(i).getImageUrl());
                    ii.putExtra("price",superHeroes.get(i).getPublisher());
                    ii.putExtra("description",superHeroes.get(i).getDescription());
                    ii.putExtra("telephone",superHeroes.get(i).getTelephone());
                    ii.putExtra("address",superHeroes.get(i).getAddress());
                    ii.putExtra("jenis", superHeroes.get(i).getJenis());
                    ii.putExtra("username", superHeroes.get(i).getUsername());
                    ii.putExtra("nama", superHeroes.get(i).getSuperHeroUser().getNama());
                    ii.putExtra("email", superHeroes.get(i).getSuperHeroUser().getEmail());
                    context.startActivity(ii);
                }
            });
        }
    }
}