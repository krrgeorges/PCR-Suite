package com.example.bankpcr;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
        Intent i = new Intent(this, LivePCR.class);
        startService(i);
        
        try {
            String status = "";
            try {
                double blive_pcr = Double.valueOf(getSharedPreferences("blive_pcr"));
                double bprev_pcr = Double.valueOf(getSharedPreferences("bprev_pcr"));
                double p_change = Math.round( ((blive_pcr - bprev_pcr) / bprev_pcr *100)  *100.0 )/100.0 ;
                if (blive_pcr > bprev_pcr) {
                    status = "LONG";
                } else if (blive_pcr < bprev_pcr) {
                    status = "SHORT";
                } else {
                    status = "NEUTRAL";
                }
                ((TextView)findViewById(R.id.bpc)).setText(Double.toString(p_change));
                ((TextView) findViewById(R.id.prev_pcr)).setText(getSharedPreferences("bprev_pcr"));
                ((TextView) findViewById(R.id.status)).setText(status);
                ((TextView) findViewById(R.id.expiry_date)).setText(getSharedPreferences("bexp_date"));
            } catch (Exception e) {
            }

            ((Button) findViewById(R.id.refresh)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String status = "";
                    try {
                        double blive_pcr = Double.valueOf(getSharedPreferences("blive_pcr"));
                        double bprev_pcr = Double.valueOf(getSharedPreferences("bprev_pcr"));
                        double p_change = Math.round( ((blive_pcr - bprev_pcr) / bprev_pcr *100)  *100.0 )/100.0 ;
                        if (blive_pcr > bprev_pcr) {
                            status = "LONG";
                        } else if (blive_pcr < bprev_pcr) {
                            status = "SHORT";
                        } else {
                            status = "NEUTRAL";
                        }
                        ((TextView)findViewById(R.id.bpc)).setText(Double.toString(p_change));
                        ((TextView) findViewById(R.id.live_pcr)).setText(getSharedPreferences("blive_pcr"));
                        ((TextView) findViewById(R.id.prev_pcr)).setText(getSharedPreferences("bprev_pcr"));
                        ((TextView) findViewById(R.id.status)).setText(status);
                        ((TextView) findViewById(R.id.expiry_date)).setText(getSharedPreferences("bexp_date"));
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
                                double blive_pcr = Double.valueOf(getSharedPreferences("blive_pcr"));
                                double bprev_pcr = Double.valueOf(getSharedPreferences("bprev_pcr"));
                                double p_change = Math.round( ((blive_pcr - bprev_pcr) / bprev_pcr *100)  *100.0 )/100.0 ;
                                if (blive_pcr > bprev_pcr) {
                                    status = "LONG";
                                } else if (blive_pcr < bprev_pcr) {
                                    status = "SHORT";
                                } else {
                                    status = "NEUTRAL";
                                }
                                ((TextView)findViewById(R.id.bpc)).setText(Double.toString(p_change));
                                ((TextView) findViewById(R.id.live_pcr)).setText(getSharedPreferences("blive_pcr"));
                                ((TextView) findViewById(R.id.prev_pcr)).setText(getSharedPreferences("bprev_pcr"));
                                ((TextView) findViewById(R.id.status)).setText(status);
                                ((TextView) findViewById(R.id.expiry_date)).setText(getSharedPreferences("bexp_date"));
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
                double blive_pcr = Double.valueOf(getSharedPreferences("blive_pcr"));
                double bprev_pcr = Double.valueOf(getSharedPreferences("bprev_pcr"));
                double p_change = Math.round( ((blive_pcr - bprev_pcr) / bprev_pcr *100)  *100.0 )/100.0 ;
                if (blive_pcr > bprev_pcr) {
                    status = "LONG";
                } else if (blive_pcr < bprev_pcr) {
                    status = "SHORT";
                } else {
                    status = "NEUTRAL";
                }
                ((TextView)findViewById(R.id.bpc)).setText(Double.toString(p_change));
                ((TextView) findViewById(R.id.live_pcr)).setText(getSharedPreferences("blive_pcr"));
                ((TextView) findViewById(R.id.prev_pcr)).setText(getSharedPreferences("bprev_pcr"));
                ((TextView) findViewById(R.id.status)).setText(status);
                ((TextView) findViewById(R.id.expiry_date)).setText(getSharedPreferences("bexp_date"));
            } catch (Exception e) {
            }

            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            String status = "";
                            double blive_pcr = Double.valueOf(getSharedPreferences("blive_pcr"));
                            double bprev_pcr = Double.valueOf(getSharedPreferences("bprev_pcr"));
                            double p_change = Math.round( ((blive_pcr - bprev_pcr) / bprev_pcr *100)  *100.0 )/100.0 ;
                            if (blive_pcr > bprev_pcr) {
                                status = "LONG";
                            } else if (blive_pcr < bprev_pcr) {
                                status = "SHORT";
                            } else {
                                status = "NEUTRAL";
                            }
                            ((TextView)findViewById(R.id.bpc)).setText(Double.toString(p_change));
                            ((TextView) findViewById(R.id.live_pcr)).setText(getSharedPreferences("blive_pcr"));
                            ((TextView) findViewById(R.id.prev_pcr)).setText(getSharedPreferences("bprev_pcr"));
                            ((TextView) findViewById(R.id.status)).setText(status);
                            ((TextView) findViewById(R.id.expiry_date)).setText(getSharedPreferences("bexp_date"));
                        } catch (Exception e) {
                        }
                    }

                }
            });
        }
        catch (Exception e){
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public String getSharedPreferences(String key){
        SharedPreferences sp = getApplicationContext().getSharedPreferences("bpcrdata",MODE_PRIVATE);
        return sp.getString(key,"");
    }

    public void setSharedPreference(String key, String value){
        SharedPreferences sp = getApplicationContext().getSharedPreferences("bpcrdata",MODE_PRIVATE);
        SharedPreferences.Editor spe = sp.edit();
        spe.putString(key,value);
        spe.commit();
    }
}
