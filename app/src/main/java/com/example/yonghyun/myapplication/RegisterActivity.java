package com.example.yonghyun.myapplication;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends Fragment {
    public RegisterActivity() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        final View v = inflater.inflate(R.layout.activity_register, container, false);
        EditText idText = (EditText)v.findViewById(R.id.idText);
        EditText passwordText = (EditText)v.findViewById(R.id.passwordText);
        EditText nameText = (EditText)v.findViewById(R.id.nameText);
        EditText ageText = (EditText)v.findViewById(R.id.ageText);

        Button registerButton = (Button)v.findViewById(R.id.registerButton);
        return v;
    }
}