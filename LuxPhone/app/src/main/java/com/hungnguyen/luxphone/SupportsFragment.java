package com.hungnguyen.luxphone;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;


public class SupportsFragment extends Fragment {
    private Context context;

    public SupportsFragment(Context context) {
        this.context = context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_supports, container, false);

        AnhXa(view);



        return view;
    }



    private void AnhXa(View view) {
        TextView tvKhieuNai = view.findViewById(R.id.tvKhieuNai);
        TextView tvTongDai = view.findViewById(R.id.tvTongDai);

        final Date currentTime = Calendar.getInstance().getTime();
        final float temp = currentTime.getMinutes();
        final float now = currentTime.getHours() + temp/60;

        tvTongDai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (now > 7.5 && now < 21.0) {
                    String dial = "tel:19000091";
                    startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(dial)));
                } else Toast.makeText(context, "Ngoài giờ làm việc", Toast.LENGTH_LONG).show();
            }
        });

        tvKhieuNai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (now > 8.0 && now < 21.5) {
                    String dial = "tel:18000081";
                    startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(dial)));
                } else Toast.makeText(context, "Ngoài giờ làm việc", Toast.LENGTH_LONG).show();
            }
        });
    }
    
    
}
