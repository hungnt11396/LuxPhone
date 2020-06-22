package com.hungnguyen.luxphone.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.hungnguyen.luxphone.Common.Common;
import com.hungnguyen.luxphone.DetailActivity;
import com.hungnguyen.luxphone.DetailNewsActivity;
import com.hungnguyen.luxphone.Listener.ItemClickListener;
import com.hungnguyen.luxphone.R;
import com.hungnguyen.luxphone.model.News;
import com.hungnguyen.luxphone.model.Product;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

class RecyclerViewHolderNews extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public ImageView imgNews;
    public TextView tvTitle;

    private ItemClickListener itemClickListener;

    public RecyclerViewHolderNews(View itemView) {
        super(itemView);

        imgNews = itemView.findViewById(R.id.imgItemNews);
        tvTitle = itemView.findViewById(R.id.tvItemTitleNews);
        itemView.setOnClickListener(this);

    }

    public void setItemClickListener(ItemClickListener itemClickListener)
    {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition());
    }

}

public class ItemNewsAdapter extends RecyclerView.Adapter<RecyclerViewHolderNews> {

    private ArrayList<News> newsList;
    private Context context;

    public ItemNewsAdapter(Context context, ArrayList<News> newsList) {
        this.context = context;
        this.newsList = newsList;
    }

    @Override
    public RecyclerViewHolderNews onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_news, null);
        RecyclerViewHolderNews viewHolder = new RecyclerViewHolderNews(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolderNews holder, int position) {
        News news = newsList.get(position);

        holder.tvTitle.setText(news.getTitle());
        Picasso.with(context).load(news.getHeaderImage()).error(R.drawable.errorimg).into(holder.imgNews);

        //Sự kiện Click Item Recyclview
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                //Common.news = newsList.get(position);
                Intent detailNewsIntent = new Intent(context, DetailNewsActivity.class);
                DetailNewsActivity.detailNews = newsList.get(position);
                context.startActivity(detailNewsIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return newsList == null ? 0 : newsList.size();
    }


}


