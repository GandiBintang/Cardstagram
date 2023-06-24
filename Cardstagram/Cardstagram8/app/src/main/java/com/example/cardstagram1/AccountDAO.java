package com.example.cardstagram1;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface AccountDAO {

    @Insert
    void insertAll(Account... accounts);


    @Query("SELECT * FROM account" )
    LiveData<List<Account>> getAllAccounts();

    @Delete
    int delete(Account account);

    @Query("DELETE FROM account")
    void deleteAll();
}
