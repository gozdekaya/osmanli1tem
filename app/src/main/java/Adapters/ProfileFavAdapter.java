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

import java.util.ArrayList;
import java.util.List;

import Fragments.FragmentUrunDetay;
import Models.Favori;
import Models.Product;
import Utils.RoundedCornersTransformation;

public class ProfileFavAdapter extends RecyclerView.Adapter<ProfileFavAdapter.ViewHolder>{
    List<Favori> products = new ArrayList<>();
Context mContetx;
    public ProfileFavAdapter(List<Favori> products,Context mContetx) {
        this.products = products;
        this.mContetx=mContetx;
    }

    @NonNull
    @Override
    public ProfileFavAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View v = inflater.inflate(R.layout.profile_fav_cardview, viewGroup, false);

        return new ProfileFavAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileFavAdapter.ViewHolder viewHolder, int i) {
       final Product product=products.get(i).getProduct();
       viewHolder.image.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               FragmentUrunDetay fragmentUrunDetay = new FragmentUrunDetay();
               Bundle args=new Bundle();
               String string=product.getId();
               args.putString("ID",string);
               fragmentUrunDetay.setArguments(args);
               AppCompatActivity activity = (AppCompatActivity) v.getContext();
               activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, fragmentUrunDetay).addToBackStack(null).commit();
           }
       });
        viewHolder.tvname.setText(product.getTitle());
        viewHolder.tvprice.setText(product.getPrice());
        Glide.with(mContetx).load(product.getProfile_image())
                .transform(new RoundedCornersTransformation(50, 10))
                .into(viewHolder.image);
       // Picasso.get().load(product.getProfile_image()).into(viewHolder.image);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView tvname,tvprice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.img);
            tvname=itemView.findViewById(R.id.name);
            tvprice=itemView.findViewById(R.id.price);
        }
    }
}
