package Adapters;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gozde.osmanlitapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import Fragments.FragmentUrunDetay;
import Models.Product;

public class UrunlerAdapter extends RecyclerView.Adapter<UrunlerAdapter.ViewHolder> {
    List<Product> mProducts;

    public UrunlerAdapter(List<Product> mProducts) {
        this.mProducts = mProducts;
    }

    @NonNull
    @Override
    public UrunlerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.urunler_cardview,viewGroup,false);
        return new UrunlerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UrunlerAdapter.ViewHolder viewHolder, int i) {
      Product product = mProducts.get(i);
      viewHolder.name.setText(product.getTitle());
      viewHolder.price.setText(product.getPrice());
      viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              FragmentUrunDetay fragmentUrunDetay=new FragmentUrunDetay();
              Bundle args=new Bundle();
              String string=mProducts.get(i).getId();
              args.putString("ID",string);
              fragmentUrunDetay.setArguments(args);
              AppCompatActivity activity = (AppCompatActivity) v.getContext();
              activity.getSupportFragmentManager().beginTransaction().replace(R.id.container,fragmentUrunDetay).addToBackStack(null).commit();
          }
      });
        Picasso.get().load(product.getProfile_image()).into(viewHolder.image);
    }

    @Override
    public int getItemCount() {
        return mProducts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name,price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.image);
            name=itemView.findViewById(R.id.name);
            price=itemView.findViewById(R.id.price);
        }
    }
}
