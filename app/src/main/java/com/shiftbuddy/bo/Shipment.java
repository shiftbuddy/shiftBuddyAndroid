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

/*
  Business Object class for Shipment.
  This class holds the information that the sender sends for a shipment of a package
 */
public class Shipment implements Parcelable {

    private String username;
    private int length;
    private int width;
    private int height;
    private Bitmap picture;
    private String description;
    private String senderAddress;
    private String receiverAddress;
    private String receptionDate;
    private String sendingDate;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Bitmap getPicture() {
        return picture;
    }

    public void setPicture(Bitmap picture) {
        this.picture = picture;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSenderAddress() {
        return senderAddress;
    }

    public void setSenderAddress(String senderAddress) {
        this.senderAddress = senderAddress;
    }

    public String getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    public String getReceptionDate() {
        return receptionDate;
    }

    public void setReceptionDate(String receptionDate) {
        this.receptionDate = receptionDate;
    }

    public String getSendingDate() {
        return sendingDate;
    }

    public void setSendingDate(String sendingDate) {
        this.sendingDate = sendingDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.username);
        dest.writeInt(this.length);
        dest.writeInt(this.width);
        dest.writeInt(this.height);
        dest.writeParcelable(this.picture, 0);
        dest.writeString(this.description);
        dest.writeString(this.senderAddress);
        dest.writeString(this.receiverAddress);
        dest.writeString(this.receptionDate);
        dest.writeString(this.sendingDate);
    }

    public Shipment() {
    }

    public Shipment(int length, int height, int width, String description) {
        setLength(length);
        setHeight(height);
        setWidth(width);
        setDescription(description);
    }

    public Shipment(int length, int height, int width, String description,Bitmap picture) {
        setLength(length);
        setHeight(height);
        setWidth(width);
        setDescription(description);
        setPicture(picture);
    }

    private Shipment(Parcel in) {
        this.username = in.readString();
        this.length = in.readInt();
        this.width = in.readInt();
        this.height = in.readInt();
        this.picture = in.readParcelable(Bitmap.class.getClassLoader());
        this.description = in.readString();
        this.senderAddress = in.readString();
        this.receiverAddress = in.readString();
        this.receptionDate = in.readString();
        this.sendingDate = in.readString();
    }

    public static final Parcelable.Creator<Shipment> CREATOR = new Parcelable.Creator<Shipment>() {
        public Shipment createFromParcel(Parcel source) {
            return new Shipment(source);
        }

        public Shipment[] newArray(int size) {
            return new Shipment[size];
        }
    };
}
