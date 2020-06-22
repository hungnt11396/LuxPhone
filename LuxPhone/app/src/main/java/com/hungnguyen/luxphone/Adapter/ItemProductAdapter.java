package com.hungnguyen.luxphone.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.hungnguyen.luxphone.Common.Common;
import com.hungnguyen.luxphone.DetailActivity;
import com.hungnguyen.luxphone.Listener.ItemClickListener;
import com.hungnguyen.luxphone.R;
import com.hungnguyen.luxphone.model.Product;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

class RecyclerViewHolderProduct extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public ImageView imgProduct;
    public TextView productName;
    public TextView productPrice;

    private ItemClickListener itemClickListener;

    public RecyclerViewHolderProduct(View itemView) {
        super(itemView);

        imgProduct = itemView.findViewById(R.id.imgProduct);
        productName = itemView.findViewById(R.id.tvName);
        productPrice = itemView.findViewById(R.id.tvPrice);
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

public class ItemProductAdapter extends RecyclerView.Adapter<RecyclerViewHolderProduct> {

    private ArrayList<Product> productList;
    private Context context;

    public ItemProductAdapter(Context context, ArrayList<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @Override
    public RecyclerViewHolderProduct onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product, null);
        RecyclerViewHolderProduct viewHolder = new RecyclerViewHolderProduct(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolderProduct holder, int position) {
        Product product = productList.get(position);

        Locale localeVN;
        NumberFormat currencyVN;
        localeVN = new Locale("vi", "VN");
        currencyVN = NumberFormat.getCurrencyInstance(localeVN);
        int price;
        try{
            price = Integer.parseInt(product.getPrice());
        }catch (Exception e){ price =0; }

        String strPrice = currencyVN.format(price);

        holder.productName.setText(product.getName());
        holder.productPrice.setText(strPrice);
        Picasso.with(context).load(product.getImagelink()).error(R.drawable.errorimg).into(holder.imgProduct);

        //Sự kiện Click Item Recyclview
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Common.product = productList.get(position);

                Intent detailIntent = new Intent(context, DetailActivity.class);
                context.startActivity(detailIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList == null ? 0 : productList.size();
    }


}


