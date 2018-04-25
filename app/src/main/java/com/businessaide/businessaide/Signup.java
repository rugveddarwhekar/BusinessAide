package com.businessaide.businessaide;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Signup extends AppCompatActivity {

    EditText UsernameEt, PasswordEt, NameEt, EmailEt, PhoneEt;
    String flag ="0";
    String username, password, name, email, phone;

    JSONObject jbo;
    String user;
    JSONArray name1 = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
    }

    public void onLoginClick(View view){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    public void onSignupClicked(View view) {
        NameEt = findViewById(R.id.name);
        EmailEt = findViewById(R.id.email);
        PhoneEt = findViewById(R.id.phone);
        UsernameEt = findViewById(R.id.username);
        PasswordEt = findViewById(R.id.password);
        username = UsernameEt.getText().toString();
        password = PasswordEt.getText().toString();
        name = NameEt.getText().toString();
        email = EmailEt.getText().toString();
        phone = PhoneEt.getText().toString();

        if (!isNetworkAvailable()) {
            Log.e("PV", "not connected");
            AlertDialog.Builder builder;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder = new AlertDialog.Builder(Signup.this, android.R.style.Theme_Material_Dialog_Alert);
            } else {
                builder = new AlertDialog.Builder(Signup.this);
            }
            builder.setTitle("No Internet!")
                    .setMessage("Internet is a necessity!")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent i = new Intent(Settings.ACTION_SETTINGS);
                            // i.setClassName("com.android.phone","com.android.phone.NetworkSetting");
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(i);
                            finish();
                        }
                    })
//                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
//                            // do nothing
//                        }
//                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
//            new AlertDialog(this, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
//                    .setTitleText("No Internet")
//                    .setContentText("Let's fix the satellites !")
//                    .setCustomImage(R.drawable.no_internet)
//                    .setConfirmText("FIX")
//                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                        @Override
//                        public void onClick(SweetAlertDialog sDialog) {
//
//                            Intent i = new Intent(Settings.ACTION_SETTINGS);
//                            // i.setClassName("com.android.phone","com.android.phone.NetworkSetting");
//                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                            startActivity(i);
//                            finish();
//
//                        }
//                    })
//                    .show();

        } else {

            try {
                StringRequest stringRequest = new StringRequest(Request.Method.POST,
                        "http://businessaide.co.in/signup.php",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String result) {

                                if (!(result.equals("0"))) {

                                    try {
                                        jbo = new JSONObject(result);
                                        name1 = jbo.getJSONArray("result");
                                        for (int i = 0; i < name.length(); i++) {
                                            JSONObject c = name1.getJSONObject(i);
                                            user = c.getString("name");
                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }


                                    Log.i("error_response", result);

                                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
//                                    i.putExtra("name_user", user);
                                    Toast.makeText(getApplicationContext(), "Signup Success", Toast.LENGTH_SHORT).show();
                                    //SaveSharedPreference.setUserName(MainActivity.this, user);
                                    startActivity(i);
                                    //Toast.makeText(getApplicationContext(), "Welcome "+user, Toast.LENGTH_SHORT).show();
                                    finish();
                                } else if (result.equals("0")) {

                                    // Log.i("error", "flag set "+result+"flag"+flag);
                                    Toast.makeText(getApplicationContext(), "Incorrect credentials", Toast.LENGTH_SHORT).show();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();

                            }
                        }) {
                    public static final String TAG = "PV";


                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        Map<String, String> params = new HashMap<>();
                        params.put("fullname1", name);
                        params.put("email1", email);
                        params.put("phone1", phone);
                        params.put("user_name1", username);
                        params.put("password1", password);
                        return params;
                    }


                };

                RequestQueue requestQueue = Volley.newRequestQueue(this);
                requestQueue.add(stringRequest);
            } catch (Exception e) {
                Log.d("e", "onLogin: Exception");
            }
//

        }

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
