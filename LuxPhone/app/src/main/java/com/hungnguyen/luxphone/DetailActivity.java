package com.hungnguyen.luxphone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hungnguyen.luxphone.model.Buy;
import com.hungnguyen.luxphone.model.Detail;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import static com.hungnguyen.luxphone.Common.Common.product;

public class DetailActivity extends AppCompatActivity {
    private ArrayList<Detail> detailList;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private Toolbar toolbar;
    private Context dContext;
    private TextView tvDetailName, tvDetailPrice, tvDisplay, tvRam, tvIm, tvPin;
    private ImageView imgDetail;
    private Spinner spnNum;
    private Button btnBuy;
    private Locale localeVN;
    private NumberFormat currencyVN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        dContext = this;
        AnhXa();
        Actionbar();
        ThongSoKyThuat();

    }

    private void Actionbar() {
        toolbar = findViewById(R.id.detailToolbar);
        toolbar.setTitle(product.getName());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(dContext, MainActivity.class);
                startActivity(intent);
            }
        });
    }
    private void AnhXa(){
        toolbar         = findViewById(R.id.detailToolbar);
        tvDetailName    = findViewById(R.id.tvDetailName);
        tvDetailPrice   = findViewById(R.id.tvDetailPrice);
        imgDetail       = findViewById(R.id.imgDetailProduct);
        tvDetailName    = findViewById(R.id.tvDetailName);
        tvDetailName    = findViewById(R.id.tvDetailName);
        tvDetailName    = findViewById(R.id.tvDetailName);
        tvDisplay       = findViewById(R.id.tvDisplay);
        tvRam           = findViewById(R.id.tvRam);
        tvIm            = findViewById(R.id.tvIm);
        tvPin           = findViewById(R.id.tvPin);
        spnNum          = findViewById(R.id.spnNum);
        btnBuy          = findViewById(R.id.btnBuy);

        Integer[] soLuong = new Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, soLuong);
        spnNum.setAdapter(arrayAdapter);

        tvDetailName.setText(product.getName());
        tvDetailPrice.setText(formatcurrencyVN(product.getPrice()));
        Picasso.with(dContext).load(product.getImagelink()).error(R.drawable.errorimg).into(imgDetail);



        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int totalPrice;
                int price = Integer.parseInt(product.getPrice());

                int soLuong = Integer.parseInt(spnNum.getSelectedItem().toString());
                String quantity = spnNum.getSelectedItem().toString();

                totalPrice = soLuong*price;

                String str = formatcurrencyVN(totalPrice + "");



                Buy buy = new Buy(product.getName(), product.getImagelink(), product.getPrice().toString(), quantity);

                boolean fl = false;
                if (BuyActivity.buyArrayList.size() > 0){
                    for (int i = 0; i <BuyActivity.buyArrayList.size(); i++){
                        Buy tempBuy = BuyActivity.buyArrayList.get(i);
                        if (buy.getProductName().equals(tempBuy.getProductName())) {
                            int sl = Integer.parseInt(tempBuy.getProductQuantity()) + soLuong;
                            BuyActivity.buyArrayList.get(i).setProductQuantity(String.valueOf(sl));
                            fl = true;
                        }
                    }
                    if (!fl) BuyActivity.buyArrayList.add(buy);
                }else BuyActivity.buyArrayList.add(buy);




                //Toast.makeText(dContext, Common.buy.getProductName() + " - Số lượng: " + Common.buy.getProductQuantity(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(dContext, BuyActivity.class);
                startActivity(intent);
            }
        });
    }

    private String formatcurrencyVN(String strCurrent){
        localeVN = new Locale("vi", "VN");
        currencyVN = NumberFormat.getCurrencyInstance(localeVN);
        int current = Integer.parseInt(strCurrent);
        String strNewCurrent = currencyVN.format(current);

        return strNewCurrent;
    }

    private void ThongSoKyThuat() {
        detailList = new ArrayList<>();
        final DatabaseReference detailsRef = database.getReference("details");
        detailsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Detail detail;
                    String key = snapshot.getKey();
                    detail = dataSnapshot.child(key).getValue(Detail.class);
                    detailList.add(detail);
                }
                if (detailList.size() != 0){
                    for (int i = 0; i < detailList.size(); i++){

                        if (detailList.get(i).getName().equals(tvDetailName.getText())){
                            tvDisplay.setText(detailList.get(i).getDisplay());
                            tvRam.setText(detailList.get(i).getRAM());
                            tvIm.setText(detailList.get(i).getIM());
                            tvPin.setText(detailList.get(i).getPin());
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

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
