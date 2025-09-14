package com.albertomorini.drivintext;

import androidx.annotation.NonNull;
import androidx.car.app.CarContext;
import androidx.car.app.Screen;
import androidx.car.app.model.Action;
import androidx.car.app.model.MessageTemplate;
import androidx.car.app.model.Template;

public class MyCarScreen extends Screen {
    public MyCarScreen(@NonNull CarContext carContext) {
        super(carContext);
    }

    @NonNull
    @Override
    public Template onGetTemplate() {
        // You could reuse data from MainActivity via shared ViewModel or StorageManager
        String title = "DrivinText";
        String message = "Choose an action";

        return new MessageTemplate.Builder(message)
                .setTitle(title)
                .addAction(
                        new Action.Builder()
                                .setTitle("Send SMS")
                                .setOnClickListener(() -> {
                                    // Reuse your SMS logic
                                    // Maybe call a helper or show a new screen
                                })
                                .build()
                )
                .build();
    }
}
