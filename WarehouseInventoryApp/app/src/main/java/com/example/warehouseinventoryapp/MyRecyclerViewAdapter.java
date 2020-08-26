package com.example.warehouseinventoryapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.warehouseinventoryapp.provider.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {
//    ArrayList<String> itemArray;
    List<Item> itemArray = new ArrayList<>();

//    public MyRecyclerViewAdapter(ArrayList<String> data) {
//        itemArray = data;
//    }

    public MyRecyclerViewAdapter() {} // empty constructor because no need to pass item but still need a constructor

    public void setData(List<Item> data) {
        itemArray = data;
    }

    @NonNull
    @Override
    public MyRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyRecyclerViewAdapter.ViewHolder holder, int position) {
        Item item = itemArray.get(position);

//        StringTokenizer sT = new StringTokenizer(itemArray.get(position), ";");
//        String name = sT.nextToken();
//        String quantity = sT.nextToken();
//        String cost = sT.nextToken();
//        String description = sT.nextToken();
//        String frozen = sT.nextToken();

        holder.itemId.setText(Integer.toString(item.getItemID()));
        holder.itemName.setText(item.getItemName());
        holder.itemQuantity.setText(Integer.toString(item.getItemQuantity()));
        holder.itemCost.setText(Double.toString(item.getItemCost()));
        holder.itemDescription.setText(item.getItemDescription());
        holder.itemFrozen.setText(Boolean.toString(item.isItemFrozen()));
    }

    @Override
    public int getItemCount() {
        return itemArray.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public View itemView;
        public TextView itemId;
        public TextView itemName;
        public TextView itemQuantity;
        public TextView itemCost;
        public TextView itemDescription;
        public TextView itemFrozen;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            itemId = itemView.findViewById(R.id.item_id);
            itemName = itemView.findViewById(R.id.item_name);
            itemQuantity = itemView.findViewById(R.id.item_quantity);
            itemCost = itemView.findViewById(R.id.item_cost);
            itemDescription = itemView.findViewById(R.id.item_description);
            itemFrozen = itemView.findViewById(R.id.item_frozen);

        }
    }
}
