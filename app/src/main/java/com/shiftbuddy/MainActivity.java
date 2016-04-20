package com.shiftbuddy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    LinearLayout shipmentScreenOpen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        initListeners();

    }

    private void initListeners() {

        shipmentScreenOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(MainActivity.this, SelectionActivity.class);
                startActivity(myIntent);
            }
        });
    }

    private void init() {
    shipmentScreenOpen = (LinearLayout) findViewById(R.id.nextScreen);
    }


}
