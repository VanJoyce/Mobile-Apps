package edu.monash.fit2081.statusapp;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    // reference to the database
    DatabaseReference mRef ;
    TextView mTV;
    DatabaseReference mCondition;
    ArrayAdapter itemsAdapter;

    ArrayList<ForecastStatus> data = new ArrayList<ForecastStatus>();
    ArrayList<String> dataKeys = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Get the ref
        mRef= FirebaseDatabase.getInstance().getReference();
        ListView listView = findViewById(R.id.list_status);
        //Add header to the listview
        listView.addHeaderView(getLayoutInflater().inflate(R.layout.forecast_header,listView,false),null,false);
        //setup the adapter
        itemsAdapter = new ForecastAdapter (this,  data);
        listView.setAdapter(itemsAdapter);
    }


    @Override
    protected void onStart() {
        super.onStart();
        mCondition = mRef.child("Week8/status");
        mCondition.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                data.add(dataSnapshot.getValue(ForecastStatus.class));
                dataKeys.add(dataSnapshot.getKey());
                itemsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                for (int i = 0; i < dataKeys.size(); i++) {
                    if (dataKeys.get(i).equals(dataSnapshot.getKey())) {
                        data.remove(i);
                        dataKeys.remove(i);
                        break;
                    }
                }

                ForecastStatus forecast = dataSnapshot.getValue(ForecastStatus.class);
                String str = "Item Removed\nStatus: " + forecast.getStatus() + "\nTS: " + forecast.getTimeStamp();
                Toast.makeText(getApplicationContext(), str, Toast.LENGTH_LONG).show();
                itemsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void btn1Handler(View view) {
        ForecastStatus forecastStatus=new ForecastStatus(getTimeStamp(),"Sunny");
        mCondition.push().setValue(forecastStatus);

    }

    public void btn2Handler(View view) {
        ForecastStatus forecastStatus=new ForecastStatus(getTimeStamp(),"Foggy");
        mCondition.push().setValue(forecastStatus);
    }

    public void btn3Handler(View view) {
        ForecastStatus forecastStatus=new ForecastStatus(getTimeStamp(),"Rainy");
        mCondition.push().setValue(forecastStatus);
    }

    String getTimeStamp(){
       return   new SimpleDateFormat("dd.MM.yyyy @ ss:mm:hh", Locale.UK).format(new Date());
    }


}
