package com.businessaide.businessaide;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
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
import com.google.zxing.Result;
import com.google.zxing.client.android.Intents;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ExitClickedActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler{
    java.util.Date noteTS;
    private ZXingScannerView mScannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clicked);

        final TextView dateText = findViewById(R.id.dateText);
        final TextView timeText = findViewById(R.id.timeText);
        TextView typeText = findViewById(R.id.typeText);
        String type_text = getIntent().getExtras().getString("clickedButton");

        System.out.println(typeText);
        typeText.setText(type_text);



        Thread t = new Thread() {

            @Override
            public void run() {
                try {
                    do {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Calendar c = Calendar.getInstance();
                                System.out.println("Current time =&gt; " + c.getTime());

                                SimpleDateFormat df = new SimpleDateFormat("d MMM yyyy");
                                SimpleDateFormat df1 = new SimpleDateFormat("hh:mm:ss a");
                                String formattedDate = df.format(c.getTime());
                                String formattedDate1 = df1.format(c.getTime());

                                dateText.setText(formattedDate);
                                timeText.setText(formattedDate1);
                                //updateTextView();
                                System.out.println(formattedDate);
                                System.out.println(formattedDate1);

                            }
                        });
                    } while (!isInterrupted());
                } catch (InterruptedException e) {
                }
            }
        };

        t.start();
    }


    public void onQRscanClicked (View v) {
        mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
        setContentView(mScannerView);
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();

    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
//        mScannerView.startCamera();          // Start camera on resume
//    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }

    @Override
    public void handleResult(Result rawResult) {
        // Do something with the result here
        // Log.v("tag", rawResult.getText()); // Prints scan results
        // Log.v("tag", rawResult.getBarcodeFormat().toString()); // Prints the scan format (qrcode, pdf417 etc.)

        Toast.makeText(this, "result : "+ rawResult.getText().toString(), Toast.LENGTH_SHORT).show();

        GPSTracker gps = new GPSTracker(this);
        if (gps.canGetLocation()) { // gps enabled

        } // return boolean true/false

        double get_lat = gps.getLatitude(); // returns latitude
        double get_long = gps.getLongitude(); // returns longitude
        String get_Lat = String.valueOf(get_lat);
        String get_Long = String.valueOf(get_long);

        System.out.println("Latitude: " + get_Lat);
        System.out.println("Logitude: " + get_Long);

        Calendar c1 = Calendar.getInstance();
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat fmt1 = new SimpleDateFormat("HH:mm:ss");

        //Log.e("lat", get_Lat);
        //Log.e("long", get_Long);

        String formattedDate = fmt.format(c1.getTime());
        String formattedDate1 = fmt1.format(c1.getTime());
        sendData(rawResult.getText().toString(), formattedDate, formattedDate1, "exit", get_Lat, get_Long);

        //MainActivity.tvresult.setText(rawResult.getText());
//        onBackPressed();

        // If you would like to resume scanning, call this method below:
        //mScannerView.resumeCameraPreview(this);
    }
//
//    Calendar c = Calendar.getInstance();
//        System.out.println("Current time =&gt; "+c.getTime());
//
//        SimpleDateFormat df = new SimpleDateFormat("d MMM yyyy");
//        SimpleDateFormat df1 = new SimpleDateFormat("hh:mm:ss a");
//        String formattedDate = df.format(c.getTime());
//        String formattedDate1 = df1.format(c.getTime());
//
//        dateText.setText(formattedDate);
//        timeText.setText(formattedDate1);
//        typeText.setText(type_text);


    public void sendData(final String input, final String date, final String time, final String type, final String lat, final String Long)
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "http://businessaide.co.in/enterData.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(ExitClickedActivity.this, "Respo : "+response, Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ExitClickedActivity.this,error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }){
            public static final String TAG = "PV";
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();
                params.put("string", type + "!"+ input + "!" + date + "!" + time + "!" + lat + "," + Long);

                return params;
            }


        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}

//    private void updateTextView() {
//
//        noteTS = Calendar.getInstance().getTime();
//
//        String time = "hh:mm"; // 12:00
//        timeText.setText(DateFormat.format(time, noteTS));
//
//        String date = "dd MMMMM yyyy"; // 01 January 2013
//        dateText.setText(DateFormat.format(date, noteTS));
//    }
