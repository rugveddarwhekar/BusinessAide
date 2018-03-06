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


public class ClickedActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler{
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
      //  typeText.setText(type_text);
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


        sendData(rawResult.getText().toString());

        //MainActivity.tvresult.setText(rawResult.getText());
        onBackPressed();

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


    public void sendData(final String input)
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "http://businessaide.co.in/tp.php",
            new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Toast.makeText(ClickedActivity.this, "Respo : "+response, Toast.LENGTH_SHORT).show();
        }
    },
        new Response.ErrorListener(){
        @Override
        public void onErrorResponse(VolleyError error) {
        Toast.makeText(ClickedActivity.this,error.getMessage(),Toast.LENGTH_LONG).show();
        }
        }){
        public static final String TAG = "PV";
        @Override
        protected Map<String, String> getParams() throws AuthFailureError {

        Map<String,String> params = new HashMap<>();
        params.put("string", input);


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
