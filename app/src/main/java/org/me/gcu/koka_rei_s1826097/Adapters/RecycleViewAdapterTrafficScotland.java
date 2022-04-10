package org.me.gcu.koka_rei_s1826097.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import org.me.gcu.koka_rei_s1826097.ItemDetailsActivity;
import org.me.gcu.koka_rei_s1826097.Modules.Item;
import org.me.gcu.koka_rei_s1826097.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class RecycleViewAdapterTrafficScotland extends RecyclerView.Adapter<RecycleViewAdapterTrafficScotland.CurrentIncidentViewHolder> {
    ArrayList<Item> items;
    Context context;

    public RecycleViewAdapterTrafficScotland(ArrayList<Item> items, Context context) {
        this.items = items;
        this.context = context;
    }


    @NonNull
    @Override
    public RecycleViewAdapterTrafficScotland.CurrentIncidentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycleview, parent ,false);
        return new CurrentIncidentViewHolder(itemView);
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull RecycleViewAdapterTrafficScotland.CurrentIncidentViewHolder holder, int position) {
        Item currentItem = items.get(position);
        holder.txtTitle.setText(currentItem.getTitle());
        holder.txtDescription.setText("");

        holder.txtTime.setText(currentItem.getPubDate());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToDetailsActivity = new Intent(context, ItemDetailsActivity.class);
                goToDetailsActivity.putExtra("Title", currentItem.getTitle());
                goToDetailsActivity.putExtra("Description", currentItem.getDescription());
                goToDetailsActivity.putExtra("Location", currentItem.getPoint());
                goToDetailsActivity.putExtra("Date", currentItem.getPubDate().toString());
                context.startActivity(goToDetailsActivity);
            }
        });
    }

    @Override
    public int getItemCount() {
       // Log.d("Title", String.valueOf(items.size()));
        return (null != items ? items.size() : 0);
    }

    public void filterList(ArrayList<Item> filteredlist) {
        items = filteredlist;
        notifyDataSetChanged();
    }

    public class CurrentIncidentViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle;
        TextView txtDescription;
        TextView txtTime;
        public CurrentIncidentViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtDescription = itemView.findViewById(R.id.txtDescription);
            txtTime = itemView.findViewById(R.id.txtTime);
        }
    }
}
