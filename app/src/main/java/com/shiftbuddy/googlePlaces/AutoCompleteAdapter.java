package com.shiftbuddy.googlePlaces;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.AutocompletePredictionBuffer;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @(#) ShiftBuddy
 * <p>
 * Copyright (C) ShiftBuddy, 2016
 * All rights reserved.
 * <p>
 * This software is the proprietary information of
 * shiftbuddy ("Confidential Information").
 * Author : Dinesh Vaithyalingam Gangatharan
 * Source : https://github.com/tutsplus/Android-PlacesAPI/blob/master/app/src/main/java/com/tutsplus/placesapi/AutoCompleteAdapter.java
 */

public class AutoCompleteAdapter extends ArrayAdapter<AutoCompletePlace> {

    private GoogleApiClient mGoogleApiClient;

    public AutoCompleteAdapter( Context context ) {
        super(context, 0);
    }

    @Override
    public View getView( int position, View convertView, ViewGroup parent ) {
        ViewHolder holder;

        if( convertView == null ) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from( getContext() ).inflate( android.R.layout.simple_list_item_1, parent, false  );
            holder.text = (TextView) convertView.findViewById( android.R.id.text1 );
            convertView.setTag( holder );
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.text.setText( getItem( position ).getDescription() );

        return convertView;
    }

    public void setGoogleApiClient(GoogleApiClient googleApiClient) {
        this.mGoogleApiClient = googleApiClient;
    }

    private class ViewHolder {
        TextView text;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                if( mGoogleApiClient == null || !mGoogleApiClient.isConnected() ) {
                    Toast.makeText( getContext(), "Not connected", Toast.LENGTH_SHORT ).show();
                    return null;
                }

                clear();

                if(constraint!=null) {
                    displayPredictiveResults(constraint.toString());
                }


                return null;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                notifyDataSetChanged();
            }
        };
    }

    private void displayPredictiveResults( String query )
    {
        //Southwest corner to Northeast corner.
        LatLngBounds bounds = new LatLngBounds(
                new LatLng( 39.906374, -105.122337 ),
                new LatLng( 39.949552, -105.068779 ) );

        //Filter: https://developers.google.com/places/supported_types#table3
        List<Integer> filterTypes = new ArrayList<Integer>();

        //filterTypes.add(Place.TYPE_COUNTRY);
        filterTypes.add(Place.TYPE_GEOCODE);

        filterTypes.add( Place.TYPE_ESTABLISHMENT );
        filterTypes.add(Place.TYPE_NEIGHBORHOOD);
        filterTypes.add(Place.TYPE_STREET_ADDRESS);

        filterTypes.add(Place.TYPE_TRAIN_STATION);
        filterTypes.add(Place.TYPE_TAXI_STAND);
        filterTypes.add(Place.TYPE_SUBWAY_STATION);
        filterTypes.add(Place.TYPE_BUS_STATION);
        filterTypes.add(Place.TYPE_AIRPORT);

        filterTypes.add(Place.TYPE_RESTAURANT);
        filterTypes.add(Place.TYPE_BAR);
        filterTypes.add(Place.TYPE_BAKERY);
        filterTypes.add(Place.TYPE_BICYCLE_STORE);
        filterTypes.add(Place.TYPE_CAFE);
        filterTypes.add(Place.TYPE_GAS_STATION);
        filterTypes.add(Place.TYPE_NIGHT_CLUB);

        filterTypes.add(Place.TYPE_SUBLOCALITY);
        filterTypes.add(Place.TYPE_SUBLOCALITY_LEVEL_1);
        filterTypes.add(Place.TYPE_SUBLOCALITY_LEVEL_2);
        filterTypes.add(Place.TYPE_SUBLOCALITY_LEVEL_3);
        filterTypes.add(Place.TYPE_SUBLOCALITY_LEVEL_4);
        filterTypes.add(Place.TYPE_SUBLOCALITY_LEVEL_5);

        filterTypes.add(Place.TYPE_POSTAL_CODE);
        filterTypes.add(Place.TYPE_POSTAL_TOWN);
        filterTypes.add(Place.TYPE_POSTAL_CODE_PREFIX);
        filterTypes.add(Place.TYPE_POST_BOX);


                Places.GeoDataApi.getAutocompletePredictions(mGoogleApiClient, query, bounds,
                        AutocompleteFilter.create(filterTypes))
                        .setResultCallback(
                                new ResultCallback<AutocompletePredictionBuffer>() {
                                    @Override
                                    public void onResult(AutocompletePredictionBuffer buffer) {

                                        if (buffer == null)
                                            return;

                                        if (buffer.getStatus().isSuccess()) {
                                            for (AutocompletePrediction prediction : buffer) {
                                                //Add as a new item to avoid IllegalArgumentsException when buffer is released
                                                add(new AutoCompletePlace(prediction.getPlaceId(), prediction.getDescription()));
                                            }
                                        }

                                        //Prevent memory leak by releasing buffer
                                        buffer.release();
                                    }
                                }, 60, TimeUnit.SECONDS);
    }
}
