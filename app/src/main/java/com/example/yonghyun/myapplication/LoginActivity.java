package com.example.yonghyun.myapplication;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends Fragment {
    public LoginActivity() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        final View v = inflater.inflate(R.layout.activity_login, container, false);
        EditText idText = (EditText)v.findViewById(R.id.idText);
        EditText passwordText = (EditText)v.findViewById(R.id.passwordText);

        Button loginButton = (Button)v.findViewById(R.id.loginButton);
        TextView registerButton = (TextView)v.findViewById(R.id.registerButton);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contentPanel, new RegisterActivity()).commit();
            }
        });
        return v;
    }
}