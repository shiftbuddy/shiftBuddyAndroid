package com.shiftbuddy;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SelectionActivity extends AppCompatActivity {

    LinearLayout senderlayout;
    LinearLayout moverLayout;
    TextView sender;
    TextView mover;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);
        init();
        initListeners();
    }

    /**
     * Take care of popping the fragment back stack or finishing the activity
     * as appropriate.
     */
    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        moveTaskToBack(true);
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
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onResume() {
        super.onResume();
        senderlayout.setBackgroundDrawable(getDrawable(R.drawable.rectangle_red));
        moverLayout.setBackgroundDrawable(getDrawable(R.drawable.rectangle_orange));

        if(sender!=null)
            sender.setTextColor(Color.parseColor("#ffcc0000"));
        if(mover!=null)
            mover.setTextColor(Color.parseColor("#ffff8800"));
    }

    private void init() {
        senderlayout = (LinearLayout)findViewById(R.id.senderLayout);
        moverLayout = (LinearLayout) findViewById(R.id.moverLayout);
        sender = (TextView) findViewById(R.id.sender);
        mover = (TextView) findViewById(R.id.mover);
    }

    private void initListeners() {

        senderlayout.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                senderlayout.setBackgroundDrawable(getDrawable(R.drawable.rectangle_red_filled));
                sender.setTextColor(Color.WHITE);
                Intent myIntent = new Intent(SelectionActivity.this, ShipmentActivity.class);
                startActivity(myIntent);
            }
        });

        moverLayout.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                moverLayout.setBackgroundDrawable(getDrawable(R.drawable.rectangle_orange_filled));
                mover.setTextColor(Color.WHITE);
                Intent myIntent = new Intent(SelectionActivity.this, ShipmentActivity.class);
                startActivity(myIntent);
            }
        });
    }

}
