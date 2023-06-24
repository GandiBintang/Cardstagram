package com.example.cardstagram1;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;

import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;


public class RegisterFragment extends Fragment {


    View view;
    private Button button_SignIn, button_Register;
    private EditText edit_Username, edit_Password;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_register, container, false);
        button_SignIn = (Button) view.findViewById(R.id.btn_SignIn);
        button_Register = (Button) view.findViewById(R.id.btn_FLSignIn);
        edit_Password = (EditText) view.findViewById(R.id.password);
        edit_Username = (EditText) view.findViewById(R.id.username);

        // KE ACTIVITY BARU KALAU REGISTER EMAIL DAN PASSWORD BERHASIL
        button_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TO FILL WITH REGISTER CODE

                if(edit_Username.getText().toString().equals("") || edit_Password.getText().toString().equals("") ) {
                    Toast.makeText(getActivity(), "One of the field is empty", Toast.LENGTH_SHORT).show();
                } else if (!isValidEmail(edit_Username.getText().toString())) {
                    Toast.makeText(getActivity(), "Email Address is not valid", Toast.LENGTH_SHORT).show();
                } else if (edit_Password.getText().toString().length() < 5) {
                    Toast.makeText(getActivity(), "Password is too short", Toast.LENGTH_SHORT).show();
                } else if (isEmailPresent(edit_Username.getText().toString())) {
                    Toast.makeText(getActivity(), "Email has already been registered before", Toast.LENGTH_SHORT).show();
                }else {
                    AccountViewModel viewModel = ViewModelProviders.of(getActivity()).get(AccountViewModel.class);
                    Account account = new Account(edit_Username.getText().toString(),edit_Password.getText().toString());

                    viewModel.insertAccount(account);
                    Toast.makeText(getActivity(),"Successfully registered, please login now", Toast.LENGTH_SHORT).show();
                    viewModel.getAllAccount().observe(getActivity(),accountList -> {

                        Log.d("accounts", ":" + accountList.size());

                        for(Account list: accountList) {
                            Log.d("accounts",list.emailAddress + " " +list.password);
                        }
                    });
                    Intent intent = new Intent(getActivity(), HomeScreen.class);
                    startActivity(intent);
                }

            }
        });

        // PINDAH DARI RegisterFragment ke LoginFragment
        button_SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginFragment loginFragment = new LoginFragment();

                FragmentManager fragmentManager = getParentFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(
                                R.anim.fade_in,
                                R.anim.fade_out,
                                R.anim.fade_in,
                                R.anim.fade_out
                        )
                        .replace(R.id.fragment_container,loginFragment,null)
                        .setReorderingAllowed(true)
                        .commit();
            }
        });



        return view;
    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    public boolean isEmailPresent(String email) {
        boolean present = false;

        AccountViewModel viewModel = ViewModelProviders.of(getActivity()).get(AccountViewModel.class);
        List<Account> accounts = viewModel.getAllAccount().getValue();
        for(Account account: accounts) {
            if(account.emailAddress.equals(email)) {
                present = true;
            }
        }

        return present;
    }
}