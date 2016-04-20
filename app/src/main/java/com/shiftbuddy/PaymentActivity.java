package com.shiftbuddy;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

public class PaymentActivity extends AppCompatActivity {

    public  static final  String TAG = PaymentActivity.class.getSimpleName();
    LinearLayout paymentActivity;
    LinearLayout postPackage;
    EditText fromAddress;
    EditText toAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        init();
        initListeners();
    }

    private void initListeners() {
        postPackage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!fromAddress.getText().toString().equals("") && !toAddress.getText().toString().equals("")) {
                    //validate address in Manager class
                    openPostedSnackbar();
                } else {
                    openAddressEnterSnackBar();
                }

            }
        });
    }

    private void openAddressEnterSnackBar() {
        //Open snackbar
        Snackbar snackbar = Snackbar
                .make(paymentActivity,
                        "Please enter valid location", Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    private void openPostedSnackbar() {

        //Open snackbar
        Snackbar snackbar = Snackbar
                .make(paymentActivity,
                        "Your package has been successfully posted!", Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    private void init() {
        paymentActivity = (LinearLayout) findViewById(R.id.payment_activity_layout);
        postPackage = (LinearLayout) findViewById(R.id.send_package);
        fromAddress = (EditText) findViewById(R.id.address_from);
        toAddress = (EditText) findViewById(R.id.address_to);
    }

}
