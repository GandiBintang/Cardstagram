package com.example.cardstagram1;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class AccountRepository {

    AccountDAO accountDAO;
    AccountDatabase db;

    AccountRepository(Application application) {
        db = AccountDatabase.getDataBase(application);
        accountDAO = db.accountDAO();

    }

    LiveData<List<Account>> getAccount() {
        return accountDAO.getAllAccounts();
    }

    void insert(Account account) {
        new insertAsyncTask(accountDAO).execute(account);
    }

    private static class insertAsyncTask extends AsyncTask<Account,Void,Void> {
        private AccountDAO taskDAO;

        insertAsyncTask(AccountDAO accountDAO){
            taskDAO = accountDAO;
        }

        @Override
        protected Void doInBackground(Account... accounts) {
            taskDAO.insertAll(accounts[0]);
            return null;
        }
    }

    void delete(Account account) {
        new deleteAsyncTask(accountDAO).execute(account);
    }

    private static class deleteAsyncTask extends AsyncTask<Account,Void,Void> {
        private AccountDAO taskDAO;

        deleteAsyncTask(AccountDAO accountDAO){
            taskDAO = accountDAO;
        }

        @Override
        protected Void doInBackground(Account... accounts) {
            taskDAO.delete(accounts[0]);
            return null;
        }
    }

    void deleteAll() {
        new deleteAllAsyncTask(accountDAO).execute();
    }

    private static class deleteAllAsyncTask extends AsyncTask<Void,Void,Void> {
        private AccountDAO taskDAO;

        deleteAllAsyncTask(AccountDAO accountDAO){
            taskDAO = accountDAO;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            taskDAO.deleteAll();
            return null;
        }
    }

}
