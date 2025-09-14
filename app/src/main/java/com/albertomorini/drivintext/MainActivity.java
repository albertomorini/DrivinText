package com.albertomorini.drivintext;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Oblivion.DrivingText.MainActivity";

    private ArrayList<Contact> starredContacts = new ArrayList<>();
    private JSONArray storedTextMessage = new JSONArray();

    /// ////////////////////////////////////

    private int selectedContact = -1;
    private int selectedMessage = -1;


    protected void init_List_StarredContact(String contacts[]) {
        Log.d(TAG, "init_List_StarredContact: " + contacts.length);
        setContentView(R.layout.activity_main);
        ListView UI_starredContacts_list = findViewById(R.id.list);
        ArrayAdapter<String> arr = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, contacts);
        UI_starredContacts_list.setAdapter(arr);

        UI_starredContacts_list.setOnItemClickListener((parent, view, position, id) -> {
            if (selectedContact == position) {
                selectedContact = -1;
                view.setBackgroundColor(Color.TRANSPARENT);
            } else {
                selectedContact = position;
                view.setBackgroundColor(Color.parseColor("#e0fff4"));
            }

        });

    }

    protected void init_List_TextMessages(String[] messages) {
        for (String s : messages) {
            Log.d(TAG, "SS: " + s);
        }
        ListView UI_messages_list = findViewById(R.id.listMessages);
        ArrayAdapter<String> arr = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, messages);
        UI_messages_list.setAdapter(arr);

        UI_messages_list.setOnItemClickListener((parent, view, position, id) -> {
            if (position == selectedMessage) {
                view.setBackgroundColor(Color.TRANSPARENT);
                selectedMessage = -1;
            } else {
                selectedMessage = position;
                view.setBackgroundColor(Color.parseColor("#e0fff4"));
            }
        });
    }

    /// ///////////////////////////////////////////////////////////////////////////////////////////////////////
    private void load_StarredContacts() {
        ContactPicker cp = new ContactPicker();
        Boolean esito = cp.checkPermission(this);
        if (esito) {
            starredContacts = cp.getStarredContacts(this);
            String[] dummyContacts = starredContacts.stream().flatMap(c -> c.parseToString()).toArray(String[]::new); // convert in a dummy array of strings
            init_List_StarredContact(dummyContacts);
        } else {
            Toast.makeText(MainActivity.this, "Contacts permission required", Toast.LENGTH_SHORT).show();
        }

    }

    private void load_TextMessages() {
        StorageManager sm = new StorageManager();
        JSONArray storedMessage = sm.getMessagesStored(this);
        storedTextMessage = storedMessage;
        String[] dummy_messages = new String[storedMessage.length()];
        for (int i = 0; i < storedMessage.length(); i++) {
            try {
                dummy_messages[i] = storedMessage.getString(i);
            } catch (JSONException e) {
                dummy_messages[i] = "";
            }
        }
        init_List_TextMessages(dummy_messages);
    }

    /// ///////

    private void sendMessage() {
        MessageSender sender = new MessageSender();
        if (sender.checkPermissionSMS(this)) {
            Contact dummyContact = starredContacts.get(selectedContact);
            Boolean res_sending = false;
            try {
                res_sending = sender.sendTextSMS(
                        dummyContact.getPhoneNumber(),
                        dummyContact.getName(),
                        storedTextMessage.getString(selectedMessage)
                );
            } catch (Exception e) {
                throw new RuntimeException(e);
            }


            if (res_sending) {

                Toast.makeText(MainActivity.this, "SMS sent to: " + dummyContact.getName(), Toast.LENGTH_SHORT).show();
                load_StarredContacts();
                load_TextMessages();
                selectedContact = -1;
                selectedMessage = -1;
            } else {
                Toast.makeText(MainActivity.this, "ERROR sending SMS", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(MainActivity.this, "Send SMS permission required", Toast.LENGTH_SHORT).show();

        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        load_StarredContacts();
        load_TextMessages();


        Button sendSmsButton = findViewById(R.id.buttonSendSMS);
        sendSmsButton.setOnClickListener(v -> {
            sendMessage();
        });

        FloatingActionButton newMessage = findViewById(R.id.floating_newMessage);
        newMessage.setOnClickListener(v -> {
            Intent intentNewMessage = new Intent(this, TextMessageEditor.class);
            startActivity(intentNewMessage);
        });

    }
}