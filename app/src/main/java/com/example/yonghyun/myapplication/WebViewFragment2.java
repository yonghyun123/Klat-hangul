package com.example.yonghyun.myapplication;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class WebViewFragment2 extends Fragment {

    private static final String baseURL = "http://stdweb2.korean.go.kr/main.jsp";

    private ImageView imageView;

    public WebViewFragment2() {
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
        final View v = inflater.inflate(R.layout.fragment_web_view2, container, false);
        imageView = (ImageView)v.findViewById(R.id.imageWebView2);
        imageView.setImageResource(R.drawable.logo2);

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
