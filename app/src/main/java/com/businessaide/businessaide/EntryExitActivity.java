package com.businessaide.businessaide;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class EntryExitActivity extends AppCompatActivity {

    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_exit);

        String user_name = getIntent().getExtras().getString("name_user");

        TextView tv = findViewById(R.id.welcomeText);
        tv.setText("Hello, "+user_name);
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
    public void onEntryClicked(View view){
        Log.d("entry", "onEntryClicked: Clicked");
    }

    public void onExitClicked(View view){
        Log.d("exit", "onExitClicked: Clicked");
    }

    public void onDataClicked(View view){
        Log.d("data", "onDataClicked: Clicked");

    }
}
