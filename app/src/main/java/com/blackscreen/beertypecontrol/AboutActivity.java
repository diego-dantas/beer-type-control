package com.blackscreen.beertypecontrol;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        setTitle(R.string.about);
    }

    public static void about(AppCompatActivity activity){

        Intent intent = new Intent(activity, AboutActivity.class);

        activity.startActivity(intent);
    }
}