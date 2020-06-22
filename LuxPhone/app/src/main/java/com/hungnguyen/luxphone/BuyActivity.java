package com.hungnguyen.luxphone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.hungnguyen.luxphone.Adapter.ItemBuyAdapter;
import com.hungnguyen.luxphone.Common.Common;
import com.hungnguyen.luxphone.model.Buy;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;


public class BuyActivity extends AppCompatActivity {

    private Context context;
    private Toolbar toolbar;
    private Button btnPay;
    public static TextView tvRong , tvTotal;
    public static ArrayList<Buy> buyArrayList = new ArrayList<>();
    RecyclerView recyclerViewShoppingCarts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);

        context = this;
        AnhXa();
        Actionbar();

        loadListProduct();
    }

    public void loadListProduct() {
        int iTotal = 0;

        if (buyArrayList.size() > 0) {

            for (int i = 0; i<buyArrayList.size(); i++){
                int price = Integer.parseInt(buyArrayList.get(i).getProductPrice());
                int quantity = Integer.parseInt(buyArrayList.get(i).getProductQuantity());
                iTotal += price * quantity;
                Common.total = iTotal;
            }
            ItemBuyAdapter itemBuyAdapter = new ItemBuyAdapter(context, buyArrayList);
            recyclerViewShoppingCarts.setHasFixedSize(true);
            recyclerViewShoppingCarts.setLayoutManager(new GridLayoutManager(context, 1));
            recyclerViewShoppingCarts.setAdapter(itemBuyAdapter);

        }else tvRong.setText("Giỏ Hàng Chưa Có Gì!");
        tvTotal.setText(CurrencyVN(iTotal));
    }

    private void AnhXa() {
        btnPay = findViewById(R.id.btnPay);
        tvTotal = findViewById(R.id.tvTotalPrice);
        tvRong = findViewById(R.id.tvRong);
        toolbar = findViewById(R.id.buyToolbar);
        recyclerViewShoppingCarts = findViewById(R.id.recyclerViewShoppingCarts);

        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (buyArrayList.size() > 0){
                    Intent intent = new Intent(context, InfoCustomerActivity.class);
                    InfoCustomerActivity.buyListInfo = buyArrayList;
                    startActivity(intent);

                }else Toast.makeText(context, "Chưa có gì trong giỏ hàng!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void Actionbar() {
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private String CurrencyVN(int money){
        Locale localeVN;
        NumberFormat currencyVN;
        localeVN = new Locale("vi", "VN");
        currencyVN = NumberFormat.getCurrencyInstance(localeVN);
        return currencyVN.format(money);
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
