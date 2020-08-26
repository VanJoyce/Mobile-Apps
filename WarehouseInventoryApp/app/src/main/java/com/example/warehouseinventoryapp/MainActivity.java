package com.example.warehouseinventoryapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuView;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.warehouseinventoryapp.provider.Item;
import com.example.warehouseinventoryapp.provider.ItemViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawer;

//    ArrayList<String> itemArray = new ArrayList<>();
//    Gson gson = new Gson();

    private ItemViewModel mItemViewModel;
    MyRecyclerViewAdapter adapter;

    EditText name;
    EditText quantity;
    EditText cost;
    EditText description;
    ToggleButton frozen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer);

        adapter = new MyRecyclerViewAdapter();
        mItemViewModel = new ViewModelProvider(this).get(ItemViewModel.class);
        mItemViewModel.getAllItems().observe(this, newData -> {
            // adapter for RecyclerView
            adapter.setData(newData);
            adapter.notifyDataSetChanged();
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.dl);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nv);
        navigationView.setNavigationItemSelectedListener(new MyNavigationListener());

        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String itemData = itemToString();
//                itemArray.add(itemData);
                Item itemValues = createItemObject();
                mItemViewModel.insert(itemValues);
                Snackbar.make(v, "Item saved!", Snackbar.LENGTH_LONG).show();
                saveSharedPreferences();
            }
        });

        name = findViewById(R.id.nameInput);
        quantity = findViewById(R.id.quantityInput);
        cost = findViewById(R.id.costInput);
        description = findViewById(R.id.descriptionInput);
        frozen = findViewById(R.id.toggleButton);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS, Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_SMS}, 0);

        IntentFilter smsFilter = new IntentFilter(MySmsReceiver.SMS_FILTER);
        MyBroadcastReceiver myBroadcastReceiver = new MyBroadcastReceiver();
        registerReceiver(myBroadcastReceiver, smsFilter);
        Log.i("TAG", "onCreate");
    }

    class MyNavigationListener implements NavigationView.OnNavigationItemSelectedListener {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            // get the id of the selected item
            int id = item.getItemId();
            if(id == R.id.add) {
//                String itemData = itemToString();
//                itemArray.add(itemData);
                Item itemValues = createItemObject();
                mItemViewModel.insert(itemValues);
                String data = name.getText().toString();
                Toast.makeText(getApplicationContext(),"New item (" + data + ") has been added", Toast.LENGTH_SHORT).show();
            } else if (id == R.id.clear) {
                name.setText("");
                quantity.setText("");
                cost.setText("");
                description.setText("");
                frozen.setChecked(false);
            } else if (id == R.id.list) {
//                String dbStr = gson.toJson(itemArray);
//
//                SharedPreferences sP = getSharedPreferences("db1", 0);
//                SharedPreferences.Editor edit = sP.edit();
//                edit.putString("KEY_LIST", dbStr);
//                edit.apply();

                Intent myIntent = new Intent(getBaseContext(), MyRecyclerViewActivity.class);
                startActivity(myIntent);
            } else if (id == R.id.delete) {
                mItemViewModel.deleteAll();
            }
            saveSharedPreferences();
            // close the drawer
            drawer.closeDrawers();
            // tell the OS
            return true;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // get the id of the selected item
        int id = item.getItemId();
        if(id == R.id.opt_add) {
//            String itemData = itemToString();
//            itemArray.add(itemData);
            Item itemValues = createItemObject();
            mItemViewModel.insert(itemValues);
            String data = name.getText().toString();
            Toast.makeText(getApplicationContext(),"New item (" + data + ") has been added", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.opt_clear) {
            name.setText("");
            quantity.setText("");
            cost.setText("");
            description.setText("");
            frozen.setChecked(false);
        }
        saveSharedPreferences();
        return super.onOptionsItemSelected(item);
    }

    class MyBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String msg = intent.getStringExtra(MySmsReceiver.SMS_MSG_KEY);

            StringTokenizer sT = new StringTokenizer(msg, ";");
            String msgName = sT.nextToken();
            String msgQuantity = sT.nextToken();
            String msgCost = sT.nextToken();
            String msgDescription = sT.nextToken();
            boolean msgFrozen = Boolean.parseBoolean(sT.nextToken());

            name.setText(msgName);
            quantity.setText(msgQuantity);
            cost.setText(msgCost);
            description.setText(msgDescription);
            frozen.setChecked(msgFrozen);
            Log.i("TAG", "onReceiveActivity");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        quantity.setText("0");
        cost.setText("0.0");
        restoreSharedPreferences();
        Log.i("TAG", "onStart");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i("TAG", "onSaveInstanceState");
    }

    @Override
    protected void onRestoreInstanceState(Bundle inState) {
        super.onRestoreInstanceState(inState);
        Log.i("TAG", "onRestoreInstanceState");
    }

    public void clearItem(View view) {
        name.setText("");
        quantity.setText("");
        cost.setText("");
        description.setText("");
        frozen.setChecked(false);

        saveSharedPreferences();
    }

    public void addItem(View view) {
        String itemName = name.getText().toString();
//        String itemData = itemToString();
//        itemArray.add(itemData);

        Item item = createItemObject();
        mItemViewModel.insert(item);

        Toast addMessage = Toast.makeText(this,"New item (" + itemName + ") has been added", Toast.LENGTH_SHORT);
        addMessage.show();

        saveSharedPreferences();
    }

    public String itemToString() {
        String data = "";

        data += name.getText().toString() + ";";
        data += quantity.getText().toString() + ";";
        data += cost.getText().toString() + ";";
        data += description.getText().toString() + ";";
        data += Boolean.toString(frozen.isChecked());

        return data;
    }

    public Item createItemObject() {
        String itemName = name.getText().toString();
        int itemQuantity = Integer.parseInt(quantity.getText().toString());
        double itemCost = Double.parseDouble(cost.getText().toString());
        String itemDescription = description.getText().toString();
        Boolean itemFrozen = frozen.isChecked();

        return new Item(itemName, itemQuantity, itemCost, itemDescription, itemFrozen);
    }

    private void saveSharedPreferences() {
        SharedPreferences myData = getPreferences(0);
        SharedPreferences.Editor myEditor = myData.edit();
        myEditor.putString("name", name.getText().toString());
        myEditor.putString("quantity", quantity.getText().toString());
        myEditor.putString("cost", cost.getText().toString());
        myEditor.putString("description", description.getText().toString());
        myEditor.putBoolean("frozen", frozen.isChecked());
        myEditor.commit();
        Log.i("TAG", "saveSharedPreferences");

    }

    private void restoreSharedPreferences() {
        SharedPreferences myData = getPreferences(0);
        name.setText(myData.getString("name", ""));
        quantity.setText(myData.getString("quantity", "0"));
        cost.setText(myData.getString("cost", "0.0"));
        description.setText(myData.getString("description", ""));
        frozen.setChecked(myData.getBoolean("frozen", false));
        Log.i("TAG", "restoreSharedPreferences");

    }
}
