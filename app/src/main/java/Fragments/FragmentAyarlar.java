package Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gozde.osmanlitapp.R;
import com.gozde.osmanlitapp.SharedPrefManager;
import com.squareup.picasso.Picasso;
import com.yarolegovich.discretescrollview.InfiniteScrollAdapter;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;

import java.util.List;

import Adapters.ExploreAdapter;
import Adapters.ProfileFavAdapter;
import Adapters.SiparisAdapter;
import Models.Favori;
import Models.Product;
import Models.Siparis;
import Models.UserProfile;
import Responses.FavoriResponse;
import Responses.ProductResponse;
import Responses.SiparisResponse;
import Responses.UserProfileResponse;
import Utils.ApiClient;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentAyarlar extends Fragment {
ImageView settings,more;
CircleImageView imageuser;
LinearLayout layoutlin;
TextView pronum,favoriler,sipnum,siparislerim,username;
    private List<Favori> products;
    ProfileFavAdapter adapter;
RecyclerView recyclerfav,recyclersip;
    FragmentActivity fragmentActivity;
LinearLayout linsip,linfav;
List<Siparis> orders;
SiparisAdapter adapters;
Context mContext;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    public FragmentAyarlar() {
        // Required empty public constructor
    }
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ayarlar, container, false);
        username=view.findViewById(R.id.username);
        sipnum=view.findViewById(R.id.sipnum);
        pronum=view.findViewById(R.id.pronum);
        favoriler=view.findViewById(R.id.favoriler);
        imageuser=view.findViewById(R.id.imageuser);
        recyclersip=view.findViewById(R.id.recyclersip);
        settings=view.findViewById(R.id.settings);
        recyclerfav=view.findViewById(R.id.recyclerfav);

        pronum.setTextColor(getResources().getColor(R.color.blue));
        favoriler.setTextColor(getResources().getColor(R.color.blue));

        linfav=view.findViewById(R.id.linfav);
        linsip=view.findViewById(R.id.linsip);
         layoutlin=view.findViewById(R.id.linmain);
       layoutlin.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard(v);
                return false;
            }
        });
        linfav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pronum.setTextColor(getResources().getColor(R.color.blue));
                favoriler.setTextColor(getResources().getColor(R.color.blue));
                sipnum.setTextColor(getResources().getColor(R.color.grey));
                siparislerim.setTextColor(getResources().getColor(R.color.grey));
                recyclersip.setVisibility(View.GONE);
                recyclerfav.setVisibility(View.VISIBLE);
            }
        });

        linsip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pronum.setTextColor(getResources().getColor(R.color.grey));
                favoriler.setTextColor(getResources().getColor(R.color.grey));
                sipnum.setTextColor(getResources().getColor(R.color.blue));
                siparislerim.setTextColor(getResources().getColor(R.color.blue));
                recyclersip.setVisibility(View.VISIBLE);
                recyclerfav.setVisibility(View.GONE);
            }
        });
        siparislerim=view.findViewById(R.id.siparislerim);
        more=view.findViewById(R.id.more);
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragmentMore dialogFragmentMore = new DialogFragmentMore();
                dialogFragmentMore.show(getFragmentManager(),"DialogMore");
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.container,new FragmentProfile()).commit();
            }
        });


Call<UserProfileResponse> responseCall=ApiClient.getInstance(getContext()).getApi().userprofile("Bearer " + SharedPrefManager.getInstance(getContext()).getToken(),"application/json");
responseCall.enqueue(new Callback<UserProfileResponse>() {
    @Override
    public void onResponse(Call<UserProfileResponse> call, Response<UserProfileResponse> response) {
        UserProfile userProfile=response.body().getData();
        username.setText(userProfile.getName());
        Glide.with(mContext).load(userProfile.getPicture()).into(imageuser);
      //  Picasso.get().load(userProfile.getPicture()).into(imageuser);
    }

    @Override
    public void onFailure(Call<UserProfileResponse> call, Throwable t) {

    }
});
        Call<SiparisResponse> call1=ApiClient.getInstance(getContext()).getApi().orders("Bearer " + SharedPrefManager.getInstance(getContext()).getToken(), "application/json");
        call1.enqueue(new Callback<SiparisResponse>() {
            @Override
            public void onResponse(Call<SiparisResponse> call1, Response<SiparisResponse> response) {
                orders=response.body().getData();
                LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
                recyclersip.setLayoutManager(layoutManager);
                adapters=new SiparisAdapter(orders, getContext());
                recyclersip.setAdapter(adapters);
            }

            @Override
            public void onFailure(Call<SiparisResponse> call, Throwable t) {

            }
        });



        String bearer= SharedPrefManager.getInstance(getContext()).getToken();
        Call<FavoriResponse> call= ApiClient.getInstance(getContext()).getApi().favoriler("Bearer " + bearer,"application/json");
        call.enqueue(new Callback<FavoriResponse>() {
            @Override
            public void onResponse(Call<FavoriResponse> call, Response<FavoriResponse> response) {


                products=response.body().getData();
                adapter=new ProfileFavAdapter(products,mContext);
                recyclerfav.setAdapter(adapter);
                recyclerfav.setLayoutManager(new GridLayoutManager(getContext(),2));


            }

            @Override
            public void onFailure(Call<FavoriResponse> call, Throwable t) {
                Log.d("failure",t.getMessage());
            }
        });
        return view;

}

    protected void hideKeyboard(View view)
    {
        InputMethodManager in = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
