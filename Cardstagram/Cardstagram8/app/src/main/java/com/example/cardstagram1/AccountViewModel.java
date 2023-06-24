package com.example.cardstagram1;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class AccountViewModel extends AndroidViewModel {
    AccountRepository repository;
    LiveData<List<Account>> accountList;

    public AccountViewModel(Application application) {
        super(application);
        repository = new AccountRepository(application);
        accountList = repository.getAccount();
    }

    LiveData<List<Account>> getAllAccount() {
        return accountList;
    }

    public void insertAccount(Account account) {
        repository.insert(account);
    }

    public void deleteAccount(Account account) {
        repository.delete(account);
    }

    public void deleteAllAccount() {
        repository.deleteAll();
    }
}
