package com.hungnguyen.luxphone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hungnguyen.luxphone.Common.Common;
import com.hungnguyen.luxphone.model.Buy;
import com.hungnguyen.luxphone.model.Order;
import com.hungnguyen.luxphone.model.OrderDetail;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

import dmax.dialog.SpotsDialog;

public class InfoCustomerActivity extends AppCompatActivity {
    private Context context;
    private Toolbar toolbar;
    public static ArrayList<Buy> buyListInfo = new ArrayList<>();
    private Button btnXacNhan;
    private EditText edtName, edtPhone;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference infoReference = database.getReference(Common.INFO);
    private DatabaseReference orderDetailReference = database.getReference(Common.OrderDetail);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_customer);
        context= this;

        AnhXa();
        Actionbar();
        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (testConnect()){
                    if (edtName.getText().toString().equals("") || edtPhone.getText().toString().equals(""))
                        Toast.makeText(context, "Bạn chưa nhập đủ thông tin!", Toast.LENGTH_LONG).show();
                    else LuuDuLieuMuaHangVaoDuLieu(buyListInfo);
                }else Toast.makeText(context, "Bạn chưa kết nối Internet! Hãy kết nối Internet!", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void AnhXa() {
        btnXacNhan = findViewById(R.id.btnXacNhan);
        edtName = findViewById(R.id.edtTenKH);
        edtPhone = findViewById(R.id.edtPhoneNumber);
        toolbar = findViewById(R.id.infoToolbar);

    }

    private void LuuDuLieuMuaHangVaoDuLieu(ArrayList<Buy> list) {

        final String currentTime = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
                                        .format(Calendar.getInstance().getTime()); //Lấy thời gian hiện tại định dạng "dd/MM/yyyy HH:mm:ss"

        final AlertDialog dialog = new SpotsDialog.Builder().setContext(context).build();
        dialog.show();
        if (list.size() > 0){
            String keyID = FirebaseDatabase.getInstance().getReference().push().getKey();
            Order order = new Order();
            order.setCustomerName(edtName.getText().toString());
            order.setCustomerPhone(edtPhone.getText().toString());
            order.setOrderID(keyID);
            order.setDateBuy(currentTime);
            infoReference.child(keyID).setValue(order); //thêm thông tin KH vào Data
            for (int i = 0; i < list.size(); i++){
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setOrderID(keyID);
                orderDetail.setProductName(list.get(i).getProductName());
                orderDetail.setQuantity(list.get(i).getProductQuantity());
                String orderDetailKey = FirebaseDatabase.getInstance().getReference().push().getKey();
                orderDetailReference.child(orderDetailKey).setValue(orderDetail); //Thêm chi tiết sản phẩm KH mua vào data
            }
            dialog.dismiss();
            BuyActivity.buyArrayList.clear();
            buyListInfo.clear();
            Toast.makeText(context, "Yêu cầu mua thành công!\n Sẽ có nhân viên gọi xác nhận đơn hàng của bạn!", Toast.LENGTH_LONG).show();
            startActivity(new Intent(context, MainActivity.class));
        }else {
            dialog.dismiss();
            Toast.makeText(context, "Yêu cầu mua thất bại!\n Không có gì trong giỏ hàng!", Toast.LENGTH_LONG).show();
        }
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

    private boolean testConnect(){
        boolean test = false;
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = manager.getActiveNetworkInfo();
        if(ni != null && ni.isConnected()) {
            test = true;
        }
        return test;
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