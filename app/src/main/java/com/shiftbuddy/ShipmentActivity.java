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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.shiftbuddy.Manager.Constants;
import com.shiftbuddy.Manager.Manager;
import com.shiftbuddy.bo.Shipment;

import java.io.File;

import biz.kasual.materialnumberpicker.MaterialNumberPicker;

public class ShipmentActivity extends AppCompatActivity {

    public static final String TAG = ShipmentActivity.class.getSimpleName();
    public final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034;
    public String photoFileName = "shiftbuddyphoto.jpg";

    LinearLayout container;
    LinearLayout nextScreen;
    LinearLayout uploadImage;
    MaterialNumberPicker length;
    MaterialNumberPicker width;
    MaterialNumberPicker height;
    MaterialNumberPicker weight;
    ImageView ivPreview;
    EditText description;
    boolean pictureLoaded = false;
    Bitmap bMapScaled;

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

    }

    private void initListeners() {

        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // create Intent to take a picture and return control to the calling application
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, getPhotoFileUri(photoFileName)); // set the image file name

                // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
                // So as long as the result is not null, it's safe to use the intent.
                if (intent.resolveActivity(getPackageManager()) != null) {
                    // Start the image capture intent to take photo
                    startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
                }
            }
        });

        nextScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkMandatory()) {
                    Intent shipmentIntent = new Intent(ShipmentActivity.this, PaymentActivity.class);
                    // Update Business object
                    if(pictureLoaded) {
                        //TODO : Pass image Uri and not actual image.
                        //Fix found on http://stackoverflow.com/questions/33238661/trying-to-share-image-to-facebook-using-intent-crashes-application
                        Shipment shipment = new Shipment(length.getValue(),height.getValue(),width.getValue(),
                                description.getText().toString(), bMapScaled);
                        shipmentIntent.putExtra(Constants.SHIPMENT_INTENT, shipment);
                    } else {
                        Shipment shipment = new Shipment(length.getValue(),height.getValue(),width.getValue(),
                                description.getText().toString());
                        shipmentIntent.putExtra(Constants.SHIPMENT_INTENT, shipment);
                    }

                    Log.d(TAG, "User values :" + " " + length.getValue()
                            + " " + width.getValue()
                            + " " + height.getValue());
                    //Open next screen
                    startActivity(shipmentIntent);
                } else {
                    Manager.openAuthenticationSnackbar("Please fill all required fields", container);
                }

            }
        });
    }

    // Returns the Uri for a photo stored on disk given the fileName
    public Uri getPhotoFileUri(String fileName) {
        // Only continue if the SD Card is mounted
        if (isExternalStorageAvailable()) {
            // Get safe storage directory for photos
            // Use `getExternalFilesDir` on Context to access package-specific directories.
            // This way, we don't need to request external read/write runtime permissions.
            File mediaStorageDir = new File(
                    getExternalFilesDir(Environment.DIRECTORY_PICTURES), TAG);

            // Create the storage directory if it does not exist
            if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
                Log.d(TAG, "failed to create directory");
            }

            // Return the file target for the photo based on filename
            return Uri.fromFile(new File(mediaStorageDir.getPath() + File.separator + fileName));
        }
        return null;
    }

    // Returns true if external storage for photos is available
    private boolean isExternalStorageAvailable() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Uri takenPhotoUri = getPhotoFileUri(photoFileName);
                // by this point we have the camera photo on disk
                Bitmap takenImage = BitmapFactory.decodeFile(takenPhotoUri.getPath());
                //scale bitmap
                bMapScaled = Bitmap.createScaledBitmap(takenImage, 600, 600, true);
                // Load the taken image into a preview
                ImageView ivPreview = (ImageView) findViewById(R.id.picture);
                if(bMapScaled!=null) {
                    pictureLoaded = true;
                    ivPreview.setImageBitmap(bMapScaled);
                }
            } else { // Result was a failure
                Toast.makeText(this, "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean checkMandatory() {
        if(description.getText()!=null) {
            if(!description.getText().toString().equals("")) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private void init() {
        container = (LinearLayout) findViewById(R.id.shipmentActivity);
        nextScreen = (LinearLayout)findViewById(R.id.next);
        uploadImage = (LinearLayout) findViewById(R.id.upload_image);
        length = (MaterialNumberPicker)findViewById(R.id.lngth);
        width = (MaterialNumberPicker) findViewById(R.id.wdth);
        height = (MaterialNumberPicker) findViewById(R.id.ht);
        weight = (MaterialNumberPicker) findViewById(R.id.wt);
        description = (EditText) findViewById(R.id.type);
    }

}


