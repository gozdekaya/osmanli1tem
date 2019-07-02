package Fragments;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.gozde.osmanlitapp.R;

public class DialogFragmentMore  extends DialogFragment {
ImageButton close;
LinearLayout linsepet,linkart,linsiparis;
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
       View view= inflater.inflate(R.layout.fragment_dialog_more,container,false);
close=view.findViewById(R.id.close);
close.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        dismiss();
    }
});

linkart=view.findViewById(R.id.linkart);
linkart.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        getFragmentManager().beginTransaction().replace(R.id.container,new FragmentCreditCards()).commit();
       dismiss();
    }
});
linsepet=view.findViewById(R.id.linsepet);
linsepet.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        getFragmentManager().beginTransaction().replace(R.id.container,new FragmentCart()).commit();
        dismiss();
    }
});
linsiparis=view.findViewById(R.id.linsiparis);
linsiparis.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        getFragmentManager().beginTransaction().replace(R.id.container,new FragmentSiparisler()).commit();
        dismiss();
    }
});

        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
       return view;
    }



}
