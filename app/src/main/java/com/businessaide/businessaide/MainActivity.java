package com.businessaide.businessaide;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

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

public class MainActivity extends AppCompatActivity {

    EditText UsernameEt, PasswordEt;
    String flag ="0";
    String username;
    String password;

    JSONObject jbo;
    String user;
    JSONArray name = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UsernameEt = findViewById(R.id.username);
        PasswordEt = findViewById(R.id.password);
    }

//    public class BackgroundWorker extends AsyncTask<String, Void, String> {
//        Context context;
//        AlertDialog alertDialog;
//
//        BackgroundWorker(Context ctx) {
//            context = ctx;
//        }
//
//        @Override
//        protected String doInBackground(String... params) {
//            String type = params[0];
//            String login_url = "http://businessaide.co.in/login.php";
//            if (type.equals("login")) {
//                try {
//                    String user_name = params[1];
//                    String password = params[2];
//                    URL url = new URL(login_url);
//                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
//                    httpURLConnection.setRequestMethod("POST");
//                    httpURLConnection.setDoOutput(true);
//                    httpURLConnection.setDoInput(true);
//                    OutputStream outputStream = httpURLConnection.getOutputStream();
//                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
//                    String post_data = URLEncoder.encode("user_name", "UTF-8") + "=" + URLEncoder.encode(user_name, "UTF-8") + "&"
//                            + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
//                    bufferedWriter.write(post_data);
//                    bufferedWriter.flush();
//                    bufferedWriter.close();
//                    outputStream.close();
//                    InputStream inputStream = httpURLConnection.getInputStream();
//                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
//                    String result = "";
//                    String line = "";
//                    while ((line = bufferedReader.readLine()) != null) {
//                        result += line;
//                    }
//                    bufferedReader.close();
//                    inputStream.close();
//                    httpURLConnection.disconnect();
//                    return result;
//                } catch (MalformedURLException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPreExecute() {
//            alertDialog = new AlertDialog.Builder(context).create();
//            alertDialog.setTitle("Login Status");
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//
//
//
//            if(result.equals("1"))
//            {
//
//               // Log.i("error", "flag set "+result+"flag"+flag);
//                Intent i = new Intent(getApplicationContext(), EntryExitActivity.class);
//                startActivity(i);
//                Toast.makeText(context, "Welcome", Toast.LENGTH_SHORT).show();
//                finish();
//            }
//            else if(result.equals("0"))
//            {
//
//               // Log.i("error", "flag set "+result+"flag"+flag);
//                Toast.makeText(context, "Incorrect credentials", Toast.LENGTH_SHORT).show();
//            }
//
//        }
//
//        @Override
//        protected void onProgressUpdate(Void... values) {
//            super.onProgressUpdate(values);
//        }
//    }

    public void onLogin(View view) {
        UsernameEt = findViewById(R.id.username);
        PasswordEt = findViewById(R.id.password);
        username = UsernameEt.getText().toString();
        password = PasswordEt.getText().toString();

        try {
            StringRequest stringRequest = new StringRequest(Request.Method.POST,
                    "http://businessaide.co.in/login.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String result) {
                            if (!(result.equals("0"))) {

                                try {
                                    jbo = new JSONObject(result);
                                    name = jbo.getJSONArray("result");
                                    for(int i =0; i<name.length(); i++)
                                    {
                                        JSONObject c = name.getJSONObject(i);
                                        user = c.getString("name");

                                    }


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }




                                Log.i("error_response", result);
                                Intent i = new Intent(getApplicationContext(), EntryExitActivity.class);
                                i.putExtra("name_user", user);
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
                    params.put("user_name", username);
                    params.put("password", password);
                    return params;
                }


            };

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }catch(Exception e){
            Log.d("e", "onLogin: Exception");
        }
//
//        String type = "login";
//        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
//        backgroundWorker.execute(type, username, password);


    }

}