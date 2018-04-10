package com.example.yonghyun.myapplication;


import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import java.io.IOException;
import java.util.zip.Inflater;


/**
 * A simple {@link Fragment} subclass.
 */
public class WebViewFragment1 extends Fragment {
    public static final String baseURL = "http://www.kets.or.kr";
    private ImageView imageView;




    public WebViewFragment1() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_web_view1, container, false);
        imageView = (ImageView)v.findViewById(R.id.imageWebView1);
        imageView.setImageResource(R.drawable.logo1);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                Uri u = Uri.parse(baseURL);
                intent.setData(u);
                startActivity(intent);
            }
        });


        return v;
    }

}
