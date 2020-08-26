package com.example.warehouseinventoryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.warehouseinventoryapp.provider.ItemViewModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MyRecyclerViewActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
//    RecyclerView.Adapter adapter;
    MyRecyclerViewAdapter adapter;

//    ArrayList<String> itemArray;

    private ItemViewModel mItemViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_recycler_view);

//        SharedPreferences sP = getSharedPreferences("db1", 0);
//        String dbStr = sP.getString("KEY_LIST", "");
//        Type type = new TypeToken<ArrayList<String>>() {}.getType();
//        Gson gson = new Gson();
//        itemArray = gson.fromJson(dbStr, type);

        recyclerView = findViewById(R.id.recyclerView);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

//        adapter = new MyRecyclerViewAdapter(itemArray);
        adapter = new MyRecyclerViewAdapter();
        recyclerView.setAdapter(adapter);

        mItemViewModel = new ViewModelProvider(this).get(ItemViewModel.class);
        mItemViewModel.getAllItems().observe(this, newData -> {
            adapter.setData(newData);
            adapter.notifyDataSetChanged();
        });

        Log.i("TAG", "onCreateRecycler");
    }
}
