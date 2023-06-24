package com.example.cardstagram1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.room.Room;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        AccountViewModel viewModel = ViewModelProviders.of(this).get(AccountViewModel.class);

        viewModel.getAllAccount().observe(this,accountList -> {

            Log.d("accounts", ":" + accountList.size());

            for(Account list: accountList) {
                Log.d("accounts",list.emailAddress + " " +list.password);
            }
        });



        RegisterFragment registerFragment = new RegisterFragment();

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container,registerFragment,null)
                .setReorderingAllowed(true)
                .commit();



    }
}