package Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.gozde.osmanlitapp.R;

import java.util.List;

import Adapters.UrunlerAdapter;
import Models.KategoriUrun;
import Models.Product;
import Responses.KategoriUrunResponse;
import Responses.ProductResponse;
import Utils.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentUrunler extends Fragment {

List<Product> products;
KategoriUrun urunler;
RecyclerView recyclerView;
UrunlerAdapter adapter;
ImageButton back;
    public FragmentUrunler() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment_urunler, container, false);
        back=view.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        final String title=getArguments().getString("title",null);

        int kategoriId=getArguments().getInt("id",0);
       recyclerView=view.findViewById(R.id.recycler_urunler);
        Call<KategoriUrunResponse> call = ApiClient.getInstance(getContext()).getApi().kategoriUrunler("application/json",kategoriId);
        call.enqueue(new Callback<KategoriUrunResponse>() {
            @Override
            public void onResponse(Call<KategoriUrunResponse> call, Response<KategoriUrunResponse> response) {
                products=response.body().getData().getProducts();
                adapter=new UrunlerAdapter(products);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));

            }

            @Override
            public void onFailure(Call<KategoriUrunResponse> call, Throwable t) {

            }
        });

        return view;




    }


}
