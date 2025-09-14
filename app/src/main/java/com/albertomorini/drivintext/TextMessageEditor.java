package com.albertomorini.drivintext;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class TextMessageEditor extends AppCompatActivity {
    private static final String TAG = "com.drivintext.MessageEditor";
    private String newTextMessage = "";

    private JSONArray allMessages = new JSONArray();

    private void storeNewMessage() {

        allMessages = StorageManager.storeMessages(this, allMessages, newTextMessage);
        //TODO: update
//        StorageManager.getMessagesStored(this);
        loadMessages();
    }

    protected void loadMessages() {
        //load messages from cache and give the possibility to remove them
        this.allMessages = StorageManager.getMessagesStored(this);
        Log.d(TAG, "Stored Messages: " + allMessages);

        RecyclerView recyclerView;
        MyAdapter adapter;
        List<String> data;

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        data = new ArrayList<>();
        for (int i = 0; i < allMessages.length(); i++) {
            try {
                data.add(allMessages.getString(i));
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }

        adapter = new MyAdapter(this, data);
        recyclerView.setAdapter(adapter);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.new_message_creator);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.MessageEditorView), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        loadMessages();
        EditText UI_NewMessage = findViewById(R.id.newMessage);

        UI_NewMessage.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                newTextMessage = s.toString();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                Log.d(TAG, "new message onchange: " + s.toString());
            }
        });

        Button btnSave = findViewById(R.id.saveNewMessage);
        btnSave.setOnClickListener(v -> {
            storeNewMessage();
        });

    }
}
