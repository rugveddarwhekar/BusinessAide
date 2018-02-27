package com.businessaide.businessaide;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class ClickedActivity extends AppCompatActivity {
    java.util.Date noteTS;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clicked);

        final TextView dateText = findViewById(R.id.dateText);
        final TextView timeText = findViewById(R.id.timeText);
        TextView typeText = findViewById(R.id.typeText);

        String type_text = getIntent().getExtras().getString("clickedButton");
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
                                System.out.println("Current time =&gt; "+c.getTime());

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
}
