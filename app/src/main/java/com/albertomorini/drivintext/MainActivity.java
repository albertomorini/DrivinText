package com.albertomorini.drivintext;

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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Oblivion.DrivingText.MainActivity";

    private ArrayList<Contact> starredContacts = new ArrayList<>();
    private String[] textMessages = new String[10];
    /// ////////////////////////////////////

    private int selectedContact = -1;
    private int selectedMessage = -1;


    protected void init_List_StarredContact(String contacts[]){
        Log.d(TAG, "init_List_StarredContact: "+contacts.length);
        setContentView(R.layout.activity_main);
        ListView UI_starredContacts_list = findViewById(R.id.list);
        ArrayAdapter<String> arr = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,contacts);
        UI_starredContacts_list.setAdapter(arr);

        UI_starredContacts_list.setOnItemClickListener((parent,view,position,id)->{
            Contact dest = starredContacts.get(position);

            selectedContact = position;
            Toast.makeText(MainActivity.this, "Clicked: " + dest.getName()+" /  " +dest.getPhoneNumber().toString(), Toast.LENGTH_SHORT).show();
        });

    }
    protected  void init_List_TextMessages(String[] messages){
        //TODO get the list and do the same of contacts

        ListView UI_messages_list = findViewById(R.id.listMessages);
        ArrayAdapter<String> arr = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,messages);
        UI_messages_list.setAdapter(arr);

        UI_messages_list.setOnItemClickListener((parent,view,position,id)->{
            String chosenTextMessage = textMessages[position];

            selectedMessage = position;
            Toast.makeText(MainActivity.this, "Chosen text: " +chosenTextMessage, Toast.LENGTH_SHORT).show();
        });
    }
    /// ///////////////////////////////////////////////////////////////////////////////////////////////////////
    private void load_StarredContacts(){
        ContactPicker cp = new ContactPicker();
        Boolean esito = cp.checkPermission(this);
        if(esito){
            starredContacts = cp.getStarredContacts(this);
            String[] dummyContacts = starredContacts.stream().flatMap(c-> c.parseToString()).toArray(String[]::new); // convert in a dummy array of strings
            init_List_StarredContact(dummyContacts);
        }else{
            Toast.makeText(MainActivity.this, "Contacts permission required", Toast.LENGTH_SHORT).show();
        }

    }
    private void load_TextMessages(){
       textMessages = new String[]{
               "Sono partito adesso","Sono arrivato a casa","Sto guidando, chiamami", "Non vedo i messaggi, se serve chiamami"
       };
       init_List_TextMessages(textMessages);
    }

    /// ///////

    private void sendMessage(){
        MessageSender sender = new MessageSender();
        if(sender.checkPermissionSMS(this)){
            Contact dummyContact = starredContacts.get(selectedContact);

            Boolean res_sending = sender.sendTextSMS(
                    dummyContact.getPhoneNumber(),
                    dummyContact.getName(),
                    textMessages[selectedMessage]
            );
            if(res_sending){
                Toast.makeText(MainActivity.this, "SMS sent", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(MainActivity.this, "ERROR sending SMS", Toast.LENGTH_SHORT).show();
            }
        }else{
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
            // Code to send SMS or do something
            sendMessage();
        });


    }
}