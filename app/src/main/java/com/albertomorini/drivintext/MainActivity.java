package com.albertomorini.drivintext;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    protected void populateList(String contacts[]){

        setContentView(R.layout.activity_main);
        ListView myL = findViewById(R.id.list);
        ArrayAdapter<String> arr = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,contacts);
        myL.setAdapter(arr);
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

        ContactPicker cp = new ContactPicker();
        Boolean esito = cp.checkPermission(this);
        Log.d("PERMISSION", esito.toString());



        ArrayList<String> contacts= cp.getContacts(this);
        //populateList(contacts.toArray().);
        if(esito){

    //            populateList();

        }else{
//            cp.onRequestPermissionsResult(1,);
        }



    }
}