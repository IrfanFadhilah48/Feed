package net.simplifiedcoding.myfeed;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;


public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> implements Filterable {

    //MainActivity M = new MainActivity();


    private String tes2;
    //Imageloader to load image
    private ImageLoader imageLoader;
    private Context context;
    private static int currentPosition = 0;


    //List to store all superheroes
    //List<SuperHero> superHeroes;pake List
    ArrayList<SuperHero> superHeroes;
    ArrayList<SuperHero> mArrayList;

    public void update(ArrayList<SuperHero> filterdNames){
        superHeroes = filterdNames;
        notifyDataSetChanged();
    }

    //Constructor of this class
    public CardAdapter(ArrayList<SuperHero> superHeroes, Context context){
        super();
        //Getting all superheroes
        this.superHeroes = superHeroes;
        this.context = context;
        mArrayList = superHeroes;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fjb_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        //Getting the particular item from the list
        final SuperHero superHero =  superHeroes.get(position);

//        merubah text menjadi currency rupiah
//        Locale localeID = new Locale("in","ID");
//        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
//        Double harga = Double.parseDouble(superHero.getPublisher());
//        holder.textViewPublisher.setText(formatRupiah.format((double)harga));

        DecimalFormat format = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols idr = new DecimalFormatSymbols();

        idr.setCurrencySymbol("Rp ");
        idr.setGroupingSeparator('.');
        format.setDecimalFormatSymbols(idr);
        holder.textViewPublisher.setText(format.format(Long.valueOf(superHero.getPublisher())));

        //Loading image from url
        /*imageLoader = CustomVolleyRequest.getInstance(context).getImageLoader();
        imageLoader.get(superHero.getImageUrl(), ImageLoader.getImageListener(holder.imageView, R.drawable.image, android.R.drawable.ic_dialog_alert));*/

        //Showing data on the views
        /*holder.imageView.setImageUrl(superHero.getImageUrl(), imageLoader);*/
        holder.textViewName.setText(superHero.getName());
//        holder.textViewPublisher.setText(superHero.getPublisher());

        holder.textViewDeskripsi.setText(superHero.getDescription());

        //preg_replace("/[^a-zA-Z0-9]/", ""



        Picasso.with(context)
                .load(superHero.getImageUrl())
                .resize(120,60)
                .centerCrop()
                .error(R.drawable.image)
                .resize(120,60)
                .into(holder.imageView);

        /*holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopUpMenu(holder.overflow);
            }
        });*/

        holder.buttonViewOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(context, holder.buttonViewOption);
                popupMenu.inflate(R.menu.menu_detail);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.item_bookmark:
                                Toast.makeText(context, "Anda Menambahkan  "+superHero.getName()+"ke Favourite",Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.item_bookmark2:

                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });



        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Anda Memilih " + superHero.getName(), Toast.LENGTH_SHORT).show();
                //context.startActivity(new Intent (context, DetailActivity.class));

                Intent i = new Intent(context,DetailActivity.class);

                String getName = superHero.getName();
                i.putExtra("name", getName);
                String getImage = superHero.getImageUrl();
                i.putExtra("image", getImage);
                String getPrice = superHero.getPublisher();
                i.putExtra("price", getPrice);
                String getDescription = superHero.getDescription();
                i.putExtra("description", getDescription);
//                String getLat = superHero.getLat();
//                i.putExtra("lat", getLat);
//                String getLng = superHero.getLng();
//                i.putExtra("lng", getLng);
                String getTelephone = superHero.getTelephone();
                i.putExtra("telephone", getTelephone);
                String getAddress = superHero.getAddress();
                i.putExtra("address",getAddress);
                String getUsername = superHero.getUsername();
                i.putExtra("username",getUsername);

                context.startActivity(i);
            }
        });


                /*Picasso.with(context)
                .load(Server.cover + arrayList.get(position).get("image"))
                .placeholder(R.drawable.noimage)
                .fit().centerCrop()
                .error(R.drawable.nopreview)
                .into(image);*/

    }

    /*private void showPopUpMenu(View view) {
        PopupMenu popup = new PopupMenu(context,view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_detail, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
    }*/

    /*class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener{

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()){
                case R.id.item_bookmark:
                    Toast.makeText(context, "Berhasil Menambahkan Bookmark", Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;
        }
    }*/
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
                        if (superHero2.getName().toLowerCase().contains(charString)){
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
        public TextView textViewDeskripsi,buttonViewOption;


        //Initializing Views
        public ViewHolder(View itemView) {

            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageViewHero);
            textViewName = (TextView) itemView.findViewById(R.id.textViewName);
            textViewPublisher = (TextView) itemView.findViewById(R.id.textViewPublisher);
            textViewDeskripsi = (TextView) itemView.findViewById(R.id.textviewDeskripsi);
            buttonViewOption = (TextView) itemView.findViewById(R.id.textViewOptions);
            //overflow = (ImageView)itemView.findViewById(R.id.imageoverflow);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int i = getAdapterPosition();
                    Toast.makeText(context, "Anda Memilih " + superHeroes.get(i).getName(),Toast.LENGTH_SHORT).show();
                    Intent ii = new Intent(context ,DetailActivity.class);
                    ii.putExtra("name",superHeroes.get(i).getName());
                    ii.putExtra("image",superHeroes.get(i).getImageUrl());
                    ii.putExtra("price",superHeroes.get(i).getPublisher());
                    ii.putExtra("description",superHeroes.get(i).getDescription());
                    ii.putExtra("telephone",superHeroes.get(i).getTelephone());
                    ii.putExtra("address",superHeroes.get(i).getAddress());
                    ii.putExtra("username",superHeroes.get(i).getUsername());
                    context.startActivity(ii);
                }
            });
        }


    }


}