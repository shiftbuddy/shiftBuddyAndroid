package com.shiftbuddy;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ShipmentActivity extends AppCompatActivity {

    LinearLayout nextScreen;
    TextView nextText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipment);
        init();
        initListeners();
    }

    /**
     * Dispatch onResume() to fragments.  Note that for better inter-operation
     * with older versions of the platform, at the point of this call the
     * fragments attached to the activity are <em>not</em> resumed.  This means
     * that in some cases the previous state may still be saved, not allowing
     * fragment transactions that modify the state.  To correctly interact
     * with fragments in their proper state, you should instead override
     * {@link #onResumeFragments()}.
     */
    @Override
    protected void onResume() {
        super.onResume();
        colorReset();

    }

    private void colorReset() {
        nextText.setTextColor(Color.WHITE);
    }

    private void initListeners() {

        nextScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextText.setTextColor(Color.BLACK);
                Intent myIntent = new Intent(ShipmentActivity.this, PaymentActivity.class);
                startActivity(myIntent);
            }
        });
    }

    private void init() {
        nextScreen = (LinearLayout)findViewById(R.id.next);
        nextText = (TextView)findViewById(R.id.nextText);
        nextText.setTextColor(Color.WHITE);
    }
}
