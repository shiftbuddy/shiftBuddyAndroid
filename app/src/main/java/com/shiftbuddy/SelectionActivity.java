package com.shiftbuddy;

/**
 * @(#) ShiftBuddy
 * <p>
 * Copyright (C) ShiftBuddy, 2016
 * All rights reserved.
 * <p>
 * This software is the proprietary information of
 * shiftbuddy ("Confidential Information").
 * Author : Dinesh Vaithyalingam Gangatharan
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SelectionActivity extends AppCompatActivity {

    RelativeLayout container;
    LinearLayout senderlayout;
    LinearLayout moverLayout;
    TextView termsAndConditions;

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

    //SnackBar to show user that he has to enter username and password
    private void openAuthenticationSnackbar() {
        Snackbar snackbar = Snackbar
                .make(container,
                        "Open terms and conditions page.", Snackbar.LENGTH_SHORT);
        snackbar.show();
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
    }

    private void init() {
        container = (RelativeLayout)findViewById(R.id.selection_layout);
        senderlayout = (LinearLayout)findViewById(R.id.senderLayout);
        moverLayout = (LinearLayout) findViewById(R.id.moverLayout);
        termsAndConditions = (TextView) findViewById(R.id.terms_and_conditions);
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

        termsAndConditions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAuthenticationSnackbar();
            }
        });
    }

}
