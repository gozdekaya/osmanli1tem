package Adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gozde.osmanlitapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import Fragments.FragmentKategori;
import Fragments.FragmentUrunler;
import Models.Categorie;

public class SubKategoriAdapter extends RecyclerView.Adapter<SubKategoriAdapter.ViewHolder>{

    List<Categorie> mCategories;
    Context mContext;

    public SubKategoriAdapter(List<Categorie> mCategories,Context mContext) {
        this.mCategories = mCategories;
        this.mContext=mContext;
    }

    @NonNull
    @Override
    public SubKategoriAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.kategori_content_cardview,viewGroup,false);
        return new SubKategoriAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubKategoriAdapter.ViewHolder viewHolder, int i) {
        Categorie categorie = mCategories.get(i);

            viewHolder.sub_title.setText(categorie.getTitle());
        Glide.with(mContext).load(categorie.getMedia()).into(viewHolder.sub_image);
           // Picasso.get().load(categorie.getMedia()).into(viewHolder.sub_image);

            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (categorie.getSubCategories().size() > 0){
                        FragmentKategori fragmentKategori = new FragmentKategori();
                        Bundle args=new Bundle();
                        int id = categorie.getId();
                        args.putInt("id",id);
                        args.putString("title",mCategories.get(i).getTitle());
                        fragmentKategori.setArguments(args);
                        AppCompatActivity activity = (AppCompatActivity) v.getContext();
                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, fragmentKategori).addToBackStack(null).commit();
                    }else {
                        FragmentUrunler fragmentUrunler = new FragmentUrunler();
                        Bundle args1=new Bundle();
                        int id = categorie.getId();
                        args1.putInt("id",id);
                        fragmentUrunler.setArguments(args1);
                        AppCompatActivity activity = (AppCompatActivity) v.getContext();
                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, fragmentUrunler).addToBackStack(null).commit();
                    }
                }
            });
    }

    @Override
    public int getItemCount() {
        return mCategories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView sub_image;
        TextView sub_title;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            sub_image=itemView.findViewById(R.id.product_image);
            sub_title=itemView.findViewById(R.id.product_title);        }
    }
}
