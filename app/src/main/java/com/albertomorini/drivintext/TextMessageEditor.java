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

public class TextMessageEditor extends AppCompatActivity {
    private static final String TAG = "com.drivintext.MessageEditor";
    private String newTextMessage = "";

    private void storeNewMessage(){
        //TODO: store into the cache
        loadMessages();
    }

    private void loadMessages(){
        //load messages from cache and give the possibility to remove them

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
                Log.d(TAG,"new message onchange: "+s.toString());
            }
        });

        Button btnSave = findViewById(R.id.saveNewMessage);
        btnSave.setOnClickListener(v->{
            storeNewMessage();
        });

    }
}
