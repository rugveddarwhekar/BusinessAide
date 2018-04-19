package com.businessaide.businessaide;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.karan.churi.PermissionManager.PermissionManager;

import java.util.HashMap;
import java.util.Map;


public class EntryExitActivity extends AppCompatActivity {
    String user_name;
    SaveSharedPreference session;
    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

//        session = new SaveSharedPreference(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_exit);

        PermissionManager permissionManager = new PermissionManager() {};
        permissionManager.checkAndRequestPermissions(this);


//            //session.checkLogin();
//
//            // get user data from session
//            HashMap<String, String> user = session.getUserDetails();
//
//            // name
//            String name = user.get(SaveSharedPreference.KEY_NAME);

        user_name = getIntent().getExtras().getString("name_user");

            TextView tv = findViewById(R.id.welcomeText);
            tv.setText("Hello, " + user_name);
        }

    public void onBackPressed(){
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
        Intent i = new Intent(this, ClickedActivity.class);
        i.putExtra("clickedButton", "Entry");
        startActivity(i);
    }

    public void onExitClicked(View view){
        Log.d("exit", "onExitClicked: Clicked");
        Intent i = new Intent(this, ExitClickedActivity.class);
        i.putExtra("clickedButton", "Exit");
        startActivity(i);
    }


    public void sendtext(View v)
    {
        Intent i = new Intent(this, DataGen.class);
        i.putExtra("name_user", user_name);
        startActivity(i);
    }
//        StringRequest stringRequest = new StringRequest(Request.Method.POST,
//                "http://businessaide.co.in/tp.php",
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        Toast.makeText(EntryExitActivity.this, "Respo : "+response, Toast.LENGTH_SHORT).show();
//                    }
//                },
//                new Response.ErrorListener(){
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(EntryExitActivity.this,error.getMessage(),Toast.LENGTH_LONG).show();
//                    }
//                }){
//            public static final String TAG = "PV";
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//
//                Map<String,String> params = new HashMap<>();
//                params.put("string", "data gela");
//
//                return params;
//            }
//        };
//
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        requestQueue.add(stringRequest);
//    }


//    public void logoutPressed(View view){
//        session.logoutUser();
//    }
}

