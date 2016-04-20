package com.shiftbuddy;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shiftbuddy.com.shiftbuddy.Manager.Manager;

public class MainActivity extends AppCompatActivity {


    LinearLayout mainActivityLayout;
    LinearLayout shipmentScreenOpen;
    EditText userName;
    EditText password;
    TextView navigateScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        navigateScreen.setTextColor(Color.WHITE);
    }

    private void initListeners() {

        shipmentScreenOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateScreen.setTextColor(Color.BLACK);

                if (userName.getText() != null && password.getText() != null) {
                    if(Manager.authenticateUser(userName.getText().toString(), password.getText().toString())) {
                        Intent myIntent = new Intent(MainActivity.this, SelectionActivity.class);
                        startActivity(myIntent);
                    } else {
                        openAuthenticationSnackbar();
                    }
                } else {
                    openAuthenticationSnackbar();
                }
            }
        });
    }

    //Open snackbar
    private void openAuthenticationSnackbar() {
        Snackbar snackbar = Snackbar
                .make(mainActivityLayout,
                        "Please enter valid username and password", Snackbar.LENGTH_SHORT);
        snackbar.show();
        navigateScreen.setTextColor(Color.WHITE);
    }


    private void init() {

        mainActivityLayout = (LinearLayout) findViewById(R.id.mainActivityLayout);
        shipmentScreenOpen = (LinearLayout) findViewById(R.id.nextScreen);
        userName = (EditText) findViewById(R.id.userName);
        password = (EditText) findViewById(R.id.passWord);
        navigateScreen = (TextView) findViewById(R.id.navigateScreen);
    }


}
