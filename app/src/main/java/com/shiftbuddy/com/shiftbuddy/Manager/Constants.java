package com.shiftbuddy.com.shiftbuddy.Manager;

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

public class Constants {

    /**
     * Shared_Preferences for user_data
     */
    public final static String SHARED_PREFS = "PREFS";
    public final static String USER_NAME_PREF = "USER_NAME_PREF";

    /**
     * Intents
     */
    public final static String SHIPMENT_INTENT = "shipmentIntent";
    /**
     * PHP Links to database. Currently built with separate PHP scripts to local database
     */
    public static final String CONNECT = "http://192.168.0.73/connection.php";
    public static final String LOGIN = "http://192.168.0.73/register.php";

    /**
     * BROADCAST IDENTITIES
     */
    public static final String BROADCAST_LOGIN_JSON = "shiftBuddyLoginCredentials";

    public static final String CREDENTIALS_FOR_USER = "loginJsonObject";
    public static final String WRONG_USER = "oops! Please try again!";
    public static final String INERNET_ERROR = "Issue with connection. Please try again.";
    public static final String USER_VALIDATION = "Please enter valid username and password";
}
