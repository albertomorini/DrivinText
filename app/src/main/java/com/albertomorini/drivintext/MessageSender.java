package com.albertomorini.drivintext;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.telephony.SmsManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MessageSender {


    public Boolean checkPermissionSMS(Context ctx){
        if (ContextCompat.checkSelfPermission(ctx, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions((Activity) ctx, new String[]{Manifest.permission.SEND_SMS}, 1);
            return true;
        }else{
            return false;
        }
    }

    public Boolean sendTextSMS(String phoneNumber,String contactName, String textMessage ){
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, textMessage, null, null);
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
           // return false;
        }
    }

}
