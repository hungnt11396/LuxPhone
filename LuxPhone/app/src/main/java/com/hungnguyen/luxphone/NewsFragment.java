package com.hungnguyen.luxphone;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hungnguyen.luxphone.Adapter.ItemBuyAdapter;
import com.hungnguyen.luxphone.Adapter.ItemNewsAdapter;
import com.hungnguyen.luxphone.Common.Common;
import com.hungnguyen.luxphone.model.News;
import com.hungnguyen.luxphone.model.Product;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import dmax.dialog.SpotsDialog;

public class NewsFragment extends Fragment {
    private ArrayList<News> newsList = new ArrayList<>();
    private TextView tvTitleHotNews;
    private ImageView imgHotNews;
    private RecyclerView recyclerViewNews;
    private Context context;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference newsReference = database.getReference(Common.NEWS);
    private News hotNews;

    public NewsFragment(Context context) {
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        AnhXa(view);
        getDataForRecyclerViewNews();

        return view;
    }

    private void getDataForRecyclerViewNews(){
        final AlertDialog dialog = new SpotsDialog.Builder().setContext(context).build();
        dialog.show();
        if (testConnect()) {
            newsReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    dialog.dismiss();
                    newsList.clear();
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        String keyChild = postSnapshot.getKey();
                        try {
                            News news = dataSnapshot.child(keyChild).getValue(News.class);
                            newsList.add(news);
                        }catch (Exception e){
                            Toast.makeText(getContext(),e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                    loadRecycleViewNews(newsList);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        else {
            dialog.dismiss();
            Toast.makeText(context, "Chưa kết nối Internet!", Toast.LENGTH_LONG).show();
        }
    }


    private void loadRecycleViewNews(ArrayList<News> newsArrayList) {

        if (newsArrayList.size() > 0) {
            ArrayList<News> tempList = new ArrayList<>();
            for (int i = newsArrayList.size() - 2; i >= 0; i--){
                tempList.add(newsArrayList.get(i));
            }
            hotNews = newsArrayList.get(newsArrayList.size() - 1);
            Picasso.with(context).load(newsArrayList.get(newsArrayList.size() - 1).getHeaderImage()).error(R.drawable.errorimg).into(imgHotNews);
            imgHotNews.setScaleType(ImageView.ScaleType.FIT_XY);
            tvTitleHotNews.setText(newsArrayList.get(newsArrayList.size() - 1).getTitle());

            ItemNewsAdapter itemNewsAdapter = new ItemNewsAdapter(context, tempList);
            recyclerViewNews.setHasFixedSize(true);
            recyclerViewNews.setLayoutManager(new GridLayoutManager(context, 1));
            recyclerViewNews.setAdapter(itemNewsAdapter);
        }
    }


    private void AnhXa(View view) {
        tvTitleHotNews = view.findViewById(R.id.tvTitleHotNews);
        imgHotNews = view.findViewById(R.id.imgHotNews);
        recyclerViewNews = view.findViewById(R.id.recyclerViewNews);

        tvTitleHotNews.setOnClickListener(onClickListener);

        imgHotNews.setOnClickListener(onClickListener);
    }

    private View.OnClickListener  onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (hotNews != null) {
                Intent detailNewsIntent = new Intent(context, DetailNewsActivity.class);
                DetailNewsActivity.detailNews = hotNews;
                context.startActivity(detailNewsIntent);
            }
        }
    };

    private boolean testConnect(){
        boolean test = false;
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = manager.getActiveNetworkInfo();
        if(ni != null && ni.isConnected()) {
            test = true;
        }
        return test;
    }

}

