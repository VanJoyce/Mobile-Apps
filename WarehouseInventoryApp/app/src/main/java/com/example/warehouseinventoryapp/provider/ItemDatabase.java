package com.example.warehouseinventoryapp.provider;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Item.class}, version = 1)
public abstract class ItemDatabase extends RoomDatabase { //Schema export directory is not provided to the annotation processor so we cannot export the schema
    public static final String ITEM_DATABASE_NAME = "item_database";
    public abstract ItemDao itemDao();
    // marking the instance as volatile to ensure atomic access to the variable
    private static volatile ItemDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4; // how many threads available to execute everything related to database
    static final ExecutorService databaseWriteExecutor = // to update database
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    static ItemDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ItemDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ItemDatabase.class, ITEM_DATABASE_NAME)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
