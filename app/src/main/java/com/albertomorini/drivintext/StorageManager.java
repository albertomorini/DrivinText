package com.albertomorini.drivintext;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class StorageManager {

    public static final String TAG = "com.albertomorini.StoreManager";
    public static final String PATHFILE = "store_message.json";
    public static final String KEY_MESSAGE_SET = "messages";

    public static JSONArray getMessagesStored(Context ctx) {
        File file = new File(ctx.getFilesDir(), PATHFILE);
        Log.d(TAG, "file: " + file.length());

        StringBuilder jsonString = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                jsonString.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            JSONObject jsonObject = new JSONObject(jsonString.toString());

            return new JSONArray(jsonObject.getString(KEY_MESSAGE_SET));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new JSONArray(); //if not exists return a new one
    }

    private static JSONArray storeMessages(Context ctx, JSONArray allValues) {
        try {
            JSONObject job = new JSONObject();

            try {
                job.put(KEY_MESSAGE_SET, allValues.toString());
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

//            Log.d(TAG, "job b4 saving : " + job.toString());

            File file = new File(ctx.getFilesDir(), PATHFILE);
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write(job.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }

            return job.getJSONArray(KEY_MESSAGE_SET);
        } catch (Exception e) {
            return new JSONArray();
        }
    }

    public static JSONArray storeMessages(Context ctx, JSONArray allValues, String newTextMessage) {
        try {
            // Avoid duplicates
            Boolean alreadyExisting = false;
            for (int i = 0; i < allValues.length() ; i++) {
                if(allValues.getString(i).equals(newTextMessage)){
                    alreadyExisting=true;
                }
            }
            if(!alreadyExisting){
                allValues.put(newTextMessage);
            }

            JSONObject job = new JSONObject();

            try {
                job.put(KEY_MESSAGE_SET, allValues.toString());
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

//            Log.d(TAG, "job b4 saving : " + job.toString());

            File file = new File(ctx.getFilesDir(), PATHFILE);
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write(job.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }

            return job.getJSONArray(KEY_MESSAGE_SET);
        } catch (Exception e) {
            return new JSONArray();
        }
    }

    protected static JSONArray removeItem(Context ctx, String text) {
        JSONArray dummy_storedMessages = getMessagesStored(ctx);
        for (int i = 0; i < dummy_storedMessages.length(); i++) {
            try {
                Log.d(TAG, "removing"+dummy_storedMessages.getString(i).toString().toLowerCase()+" / "+text.toLowerCase());
                if (dummy_storedMessages.getString(i).toString().toLowerCase().equals(text.toLowerCase())) {


                     dummy_storedMessages.remove(i);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }
        Log.d(TAG, "removeItem: "+dummy_storedMessages.toString());
        storeMessages(ctx, dummy_storedMessages);
        return dummy_storedMessages;
    }


}
