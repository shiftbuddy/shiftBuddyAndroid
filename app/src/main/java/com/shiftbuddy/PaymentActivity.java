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
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.shiftbuddy.Manager.Constants;
import com.shiftbuddy.Manager.HttpCalls;
import com.shiftbuddy.Manager.Manager;
import com.shiftbuddy.bo.Shipment;
import com.shiftbuddy.googlePlaces.AutoCompleteAdapter;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class PaymentActivity extends AppCompatActivity
        implements DatePickerDialog.OnDateSetListener{

    public  static final  String TAG = PaymentActivity.class.getSimpleName();

    /**
     * Dispatch onResume() to fragments.  Note that for better inter-operation
     * with older versions of the platform, at the point of this call the
     * fragments attached to the activity are <em>not</em> resumed.  This means
     * that in some cases the previous state may still be saved, not allowing
     * fragment transactions that modify the state.  To correctly interact
     * with fragments in their proper state, you should instead override
     * {@link #onResumeFragments()}.
     */

    LinearLayout paymentActivity;
    LinearLayout postPackage;
    TextView fromAddress;
    TextView toAddress;
    TextView pickupDateButton;
    TextView pickUpDateText;
    TextView deliverDateButton;
    TextView deliverDateText;

    private AutoCompleteAdapter mAdapter;

    boolean pickupOn = false;
    boolean deliverOn = false;

    Shipment shipment = new Shipment();
    HttpCalls httpCalls = new HttpCalls();
    private GoogleApiClient mGoogleApiClient;

    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    Calendar c = Calendar.getInstance();
    String currentDate = dateFormat.format(c.getTime());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle bundle = this.getIntent().getExtras();
        shipment = (Shipment)bundle.get(Constants.SHIPMENT_INTENT);
        Log.d(TAG, shipment.getDescription());
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        //buildGooglePlacesAPIClient();
        init();
        initListeners();
        //updateAddress();
    }

    @Override
    protected void onResume() {
        updateAddress();
        super.onResume();
    }

    private void updateAddress() {
        if(shipment!=null) {
            if(null!=shipment.getSenderAddress()/*|| !shipment.getSenderAddress().equals("")*/) {
                fromAddress.setText(shipment.getSenderAddress());
            }

            if(null!=shipment.getReceiverAddress() /*|| !shipment.getReceiverAddress().equals("")*/) {
                toAddress.setText(shipment.getReceiverAddress());
            }
        }

    }

    @Override
    protected void onStop() {

        /*if( mGoogleApiClient != null && mGoogleApiClient.isConnected() ) {
            mGoogleApiClient.disconnect();
        }*/
        super.onStop();
    }

   /* private void buildGooglePlacesAPIClient() {

        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this,this)
                .build();
    }*/

    private void initListeners() {

        fromAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Manager.verifyDate(pickUpDateText.getText().toString(), deliverDateText.getText().toString())) {
                    Intent myIntent = new Intent(PaymentActivity.this, LocationActivity.class);
                    myIntent.putExtra(Constants.SHIPMENT_INTENT,shipment);
                    myIntent.putExtra(Constants.FROM_ADDRESS,true);
                    startActivity(myIntent);
                } else {
                    Manager.openAuthenticationSnackbar("Please enter valid date.", paymentActivity);
                }
            }
        });

        toAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Manager.verifyDate(pickUpDateText.getText().toString(), deliverDateText.getText().toString())) {
                    Intent myIntent = new Intent(PaymentActivity.this, LocationActivity.class);
                    myIntent.putExtra(Constants.SHIPMENT_INTENT,shipment);
                    myIntent.putExtra(Constants.TO_ADDRESS,true);
                    startActivity(myIntent);
                } else {
                    Manager.openAuthenticationSnackbar("Please enter valid date.", paymentActivity);
                }
            }
        });

        pickupDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickupOn = true;
                deliverOn = false;
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        PaymentActivity.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getFragmentManager(), "PickUpDatepickerdialog");
            }
        });

        deliverDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickupOn = false;
                deliverOn = true;
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        PaymentActivity.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getFragmentManager(), "DeliverDatepickerdialog");
            }
        });

        postPackage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!fromAddress.getText().toString().equals("") && !toAddress.getText().toString().equals("")) {
                    //validate address in Manager class
                    boolean addressVerification = Manager.verifyAddress(fromAddress.getText().toString(), toAddress.getText().toString());
                    if (addressVerification) {
                        shipment.setSenderAddress(fromAddress.getText().toString());
                        shipment.setReceiverAddress(toAddress.getText().toString());
                        //validate date in Manager class
                        if (Manager.verifyDate(pickUpDateText.getText().toString(), deliverDateText.getText().toString())) {
                            shipment.setSendingDate(pickUpDateText.getText().toString());
                            shipment.setReceptionDate(deliverDateText.getText().toString());
                            httpCalls.updateShipmentInDatabase(shipment, getApplicationContext());
                            Manager.openAuthenticationSnackbar("Your package has been successfully posted!", paymentActivity);
                        } else {
                            Manager.openAuthenticationSnackbar("Please enter valid date.", paymentActivity);
                        }
                    } else {
                        Manager.openAuthenticationSnackbar("Please enter valid location.", paymentActivity);
                    }
                } else {
                    Manager.openAuthenticationSnackbar("Please enter valid location.", paymentActivity);
                }

            }
        });
    }

    /*private void findPlaceById(String id) {

        if( TextUtils.isEmpty(id) || mGoogleApiClient == null || !mGoogleApiClient.isConnected() )
            return;

        Places.GeoDataApi.getPlaceById( mGoogleApiClient, id ) .setResultCallback(new ResultCallback<PlaceBuffer>() {
            @Override
            public void onResult(PlaceBuffer places) {
                if (places.getStatus().isSuccess()) {
                    Place place = places.get(0);
                    displayPlace(place);
                    fromAddress.setText("");
                    mAdapter.clear();
                }

                //Release the PlaceBuffer to prevent a memory leak
                places.release();
            }
        });
    }*/

    /*private void displayPlace(Place place) {

        String content = "";
        if( !TextUtils.isEmpty( place.getName() ) ) {
            content += "Name: " + place.getName() + "\n";
        }
        if( !TextUtils.isEmpty( place.getAddress() ) ) {
            content += "Address: " + place.getAddress() + "\n";
        }
        if( !TextUtils.isEmpty( place.getPhoneNumber() ) ) {
            content += "Phone: " + place.getPhoneNumber();
        }

        toAddress.setText( content );
    }*/

    private void init() {
        paymentActivity = (LinearLayout) findViewById(R.id.payment_activity_layout);
        postPackage = (LinearLayout) findViewById(R.id.send_package);
        fromAddress = (TextView) findViewById(R.id.address_from);
        //mAdapter = new AutoCompleteAdapter( this );
       // fromAddress.setAdapter( mAdapter );
        toAddress = (TextView) findViewById(R.id.address_to);
        pickupDateButton = (TextView) findViewById(R.id.pickupDateButton);
        pickUpDateText = (TextView) findViewById(R.id.pickupDateText);
        pickUpDateText.setText(currentDate);//initialize with current date
        deliverDateButton = (TextView) findViewById(R.id.receiveDateButton);
        deliverDateText = (TextView) findViewById(R.id.receiveDateText);
        deliverDateText.setText(currentDate);//initialize with current date

    }

    /**
     * @param view        The view associated with this listener.
     * @param year        The year that was set.
     * @param monthOfYear The month that was set (0-11) for compatibility
     *                    with {@link Calendar}.
     * @param dayOfMonth  The day of the month that was set.
     */
    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        if(pickupOn && !deliverOn) {
            pickUpDateText.setText((monthOfYear+1)+"/"+dayOfMonth+"/"+year);
        } else if (!pickupOn && deliverOn) {
            deliverDateText.setText((monthOfYear+1)+"/"+dayOfMonth+"/"+year);
        }

    }

    /*@Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Manager.openAuthenticationSnackbar("Problem in internet connection.",paymentActivity);
    }

    @Override
    public void onConnected(Bundle bundle) {
        if( mAdapter != null )
            mAdapter.setGoogleApiClient( mGoogleApiClient );
    }

    @Override
    public void onConnectionSuspended(int i) {

    }*/
}

/*    private void openAddressEnterSnackBar() {
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
    }*/
