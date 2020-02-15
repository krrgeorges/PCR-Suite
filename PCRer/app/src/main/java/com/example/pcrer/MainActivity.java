package com.example.pcrer;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Raleway-Bold.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    "PCRERNotifChannel",
                    "PCRERNotif Channel",
                    NotificationManager.IMPORTANCE_HIGH
            );

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }

        Intent i = new Intent(this, LivePCR.class);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(i);
        }
        else{
            startService(i);
        }
        final Context c = this;
        try {
            String status = "";
            try {
                double live_pcr = Double.valueOf(getSharedPreferences("live_pcr"));
                double prev_pcr = Double.valueOf(getSharedPreferences("prev_pcr"));
                if (live_pcr > prev_pcr) {
                    status = "LONG";
                } else if (live_pcr < prev_pcr) {
                    status = "SHORT";
                } else {
                    status = "NEUTRAL";
                }

                double p_change = Math.round( ((live_pcr - prev_pcr) / prev_pcr *100)  *100.0 )/100.0 ;


                ((TextView) findViewById(R.id.live_pcr)).setText(getSharedPreferences("live_pcr"));
                ((TextView) findViewById(R.id.prev_pcr)).setText(getSharedPreferences("prev_pcr"));
                ((TextView) findViewById(R.id.status)).setText(status);
                ((TextView) findViewById(R.id.percent_change)).setText(String.valueOf(p_change));
                ((TextView) findViewById(R.id.expiry_date)).setText(getSharedPreferences("exp_date"));
            } catch (Exception e) {
            }

            ((Button) findViewById(R.id.refresh)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String status = "";
                    try {
                        double live_pcr = Double.valueOf(getSharedPreferences("live_pcr"));
                        double prev_pcr = Double.valueOf(getSharedPreferences("prev_pcr"));
                        if (live_pcr > prev_pcr) {
                            status = "LONG";
                        } else if (live_pcr < prev_pcr) {
                            status = "SHORT";
                        } else {
                            status = "NEUTRAL";
                        }


                        double p_change = Math.round( ((live_pcr - prev_pcr) / prev_pcr *100)  *100.0 )/100.0 ;


                        ((TextView) findViewById(R.id.live_pcr)).setText(getSharedPreferences("live_pcr"));
                        ((TextView) findViewById(R.id.prev_pcr)).setText(getSharedPreferences("prev_pcr"));
                        ((TextView) findViewById(R.id.status)).setText(status);
                        ((TextView) findViewById(R.id.percent_change)).setText(String.valueOf(p_change));
                        ((TextView) findViewById(R.id.expiry_date)).setText(getSharedPreferences("exp_date"));
                    } catch (Exception e) {

                    }
                }
            });

            this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    TimerTask tt = new TimerTask() {
                        @Override
                        public void run() {
                            try {
                                String status = "";
                                double live_pcr = Double.valueOf(getSharedPreferences("live_pcr"));
                                double prev_pcr = Double.valueOf(getSharedPreferences("prev_pcr"));
                                if (live_pcr > prev_pcr) {
                                    status = "LONG";
                                } else if (live_pcr < prev_pcr) {
                                    status = "SHORT";
                                } else {
                                    status = "NEUTRAL";
                                }

                                double p_change = Math.round( ((live_pcr - prev_pcr) / prev_pcr *100)  *100.0 )/100.0 ;

                                ((TextView) findViewById(R.id.live_pcr)).setText(getSharedPreferences("live_pcr"));
                                ((TextView) findViewById(R.id.prev_pcr)).setText(getSharedPreferences("prev_pcr"));
                                ((TextView) findViewById(R.id.status)).setText(status);
                                ((TextView) findViewById(R.id.percent_change)).setText(String.valueOf(p_change));
                                ((TextView) findViewById(R.id.expiry_date)).setText(getSharedPreferences("exp_date"));
                            } catch (Exception e) {
                            }
                        }
                    };
                    new Timer().scheduleAtFixedRate(tt,1000,5000);
                }
            });
        }
        catch (Exception e){
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        Intent i = new Intent(this, LivePCR.class);
        startService(i);
        try {
            String status = "";
            try {
                double live_pcr = Double.valueOf(getSharedPreferences("live_pcr"));
                double prev_pcr = Double.valueOf(getSharedPreferences("prev_pcr"));
                if (live_pcr > prev_pcr) {
                    status = "LONG";
                } else if (live_pcr < prev_pcr) {
                    status = "SHORT";
                } else {
                    status = "NEUTRAL";
                }
                ((TextView) findViewById(R.id.live_pcr)).setText(getSharedPreferences("live_pcr"));
                ((TextView) findViewById(R.id.prev_pcr)).setText(getSharedPreferences("prev_pcr"));
                ((TextView) findViewById(R.id.status)).setText(status);
                ((TextView) findViewById(R.id.expiry_date)).setText(getSharedPreferences("exp_date"));
            } catch (Exception e) {
            }

            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            String status = "";
                            double live_pcr = Double.valueOf(getSharedPreferences("live_pcr"));
                            double prev_pcr = Double.valueOf(getSharedPreferences("prev_pcr"));
                            if (live_pcr > prev_pcr) {
                                status = "LONG";
                            } else if (live_pcr < prev_pcr) {
                                status = "SHORT";
                            } else {
                                status = "NEUTRAL";
                            }
                            ((TextView) findViewById(R.id.live_pcr)).setText(getSharedPreferences("live_pcr"));
                            ((TextView) findViewById(R.id.prev_pcr)).setText(getSharedPreferences("prev_pcr"));
                            ((TextView) findViewById(R.id.status)).setText(status);
                            ((TextView) findViewById(R.id.expiry_date)).setText(getSharedPreferences("exp_date"));
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                }
            });
        }
        catch (Exception e){
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public String getSharedPreferences(String key){
        SharedPreferences sp = getApplicationContext().getSharedPreferences("pcrdata",MODE_PRIVATE);
        return sp.getString(key,"");
    }

    public void setSharedPreference(String key, String value){
        SharedPreferences sp = getApplicationContext().getSharedPreferences("pcrdata",MODE_PRIVATE);
        SharedPreferences.Editor spe = sp.edit();
        spe.putString(key,value);
        spe.commit();
    }
}
