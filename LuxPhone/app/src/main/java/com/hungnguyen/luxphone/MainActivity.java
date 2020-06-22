package com.hungnguyen.luxphone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    public static String nameFragment;
    DrawerLayout left_drawerLayout;
    NavigationView left_navigationView;
    Toolbar toolbar;
    BottomNavigationView bottomNavigationView;
    Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AnhXa();
        Actionbar();
        OpenFragment();

    }

    private void OpenFragment(){
        if (nameFragment == "NewsFragment"){
            toolbar.setTitle("Tin tức");
            bottomNavigationView.setSelectedItemId(R.id.nav_news);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new NewsFragment(context)).commit();
            nameFragment = "";
        }
    }

    private void Actionbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                left_drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.item_shopping_cart:
                Intent intent = new Intent(context, BuyActivity.class);
                startActivity(intent);
                break;
            default:break;
        }

        return super.onOptionsItemSelected(item);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_shopping_cart, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void AnhXa() {
        left_drawerLayout = findViewById(R.id.left_drawerlayout);
        left_navigationView = findViewById(R.id.left_navigationView);
        left_navigationView.setNavigationItemSelectedListener(navListener);
        toolbar = findViewById(R.id.toolbar);
        bottomNavigationView = findViewById(R.id.bottom_navigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomnavListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProductFragment(context)).commit();

    }

    private NavigationView.OnNavigationItemSelectedListener navListener =
            new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    Intent intent = new Intent();
                    boolean bFlag = false;
                    String toolbarTitle = "";
                    switch (item.getItemId()){
                        case R.id.left_nav_product:
                            selectedFragment = new ProductFragment(context);
                            toolbarTitle = "Sản phẩm";
                            break;
                        case R.id.left_nav_news:
                            selectedFragment = new NewsFragment(context);
                            toolbarTitle = "Tin tức";
                            break;
                        case R.id.left_nav_supports:
                            selectedFragment = new SupportsFragment(context);
                            toolbarTitle = "Hỗ trợ";
                            break;
                        case R.id.left_nav_shoppingCart:
                            bFlag = true;
                            intent = new Intent(context, BuyActivity.class);
                            break;
                    }
                    left_drawerLayout.closeDrawer(GravityCompat.START);
                    if (bFlag){
                        startActivity(intent);
                    }
                    else{
                        toolbar.setTitle(toolbarTitle);
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                    }
                    return true;
                }
            };

    private BottomNavigationView.OnNavigationItemSelectedListener bottomnavListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    String toolbarTitle = "";
                    switch (item.getItemId()){
                        case R.id.nav_product:
                            selectedFragment = new ProductFragment(context);
                            toolbarTitle = "Sản phẩm";
                            break;
                        case R.id.nav_news:
                            selectedFragment = new NewsFragment(context);
                            toolbarTitle = "Tin tức";
                            break;
                        case R.id.nav_supports:
                            selectedFragment = new SupportsFragment(context);
                            toolbarTitle = "Hỗ trợ";
                            break;
                    }
                    toolbar.setTitle(toolbarTitle);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                    return true;
                }
            };

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
