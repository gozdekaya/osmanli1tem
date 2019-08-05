package Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.gozde.osmanlitapp.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutionException;

import Fragments.FragmentUrunDetay;
import Models.Media;
import Models.Product;

public class MainHorzAdapter extends RecyclerView.Adapter<MainHorzAdapter.ViewHolder> {
    private List<Media> mediaList;
    List<Product> products;
    private LayoutInflater inflater;
    DisplayMetrics displayMetrics;
    Context mContext;
    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.displayMetrics =  recyclerView.getResources().getDisplayMetrics();

    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    public MainHorzAdapter(List<Media> mediaList, Context context,List<Product> products) {
        this.mediaList = mediaList;
        this.products=products;

       this.mediaList.add(new Media("http://api.osmanli.app-xr.com/storage/osm-m3u8/index.m3u8", 2));
        this.inflater = LayoutInflater.from(context);
        this.mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.layout_likebuttonmainpage, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MainHorzAdapter.ViewHolder viewHolder, int i) {
        Product myPrduct=products.get(i);
        Media myMedia = mediaList.get(i);
        try {
            viewHolder.setData(myMedia, i);
        } catch (IOException e) {
            e.printStackTrace();
        }
      /*  viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentUrunDetay fragmentUrunDetay=new FragmentUrunDetay();
                Bundle args=new Bundle();
                String string=myPrduct.getId();
                args.putString("ID",string);
                fragmentUrunDetay.setArguments(args);
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.container,fragmentUrunDetay).addToBackStack(null).commit();
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return mediaList.size();
    }

    @Override
    public void onViewAttachedToWindow(@NonNull ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        VideoView v = holder.itemView.findViewById(R.id.videoView);
        if (v != null)
            v.start();
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        VideoView v = holder.itemView.findViewById(R.id.videoView);
        if (v != null)
            v.pause();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        VideoView videoView;
        FrameLayout placeholder;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            videoView = itemView.findViewById(R.id.videoView);
            placeholder = itemView.findViewById(R.id.placeholder);
        }

        public void setData(Media selectedMedia, int position) throws IOException {
            if (selectedMedia.getType() == 2) {
                videoView.setVisibility(View.VISIBLE);
                Uri uri = Uri.parse(selectedMedia.getUrl());
                //     Uri uri = Uri.parse("http://184.72.239.149/vod/smil:BigBuckBunny.smil/playlist.m3u8");
                videoView.setVideoURI(uri);
                videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        placeholder.setVisibility(View.INVISIBLE);

                    }
                });
                //videoView.start();
            } else {
                placeholder.setVisibility(View.INVISIBLE);
                imageView.setVisibility(View.VISIBLE);



                Glide.with(mContext).load(selectedMedia.getUrl()).into(imageView);
              //  new DownLoadImageTask(imageView).execute(selectedMedia.getUrl());

                //Picasso.get().load(Uri.parse(selectedMedia.getUrl())).into(imageView);
/*
                imageView.getLayoutParams().height =displayMetrics.widthPixels;
                imageView.getLayoutParams().width = displayMetrics.widthPixels;
                imageView.requestLayout();*/
                Log.d("asd",String.valueOf(imageView.getLayoutParams().height));
                Log.d("asd_1", String.valueOf(imageView.getLayoutParams().width));
            }
        }
    }

    private class DownLoadImageTask extends AsyncTask<String,Void,Bitmap> {
        ImageView imageView;

        public DownLoadImageTask(ImageView imageView){
            this.imageView = imageView;
        }

        /*
            doInBackground(Params... params)
                Override this method to perform a computation on a background thread.
         */
        protected Bitmap doInBackground(String...urls){
            String urlOfImage = urls[0];
            Bitmap logo = null;
            try{
                InputStream is = new URL(urlOfImage).openStream();
                /*
                    decodeStream(InputStream is)
                        Decode an input stream into a bitmap.
                 */
                logo = BitmapFactory.decodeStream(is);
            }catch(Exception e){ // Catch the download exception
                e.printStackTrace();
            }
            return logo;
        }

        /*
            onPostExecute(Result result)
                Runs on the UI thread after doInBackground(Params...).
         */
        protected void onPostExecute(Bitmap result){
            imageView.setImageBitmap(result);
        }
    }

}

