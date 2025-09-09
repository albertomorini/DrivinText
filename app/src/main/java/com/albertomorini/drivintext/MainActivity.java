package com.albertomorini.drivintext;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
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

        setContentView(R.layout.activity_main);
        ListView UI_starredContacts_list = findViewById(R.id.list);
        ArrayAdapter<String> arr = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,contacts);
        UI_starredContacts_list.setAdapter(arr);

        UI_starredContacts_list.setOnItemClickListener((parent,view,position,id)->{
            Contact dest = starredContacts.get(position);
            Log.d(TAG, ">>"+dest.getName());
            selectedContact = position;
            Toast.makeText(MainActivity.this, "Clicked: " + dest.getName()+" /  " +dest.getPhoneNumber().toString(), Toast.LENGTH_SHORT).show();
        });

    }
    protected  void init_List_TextMessages(String[] messages){
        //TODO get the list and do the same of contacts
    }

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
      //  String[4] tmp = new String["Sono partito adesso","Sono arrivato a casa","Sto guidando, chiamami", "Non vedo i messaggi, se serve chiamami"];
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





    }
}