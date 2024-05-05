package com.example.application052;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class SimpleAdapter extends RecyclerView.Adapter<SimpleAdapter.ViewHolder> {
    private List <String> items;
    private List <Integer> imageIds;

    public SimpleAdapter(List <String> items, List <Integer> imageIds){
        this.items = items;
        this.imageIds = imageIds;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String item = items.get(position);
        Integer imageview = imageIds.get(position);
        holder.textView.setText(item);
        holder.imageView.setImageResource(imageview);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;

        ViewHolder(View view) {
            super(view);
            textView = view.findViewById(R.id.TextView);
            imageView = view.findViewById(R.id.ImageView);
        }
    }
}