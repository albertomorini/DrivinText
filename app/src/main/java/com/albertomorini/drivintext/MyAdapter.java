package com.albertomorini.drivintext;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private List<String> dataList;
    private Context context;

    public MyAdapter(Context context, List<String> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView itemText;
        Button itemButton;

        public MyViewHolder(View itemView) {
            super(itemView);
            itemText = itemView.findViewById(R.id.itemText);
            itemButton = itemView.findViewById(R.id.itemButton);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String text = dataList.get(position);
        holder.itemText.setText(text);

        holder.itemButton.setOnClickListener(v -> {
                    StorageManager sm = new StorageManager();
                    sm.removeItem(this.context, text);

                    Toast.makeText(context, "Deleted message: " + text, Toast.LENGTH_SHORT).show();
                    TextMessageEditor tme = new TextMessageEditor();
                    tme.loadMessages();

                }
        );
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
