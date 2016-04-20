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

public class Manager {

    public static boolean authenticateUser(String userName, String password) {

        if(userName.equals(Constants.userName) && password.equals(Constants.password)){
            return true;
        } else {
            return false;
        }
    }
}
