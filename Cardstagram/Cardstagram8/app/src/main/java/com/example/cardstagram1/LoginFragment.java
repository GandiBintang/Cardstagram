package com.example.cardstagram1;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;


public class LoginFragment extends Fragment {

    View view;

    private Button button_LFSignIn, button_LFRegister;
    private EditText edit_LFUsername, edit_LFPassword;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_login, container, false);

        button_LFRegister = (Button) view.findViewById(R.id.btn_FLRegister);
        button_LFSignIn = (Button) view.findViewById(R.id.btn_FLSignIn);
        edit_LFUsername = (EditText) view.findViewById(R.id.username);
        edit_LFPassword = (EditText) view.findViewById(R.id.password);


        // CHECK KALAU EMAIL DAN PASSWORD LOGIN OKAY, BOLEH KE ACTIVITY SELANJUTNYA
        button_LFSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = edit_LFUsername.getText().toString();
                String password = edit_LFPassword.getText().toString();
                if(username.equals("") || password.equals("")) {
                    Toast.makeText(getActivity(),"One of the field is empty", Toast.LENGTH_SHORT).show();
                }else if(isLoginOkay(username,password)) {
                    Toast.makeText(getActivity(), "Sign In Successfull", Toast.LENGTH_SHORT).show();

                    // DISINI BOLEH BIKIN REDIRECT KE NEW ACTIVITYNYA
                    Intent intent = new Intent(getActivity(), HomeScreen.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity(),"Either Email or Password is wrong, please check again.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        button_LFRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegisterFragment registerFragment = new RegisterFragment();

                FragmentManager fragmentManager = getParentFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(
                                R.anim.fade_in,
                                R.anim.fade_out,
                                R.anim.fade_in,
                                R.anim.fade_out
                        )
                        .replace(R.id.fragment_container,registerFragment,null)
                        .setReorderingAllowed(true)

                        .commit();
            }
        });


        return view;
    }

    public boolean isLoginOkay(String email, String password) {
        boolean present = false;

        AccountViewModel viewModel = ViewModelProviders.of(getActivity()).get(AccountViewModel.class);
        List<Account> accounts = viewModel.getAllAccount().getValue();
        for(Account account: accounts) {
            if(account.emailAddress.equals(email) && account.password.equals(password)) {
                present = true;
            }
        }

        return present;
    }
}