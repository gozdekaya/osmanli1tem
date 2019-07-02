package Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static Retrofit retrofit;
    private static final String  BASE_URL="http://api.osmanli.app-xr.com/api/v1/";
    private static ApiClient mInstance;
    private static Context mContext;

    private ApiClient(){

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();



    }

    public static synchronized ApiClient getInstance(Context context){

        if (mInstance==null){
            mContext = context;
            mInstance=new ApiClient();
        }
        return mInstance;
    }

    public ApiInterface getApi(){
        return retrofit.create(ApiInterface.class);
    }
}
