package Fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestAsyncTask;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.Task;
import com.gozde.osmanlitapp.R;
import com.gozde.osmanlitapp.SharedPrefManager;

import org.json.JSONObject;

import java.util.Arrays;

import Models.UserToken;
import Responses.SocialResponseAccessToken;
import Utils.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentDialogSignup extends DialogFragment implements View.OnClickListener,GoogleApiClient.OnConnectionFailedListener{
    ImageButton close_dialog;
    Button btn_fb,g_btn,e_btn;
    TextView textgiris;
    CallbackManager callbackManager;
    private static final String EMAIL = "email";
    LoginButton loginButton;
    //private GoogleApiClient googleApiClient;
    GoogleSignInClient mGoogleSignInClient;
    private static final int code=100;
    SignInButton btnGoogleSignIn;
    Context mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setStyle(STYLE_NO_TITLE, android.R.style.Theme_Material_Light_NoActionBar_Fullscreen);
        } else {
            setStyle(STYLE_NO_TITLE, android.R.style.Theme_DeviceDefault_Light_NoActionBar);
        }
        super.onCreate(savedInstanceState);




    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_dialog,container,false);
        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.server_client_id))
                .requestEmail()
                .build();
       // googleApiClient = new GoogleApiClient.Builder(getContext()).enableAutoManage(getActivity(), this).addApi(Auth.GOOGLE_SIGN_IN_API, signInOptions).build();



         btnGoogleSignIn = view.findViewById(R.id.btnGoogleSignIn);
         mGoogleSignInClient = GoogleSignIn.getClient(getContext(), signInOptions);
        btnGoogleSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googleSocialLogin();
            }
        });

        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) view.findViewById(R.id.login_button);
        loginButton.setOnClickListener(this);
        loginButton.setReadPermissions(Arrays.asList("public_profile",EMAIL));

        loginButton.setFragment(this);

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
               final AccessToken accessToken=loginResult.getAccessToken();
                GraphRequestAsyncTask request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        //  LoginManager.getInstance().logOut();
                        String a_token = response.getRequest().getAccessToken().getToken();
                        LoginManager.getInstance().logInWithReadPermissions(getActivity(), Arrays.asList("public_profile",EMAIL));
                        Call<SocialResponseAccessToken> call = ApiClient.getInstance(getContext()).getApi().fblogin("application/json",a_token);
                        call.enqueue(new Callback<SocialResponseAccessToken>() {
                            @Override
                            public void onResponse(Call<SocialResponseAccessToken> call, Response<SocialResponseAccessToken> response) {
                                String accesstoken= response.body().getAccess_token();
                                UserToken userToken = new UserToken(accesstoken);

                                SharedPrefManager.getInstance(getActivity()).saveToken(userToken);
                                Toast.makeText(mContext, R.string.basarili, Toast.LENGTH_SHORT).show();
                                dismiss();

                                getFragmentManager().beginTransaction().replace(R.id.container,new FragmentAyarlar()).commit();

                            }

                            @Override
                            public void onFailure(Call<SocialResponseAccessToken> call, Throwable t) {
                                Log.d("hata", "hata");
                                t.printStackTrace();
                            }
                        });
                    }
                }).executeAsync();
                Toast.makeText(getContext(), "Login Success with facebook", Toast.LENGTH_SHORT).show();
                getFragmentManager().beginTransaction().replace(R.id.container,new FragmentHome()).commit();
            }

            @Override
            public void onCancel() {
                // App code
                Log.d("cancel","asd");
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                Log.d("err", exception.toString());
            }
        });



        // If you are using in a fragment, call loginButton.setFragment(this);
      /*   loginButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {


                 LoginManager.getInstance().logInWithReadPermissions(getActivity(), Arrays.asList("public_profile"));

             }
         });*/
        // Callback registration









      getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));









      e_btn=(Button)view.findViewById(R.id.btn_e);
      textgiris=(TextView)view.findViewById(R.id.txt_giris);
       close_dialog=(ImageButton)view.findViewById(R.id.close);
       close_dialog.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               dismiss();
           }
       });
      textgiris.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              FragmentDialogLogin dialogSignup = new FragmentDialogLogin();
              dialogSignup.show(getFragmentManager(),"FragmentLogin");
            dismiss();
          }
      });

      e_btn.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              FragmentDialogRegister dialogRegister=new FragmentDialogRegister();
              dialogRegister.show(getFragmentManager(), "FragmentRegister");
          }
      });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 864) {

            //GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleResultGoogleSignIn(task);
        }
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    private void googleSocialLogin() {
       // Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        Intent intent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(intent, 864);
    }
        private void handleResultGoogleSignIn(Task<GoogleSignInAccount> result) {


        try {
            GoogleSignInAccount account = result.getResult(ApiException.class);
            Log.d("asd","asd");
        }catch (ApiException e) {
            Log.d("signinapi", e.getMessage());
        }
        /*
            if (result.isSuccess()) {
                GoogleSignInAccount account = result.getSignInAccount();
                String id_token = account.getIdToken();
                String socialFirstName = account.getGivenName();
                String socialLastName = account.getFamilyName();
                String socialEmail = account.getEmail();
                String socialUserId = account.getId();

            }
            */
        }
/*
    private void googleLogout() {
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {

            }
        });
    }
*/
    @Override
    public void onPause() {
        super.onPause();
        //googleApiClient.stopAutoManage(getActivity());
        //googleApiClient.disconnect();
    }
}

