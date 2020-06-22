package com.hungnguyen.luxphone.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.FirebaseDatabase;
import com.hungnguyen.luxphone.BuyActivity;
import com.hungnguyen.luxphone.Common.Common;
import com.hungnguyen.luxphone.Listener.ItemClickListener;
import com.hungnguyen.luxphone.R;
import com.hungnguyen.luxphone.model.Buy;
import com.hungnguyen.luxphone.model.Product;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import static com.hungnguyen.luxphone.BuyActivity.tvRong;
import static com.hungnguyen.luxphone.BuyActivity.tvTotal;

class RecyclerViewHolderBuy extends RecyclerView.ViewHolder
{
    public ImageView imgProductLink;
    public TextView productNameBuy;
    public TextView productPriceBuy;
    public TextView productQuantityBuy;
    public TextView productTotalPriceBuy;
    public Button btnDeleteProductBuy;


    public RecyclerViewHolderBuy(View itemView) {
        super(itemView);
        imgProductLink = itemView.findViewById(R.id.imgProductItemBuy);
        productNameBuy = itemView.findViewById(R.id.tvproductNameItemBuy);
        productPriceBuy = itemView.findViewById(R.id.tvproductPriceItemBuy);
        productQuantityBuy = itemView.findViewById(R.id.tvproductQuantityItemBuy);
        productTotalPriceBuy = itemView.findViewById(R.id.tvTotalProductItemBuy);
        btnDeleteProductBuy = itemView.findViewById(R.id.btnDeleteProductItemBuy);
    }
}
public class ItemBuyAdapter extends RecyclerView.Adapter<RecyclerViewHolderBuy>{
    private Context context;
    private ArrayList<Buy> buyList;
    //private TextView tvTotal, tvRong;

    public ItemBuyAdapter(Context context, ArrayList<Buy> BuyList) {
        this.context = context;
        this.buyList = BuyList;
    }

    @Override
    public RecyclerViewHolderBuy onCreateViewHolder(ViewGroup parent, int viewType) {
        //tvTotal = parent.findViewById(R.id.tvTotalPrice);

        View view = LayoutInflater.from(context).inflate(R.layout.item_shoppingcarts, null);
        RecyclerViewHolderBuy viewHolder = new RecyclerViewHolderBuy(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolderBuy holder, final int position) {
        Buy buy = buyList.get(position);

        String strPrice = CurrencyVN(Integer.parseInt(buy.getProductPrice()));
        int totalProductTemp;
        try{
            totalProductTemp = Integer.parseInt(buy.getProductPrice()) * Integer.parseInt(buy.getProductQuantity());
        }catch (Exception e){
            totalProductTemp = 0;
        }
        final int totalProduct = totalProductTemp;
        String strTotalPrice = CurrencyVN(totalProduct);

        holder.productNameBuy.setText(buy.getProductName());
        holder.productPriceBuy.setText("Giá: " + strPrice);
        holder.productQuantityBuy.setText("Số lượng: " + buy.getProductQuantity());
        holder.productTotalPriceBuy.setText(strTotalPrice);
        Picasso.with(context).load(buy.getProductImageLink()).error(R.drawable.errorimg).into(holder.imgProductLink);

        holder.btnDeleteProductBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Common.total = Common.total - totalProduct;
                BuyActivity.buyArrayList.remove(position);
                notifyDataSetChanged();
                notifyItemRemoved(position);
                tvTotal.setText(CurrencyVN(Common.total));
                if (BuyActivity.buyArrayList.size() == 0) tvRong.setText("Giỏ Hàng Chưa Có Gì!");
            }
        });
        //notifyItemChanged(position);
    }

    @Override
    public int getItemCount() {
        return buyList == null ? 0 : buyList.size();
    }

    private String CurrencyVN(int money){
        Locale localeVN;
        NumberFormat currencyVN;
        localeVN = new Locale("vi", "VN");
        currencyVN = NumberFormat.getCurrencyInstance(localeVN);
        String Money = currencyVN.format(money);
        return Money;
    }


}
