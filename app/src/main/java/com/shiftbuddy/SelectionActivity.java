package com.shiftbuddy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

public class SelectionActivity extends AppCompatActivity {

    LinearLayout senderlayout;
    LinearLayout moverLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);
        init();
        initListeners();
    }

    private void init() {
        senderlayout = (LinearLayout)findViewById(R.id.senderLayout);
        moverLayout = (LinearLayout) findViewById(R.id.moverLayout);
    }

    private void initListeners() {

        senderlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(SelectionActivity.this, ShipmentActivity.class);
                startActivity(myIntent);
            }
        });

        moverLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(SelectionActivity.this, ShipmentActivity.class);
                startActivity(myIntent);
            }
        });
    }

}
