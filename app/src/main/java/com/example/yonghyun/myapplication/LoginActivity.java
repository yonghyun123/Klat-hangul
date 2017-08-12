package com.example.yonghyun.myapplication;

import android.app.Fragment;
import android.app.FragmentTransaction;
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

        final FragmentTransaction tr = getFragmentManager().beginTransaction();
        final Fragment RegisterActivityFragment = new RegisterActivity();

        EditText idText = (EditText)v.findViewById(R.id.idText);
        EditText passwordText = (EditText)v.findViewById(R.id.passwordText);

        Button loginButton = (Button)v.findViewById(R.id.loginButton);
        TextView registerButton = (TextView)v.findViewById(R.id.registerButton);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tr.replace(R.id.contentPanel, RegisterActivityFragment);
                tr.addToBackStack(null);
                tr.commit();
            }
        });
        return v;
    }
}