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

    public static JSONArray readMessagesStored(Context ctx) {
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

    public static JSONArray storeMessages(Context ctx, JSONArray allValues, String newValue) {
        try {
            allValues.put(newValue);
            JSONObject job = new JSONObject();

            try {
                job.put(KEY_MESSAGE_SET, allValues.toString());
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

            Log.d(TAG, "job b4 saving : " + job.toString());

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


}
