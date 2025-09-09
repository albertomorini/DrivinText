package com.albertomorini.drivintext;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class ContactPicker {
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;
    private static final String TAG = "Oblivion.ContactPicker";


    public Boolean checkPermission(Context myContext) {
        if (ContextCompat.checkSelfPermission(myContext, Manifest.permission.READ_CONTACTS)
                == PackageManager.PERMISSION_GRANTED) {
            return true;
            // Proceed to read contacts
        } else {
            return false;
        }
    }


    protected void main() { //TODO
        //ASK PERMSISION
        //Load contacts
        // show contacts on View
        // pass the selected contact

    }

//    public String[] getStarredContacts(Context context) {
//        ContentResolver contentResolver = context.getContentResolver();
//        Cursor cursor = contentResolver.query(
//                ContactsContract.Contacts.CONTENT_URI,
//                null,
//                "starred=?",
//                new String[]{"1"},
//                null
//        );
//
//        String[] contacts = new String[cursor.getCount()]; //size by the size of number of starred contacts
//        int index =0;
//        if (cursor != null && cursor.getCount() > 0) {
//            while (cursor.moveToNext()) {
//                String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
//                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
//
//                // Check if contact has a phone number
//                int hasPhoneNumber = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
//
//
//                if (hasPhoneNumber > 0) {
//                    Cursor phoneCursor = contentResolver.query(
//                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
//                            null,
//                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
//                            new String[]{contactId},
//                            null
//                    );
//
//                    if (phoneCursor != null) {
//                        while (phoneCursor.moveToNext()) {
//                            String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
//                            contacts[index] = name + " (" + phoneNumber + ")";
//                            index++;
//                        }
//                        phoneCursor.close();
//                    }
//                }
//            }
//            cursor.close();
//        }
//        Log.d("CONTACTS", "getStarredContacts: " + contacts.length);
//        return contacts;
//    }


    public ArrayList<Contact> getStarredContacts(Context context) {
        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor = contentResolver.query(
                ContactsContract.Contacts.CONTENT_URI,
                null,
                "starred=?",
                new String[]{"1"},
                null
        );

        ArrayList<Contact>  contacts = new ArrayList<>(); //size by the size of number of starred contacts
        int index =0;
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                // Check if contact has a phone number
                int hasPhoneNumber = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));


                if (hasPhoneNumber > 0) {
                    Cursor phoneCursor = contentResolver.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{contactId},
                            null
                    );

                    if (phoneCursor != null) {
                        while (phoneCursor.moveToNext()) {
                            String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                            contacts.add(new Contact(name,phoneNumber));
                            index++;
                        }
                        phoneCursor.close();
                    }
                }
            }
            cursor.close();
        }
        Log.d(TAG, "total starred contacts: " + contacts.size());
        return contacts;
    }


}
