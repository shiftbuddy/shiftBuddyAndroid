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
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.shiftbuddy.Manager.Constants;
import com.shiftbuddy.Manager.HttpCalls;
import com.shiftbuddy.Manager.Manager;
import com.shiftbuddy.bo.Shipment;
import com.shiftbuddy.googlePlaces.AutoCompleteAdapter;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

public class PaymentActivity extends AppCompatActivity
        implements DatePickerDialog.OnDateSetListener, GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks{

    public  static final  String TAG = PaymentActivity.class.getSimpleName();

    LinearLayout paymentActivity;
    LinearLayout postPackage;
    AutoCompleteTextView fromAddress;
    EditText toAddress;
    TextView pickupDateButton;
    TextView pickUpDateText;
    TextView deliverDateButton;
    TextView deliverDateText;

    private AutoCompleteAdapter mAdapter;
    private int PLACE_PICKER_REQUEST = 1;

    boolean pickupOn = false;
    boolean deliverOn = false;

    Shipment shipment = new Shipment();
    HttpCalls httpCalls = new HttpCalls();
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle bundle = this.getIntent().getExtras();
        shipment = (Shipment)bundle.get(Constants.SHIPMENT_INTENT);
        Log.d(TAG,shipment.getDescription());
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        buildGooglePlacesAPIClient();
        init();
        initListeners();
    }

    @Override
    protected void onStop() {

        if( mGoogleApiClient != null && mGoogleApiClient.isConnected() ) {
            mGoogleApiClient.disconnect();
        }
        super.onStop();
    }

    private void buildGooglePlacesAPIClient() {

        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this,this)
                .build();
    }

    private void initListeners() {

        fromAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(PaymentActivity.this, LocationActivity.class);
                startActivity(myIntent);
            }
        });
        /*fromAddress.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AutoCompletePlace place = (AutoCompletePlace) parent.getItemAtPosition( position );
                findPlaceById( place.getId() );
            }
        });*/

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

    private void findPlaceById(String id) {

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
    }

    private void displayPlace(Place place) {

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
    }

    private void init() {
        paymentActivity = (LinearLayout) findViewById(R.id.payment_activity_layout);
        postPackage = (LinearLayout) findViewById(R.id.send_package);
        fromAddress = (AutoCompleteTextView) findViewById(R.id.address_from);
        mAdapter = new AutoCompleteAdapter( this );
        fromAddress.setAdapter( mAdapter );
        toAddress = (EditText) findViewById(R.id.address_to);
        pickupDateButton = (TextView) findViewById(R.id.pickupDateButton);
        pickUpDateText = (TextView) findViewById(R.id.pickupDateText);
        deliverDateButton = (TextView) findViewById(R.id.receiveDateButton);
        deliverDateText = (TextView) findViewById(R.id.receiveDateText);

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

    @Override
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

    }
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
