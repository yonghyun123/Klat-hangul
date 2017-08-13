package com.example.yonghyun.myapplication;


import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import java.util.zip.Inflater;


/**
 * A simple {@link Fragment} subclass.
 */
public class WebViewFragment1 extends Fragment {
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
        imageView.setImageResource(R.drawable.klat_logo);

        return v;
    }

}
