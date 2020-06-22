package com.hungnguyen.luxphone;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hungnguyen.luxphone.Adapter.ItemProductAdapter;
import com.hungnguyen.luxphone.Common.Common;
import com.hungnguyen.luxphone.model.Product;

import java.util.ArrayList;

import dmax.dialog.SpotsDialog;


public class ProductFragment extends Fragment {
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference productDataRef = database.getReference(Common.PRODUCT);
    private RecyclerView recyclerViewProduct;
    public  ArrayList<Product> productList;
    private NavigationView rightNavLoc;
    private DrawerLayout right_drawerLayout;
    private EditText edtPriceMin, edtPriceMax;
    private Spinner spnCompany;
    private TextView tvSamsung, tvIphone;
    private Button btnLoc, btnHuyLoc,btnChon;
    Context mContext;

    public ProductFragment(Context context) {
        this.mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product, container, false);
        AnhXa(view);

        btnLoc.setVisibility(View.VISIBLE);
        btnHuyLoc.setVisibility(View.INVISIBLE);

        getDataforRecycleviewProduct();
        updateProduct();



        tvSamsung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<Product> prodL = getListProductByCompany(productList,"samsung");
                loadRecycleViewProduct(prodL);
                btnHuyLoc.setVisibility(View.VISIBLE);
            }
        });

        tvIphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Product> prodL = getListProductByCompany(productList,"apple");
                loadRecycleViewProduct(prodL);
                btnHuyLoc.setVisibility(View.VISIBLE);
            }
        });

        btnLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                right_drawerLayout.openDrawer(GravityCompat.END);

            }
        });

        btnHuyLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnHuyLoc.setVisibility(View.INVISIBLE);
                btnLoc.setVisibility(View.VISIBLE);
                loadRecycleViewProduct(productList);

                String[] soLuong = new String[]{"Chọn hãng", "samsung", "apple", "oppo", "vsmart"};
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_dropdown_item, soLuong);
                spnCompany.setAdapter(arrayAdapter);
                edtPriceMax.setText("");
                edtPriceMin.setText("");
            }
        });


        btnChon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sCompany = spnCompany.getSelectedItem().toString();
                String priceMin = edtPriceMin.getText().toString();
                String priceMax = edtPriceMax.getText().toString();

                if (TextUtils.isEmpty(priceMin))
                    priceMin = "0";
                if (TextUtils.isEmpty(priceMax))
                    priceMax = "0";

                loc(sCompany, priceMin, priceMax);
            }
        });



        return view;
    }

    private void updateProduct(){

        productDataRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                getDataforRecycleviewProduct();

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                try {
                    getDataforRecycleviewProduct();
                }catch (Exception e){}

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getDataforRecycleviewProduct(){
        productList = new ArrayList<>();
        final AlertDialog dialog = new SpotsDialog.Builder().setContext(mContext).build();

        dialog.show();
        if (testConnect()){
            productDataRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    dialog.dismiss();
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        String keyChild = postSnapshot.getKey();
                        try {
                            Product product = dataSnapshot.child(keyChild).getValue(Product.class);
                            productList.add(product);
                        }catch (Exception e){
                            Toast.makeText(getContext(),e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                    loadRecycleViewProduct(productList);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        else {
            dialog.dismiss();
            Toast.makeText(mContext, "Chưa kết nối Internet!", Toast.LENGTH_LONG).show();
        }
    }

    private ArrayList<Product> getListProductByCompany(ArrayList<Product> products, String company){

        ArrayList<Product> prodList = new ArrayList<>();

        for (int i = 0; i < products.size(); i++){
            Product prod;
            if (products.get(i).getCompany().equals(company)) {
                prod = products.get(i);

                prodList.add(prod);
            }
        }

        return prodList;
    }
    private ArrayList<Product> getListProductByPrice(ArrayList<Product> products, int priceMin, int priceMax){
        if (priceMin < priceMax){
        ArrayList<Product> prodList = new ArrayList<>();
        for (int i = 0; i < products.size(); i++){
            Product prod;
            int price = Integer.parseInt(products.get(i).getPrice());
            if (price >= priceMin && price <= priceMax) {
                prod = products.get(i);
                prodList.add(prod);
            }
        }
        return prodList;
        }
        return products;
    }

    private void loc(String sCompany, String priceMin, String priceMax){

        ArrayList<Product> prodL = new ArrayList<>();

        int iPriceMin = Integer.parseInt(priceMin);
        int iPriceMax = Integer.parseInt(priceMax);
        if (sCompany != "Chọn hãng"){
            prodL = getListProductByCompany(productList, sCompany);
        }
        if (iPriceMin < iPriceMax){
            if (prodL.size()>0){
                prodL = getListProductByPrice(prodL, iPriceMin, iPriceMax);
            }else prodL = getListProductByPrice(productList,  iPriceMin, iPriceMax);
        }
        if (sCompany == "Chọn hãng" && iPriceMin == 0 && iPriceMax == 0)
            prodL = productList;

        loadRecycleViewProduct(prodL);
        btnHuyLoc.setVisibility(View.VISIBLE);
        btnLoc.setVisibility(View.INVISIBLE);
        right_drawerLayout.closeDrawer(GravityCompat.END);

    }


    private void loadRecycleViewProduct(ArrayList<Product> proList){
        ItemProductAdapter itemProductAdapter = new ItemProductAdapter(mContext, proList);
        recyclerViewProduct.setHasFixedSize(true);
        recyclerViewProduct.setLayoutManager(new GridLayoutManager(mContext, 2));
        recyclerViewProduct.setAdapter(itemProductAdapter);
    }

    private void AnhXa(View v){
        spnCompany = v.findViewById(R.id.spnCompany);
        right_drawerLayout = v.findViewById(R.id.right_DrawerLayout);
        rightNavLoc = v.findViewById(R.id.right_navigation);
        recyclerViewProduct = v.findViewById(R.id.recyclerViewProduct);
        btnLoc = v.findViewById(R.id.btnLoc);
        btnChon = v.findViewById(R.id.btnChon);
        btnHuyLoc = v.findViewById(R.id.btnHuyLoc);
        tvSamsung = v.findViewById(R.id.tvSamsung);
        tvIphone = v.findViewById(R.id.tvIphone);
        edtPriceMin = v.findViewById(R.id.edtPriceMin);
        edtPriceMax = v.findViewById(R.id.edtPriceMax);
        String[] soLuong = new String[]{"Chọn hãng", "samsung", "apple", "oppo", "vsmart"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_dropdown_item, soLuong);
        spnCompany.setAdapter(arrayAdapter);
    }
    private boolean testConnect(){
        boolean test = false;
        ConnectivityManager manager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = manager.getActiveNetworkInfo();
        if(ni != null && ni.isConnected()) {
            test = true;
        }
        return test;
    }

}
