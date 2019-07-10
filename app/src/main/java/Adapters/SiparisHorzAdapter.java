package Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.gozde.osmanlitapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import Models.Item;
import Models.Product;

public class SiparisHorzAdapter extends RecyclerView.Adapter<SiparisHorzAdapter.ViewHolder> {
    List<Item> items;
    Context mContext;

    public SiparisHorzAdapter(List<Item> items,Context mContext) {
        this.items = items;
        this.mContext=mContext;
    }

    @NonNull
    @Override
    public SiparisHorzAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.siparis_recycler_cardview, viewGroup, false);
        return new SiparisHorzAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SiparisHorzAdapter.ViewHolder viewHolder, int i) {
        Glide.with(mContext).load(items.get(i).getProduct().getProfile_image()).into(viewHolder.imagepro);
      //  Picasso.get().load(items.get(i).getProduct().getProfile_image()).into(viewHolder.imagepro);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imagepro;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imagepro=itemView.findViewById(R.id.image_order);
        }
    }
}
