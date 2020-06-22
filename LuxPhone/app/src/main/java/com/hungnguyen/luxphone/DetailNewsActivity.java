package com.hungnguyen.luxphone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import com.hungnguyen.luxphone.model.News;
import com.squareup.picasso.Picasso;

public class DetailNewsActivity extends AppCompatActivity {
    public static News detailNews;
    private Context context;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_news);
        context = this;

        AnhXa();
        Actionbar();

    }

    private void AnhXa() {
        ImageView imgDetailNews = findViewById(R.id.imgDetailNews);
        TextView tvContentDetailNews = findViewById(R.id.tvContentDetailNews);
        TextView tvTitleDetailNews = findViewById(R.id.tvTitleDetailNews);
        toolbar = findViewById(R.id.toolbarDetailNews);

        Picasso.with(context).load(detailNews.getHeaderImage()).error(R.drawable.errorimg).into(imgDetailNews);
        imgDetailNews.setScaleType(ImageView.ScaleType.FIT_XY);
        tvTitleDetailNews.setText(detailNews.getTitle());
        tvContentDetailNews.setText(detailNews.getContent());

    }

    private void Actionbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(context, MainActivity.class);
                MainActivity.nameFragment = "NewsFragment";
                startActivity(mainIntent);
            }
        });
    }

    //Hiệu ứng khi start & finish activity
    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

}