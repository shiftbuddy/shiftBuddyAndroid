package com.shiftbuddy.bo;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

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

public class MoverVehicle implements Parcelable {

    private String packageHeight;
    private String packageLength;
    private String packageWidth;
    private String vehicleNumber;
    private String maxWeight;
    private String fromAddress;
    private String toAddress;
    private String pickupDate;
    private String deliveryDate;
    private Bitmap picture;

    public String getPackageHeight() {
        return packageHeight;
    }

    public void setPackageHeight(String packageHeight) {
        this.packageHeight = packageHeight;
    }

    public String getPackageLength() {
        return packageLength;
    }

    public void setPackageLength(String packageLength) {
        this.packageLength = packageLength;
    }

    public String getPackageWidth() {
        return packageWidth;
    }

    public void setPackageWidth(String packageWidth) {
        this.packageWidth = packageWidth;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public String getMaxWeight() {
        return maxWeight;
    }

    public void setMaxWeight(String maxWeight) {
        this.maxWeight = maxWeight;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public String getPickupDate() {
        return pickupDate;
    }

    public void setPickupDate(String pickupDate) {
        this.pickupDate = pickupDate;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Bitmap getPicture() {
        return picture;
    }

    public void setPicture(Bitmap picture) {
        this.picture = picture;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.packageHeight);
        dest.writeString(this.packageLength);
        dest.writeString(this.packageWidth);
        dest.writeString(this.vehicleNumber);
        dest.writeString(this.maxWeight);
        dest.writeString(this.fromAddress);
        dest.writeString(this.toAddress);
        dest.writeString(this.pickupDate);
        dest.writeString(this.deliveryDate);
        dest.writeParcelable(this.picture, 0);
    }

    public MoverVehicle() {
    }

    private MoverVehicle(Parcel in) {
        this.packageHeight = in.readString();
        this.packageLength = in.readString();
        this.packageWidth = in.readString();
        this.vehicleNumber = in.readString();
        this.maxWeight = in.readString();
        this.fromAddress = in.readString();
        this.toAddress = in.readString();
        this.pickupDate = in.readString();
        this.deliveryDate = in.readString();
        this.picture = in.readParcelable(Bitmap.class.getClassLoader());
    }

    public static final Parcelable.Creator<MoverVehicle> CREATOR = new Parcelable.Creator<MoverVehicle>() {
        public MoverVehicle createFromParcel(Parcel source) {
            return new MoverVehicle(source);
        }

        public MoverVehicle[] newArray(int size) {
            return new MoverVehicle[size];
        }
    };
}
