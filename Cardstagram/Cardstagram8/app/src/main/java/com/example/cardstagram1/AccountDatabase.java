package com.example.cardstagram1;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Account.class}, version=1)
public abstract class AccountDatabase extends RoomDatabase {
    public abstract AccountDAO accountDAO();

    public static AccountDatabase INSTANCE;

    public static AccountDatabase getDataBase(final Context context) {
        if(INSTANCE == null) {
            synchronized (AccountDatabase.class) {
                if(INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),AccountDatabase.class,"account-database").fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
