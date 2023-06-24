package com.example.cardstagram8;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Account {

    @PrimaryKey(autoGenerate = true)
    public int uid;


    @ColumnInfo(name = "email_address")
    public String emailAddress;


    @ColumnInfo(name = "password")
    public String password;

    public Account(String emailAddress, String password) {
        this.emailAddress = emailAddress;
        this.password = password;
    }
}
