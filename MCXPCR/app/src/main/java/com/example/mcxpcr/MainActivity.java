package com.example.mcxpcr;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.http.SslError;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final WebView ms = (WebView) findViewById(R.id.main_stuff);

        ms.getSettings().setJavaScriptEnabled(true);
        ms.getSettings().setLoadWithOverviewMode(true);
        ms.getSettings().setUseWideViewPort(true);

        ms.getSettings().setSupportZoom(true);
        ms.getSettings().setBuiltInZoomControls(true);
        ms.getSettings().setDisplayZoomControls(false);

        ms.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        ms.setScrollbarFadingEnabled(false);

//        ms.loadUrl("https://www.mcxindia.com/market-data/option-chain");

        final Context c = this;

//        ms.setWebViewClient(new WebViewClient(){
//
//            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//            @Override
//            public void onPageFinished(final WebView view, String url) {
//                Toast.makeText(getApplicationContext(),"Loaded", Toast.LENGTH_LONG).show();
//
//                final String js = "javascript:(function(){"+
//                        "document.getElementById(\"cph_InnerContainerRight_C008_hdnCommodity\").value = \"CRUDEOIL\";"+
//                        "document.getElementById(\"ctl00_cph_InnerContainerRight_C008_ddlSymbols\").value = \"CRUDEOIL\";"+
//                        "var Expiries = $(vTick).filter(function (idx, n) { return n.Symbol === $(\"#ctl00_cph_InnerContainerRight_C008_ddlSymbols\").val(); });$(\"#cph_InnerContainerRight_C008_hdnSymbols\").attr(\"value\", $(\"#ctl00_cph_InnerContainerRight_C008_ddlSymbols\").val());$(\"#ddlExpiry\").empty();$(\"#ddlExpiry\").siblings(\".cs-options\").children(\"ul\").empty();$(\"#ddlExpiry\").append($(\"<option value=\\\"Select\\\">Select Expiry</option>\"));$.each(Expiries, function (idx, Data) {$(\"#ddlExpiry\").append($(\"<option></option>\").attr(\"value\", Data.ExpiryDate).text(Data.ExpiryDate));});"+
//                        "GetData(\"/backpage.aspx/GetOptionChain\", \"{'Commodity':'CRUDEOIL','Expiry':'\"+document.getElementById(\"ddlExpiry\").childNodes[1].value+\"'}\", OnSuccessOptionChain, OnFailedOptionChain);"+
//                        "})()";
//
//                view.evaluateJavascript(js, new ValueCallback<String>() {
//                    @Override
//                    public void onReceiveValue(String s) {
//                        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
//                        boolean detsObtained = false;
//
//                        ((MainActivity) c).runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                try {
//                                    new Thread().sleep(10000);
//                                    String pjs = "javascript:(function(){"+
//                                            "return document.getElementById(\"tblOptionChain\").getElementsByTagName(\"tr\")[document.getElementById(\"tblOptionChain\").getElementsByTagName(\"tr\").length-1].getElementsByTagName(\"td\")[1].innerText+\" \"+document.getElementById(\"tblOptionChain\").getElementsByTagName(\"tr\")[document.getElementById(\"tblOptionChain\").getElementsByTagName(\"tr\").length-1].getElementsByTagName(\"td\")[document.getElementById(\"tblOptionChain\").getElementsByTagName(\"tr\")[document.getElementById(\"tblOptionChain\").getElementsByTagName(\"tr\").length-1].getElementsByTagName(\"td\").length-1].innerText+\" \"+document.getElementById(\"ddlExpiry\").childNodes[1].value;"+
//                                            "})()";
//                                    view.evaluateJavascript(pjs, new ValueCallback<String>() {
//                                        @Override
//                                        public void onReceiveValue(String s) {
//                                            try {
//                                                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
//                                                String[] parts = s.split(" ");
//                                                double calls = Double.valueOf(parts[0].replace("\"",""));
//                                                double puts = Double.valueOf(parts[1].replace("\"",""));
//
//                                                double live_pcr = Math.round((puts / calls) * 100.0) / 100.0;
//
//                                                String exp_date = parts[2];
//
//                                                double prev_pcr = 99999;
//
//                                                if (getSP("prev_pcr") != "") {
//                                                    prev_pcr = Double.valueOf(getSP("prev_pcr"));
//                                                }
//
//                                                double percent_change = Math.round(((live_pcr - prev_pcr) / prev_pcr * 100) * 100.0) / 100.0;
//
//                                                String status = "";
//
//                                                if (prev_pcr > live_pcr) {
//                                                    status = "SHORT";
//                                                } else if (prev_pcr < live_pcr) {
//                                                    status = "LONG";
//                                                } else {
//                                                    status = "NEUTRAL";
//                                                }
//
//                                                int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
//
//                                                if (hour > 0 && hour < 9) {
//                                                    setSP("prev_pcr", String.valueOf(live_pcr));
//                                                }
//
//                                                ((TextView) findViewById(R.id.prev_pcr)).setText(String.valueOf(prev_pcr));
//                                                ((TextView) findViewById(R.id.live_pcr)).setText(String.valueOf(live_pcr));
//                                                ((TextView) findViewById(R.id.p_change)).setText(String.valueOf(percent_change));
//                                                ((TextView) findViewById(R.id.p_change)).setText(String.valueOf(percent_change));""))
//                                                ((TextView) findViewById(R.id.status)).setText(status);
//
//
//                                                ((Button) findViewById(R.id.refresh)).setEnabled(true);
//
//                                            }
//                                            catch (Exception e){
//                                                Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
//                                            }
//
//                                        }});
//                                } catch (InterruptedException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        });
//
//
//
//
//                    }
//                });
//        }
//            @Override
//            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
//                if (handler != null){
//                    handler.proceed();
//                } else {
//                    super.onReceivedSslError(view, null, error);
//                }
//            }
//        });
        ms.getSettings().setJavaScriptEnabled(true);
        ms.getSettings().setDomStorageEnabled(true);

        ((Button) findViewById(R.id.refresh)).setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                ((Button) findViewById(R.id.refresh)).setEnabled(false);

                final String js = "javascript:(function(){"+
                        "document.getElementById(\"cph_InnerContainerRight_C008_hdnCommodity\").value = \"CRUDEOIL\";"+
                        "document.getElementById(\"ctl00_cph_InnerContainerRight_C008_ddlSymbols\").value = \"CRUDEOIL\";"+
                        "var Expiries = $(vTick).filter(function (idx, n) { return n.Symbol === $(\"#ctl00_cph_InnerContainerRight_C008_ddlSymbols\").val(); });$(\"#cph_InnerContainerRight_C008_hdnSymbols\").attr(\"value\", $(\"#ctl00_cph_InnerContainerRight_C008_ddlSymbols\").val());$(\"#ddlExpiry\").empty();$(\"#ddlExpiry\").siblings(\".cs-options\").children(\"ul\").empty();$(\"#ddlExpiry\").append($(\"<option value=\\\"Select\\\">Select Expiry</option>\"));$.each(Expiries, function (idx, Data) {$(\"#ddlExpiry\").append($(\"<option></option>\").attr(\"value\", Data.ExpiryDate).text(Data.ExpiryDate));});"+
                        "GetData(\"/backpage.aspx/GetOptionChain\", \"{'Commodity':'CRUDEOIL','Expiry':'\"+document.getElementById(\"ddlExpiry\").childNodes[1].value+\"'}\", OnSuccessOptionChain, OnFailedOptionChain);"+
                        "})()";

                ms.evaluateJavascript(js, new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String s) {
                        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
                        boolean detsObtained = false;

                        ((MainActivity) c).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    new Thread().sleep(10000);
                                    String pjs = "javascript:(function(){"+
                                            "return document.getElementById(\"tblOptionChain\").getElementsByTagName(\"tr\")[document.getElementById(\"tblOptionChain\").getElementsByTagName(\"tr\").length-1].getElementsByTagName(\"td\")[1].innerText+\" \"+document.getElementById(\"tblOptionChain\").getElementsByTagName(\"tr\")[document.getElementById(\"tblOptionChain\").getElementsByTagName(\"tr\").length-1].getElementsByTagName(\"td\")[document.getElementById(\"tblOptionChain\").getElementsByTagName(\"tr\")[document.getElementById(\"tblOptionChain\").getElementsByTagName(\"tr\").length-1].getElementsByTagName(\"td\").length-1].innerText+\" \"+document.getElementById(\"ddlExpiry\").childNodes[1].value;"+
                                            "})()";
                                    ms.evaluateJavascript(pjs, new ValueCallback<String>() {
                                        @Override
                                        public void onReceiveValue(String s) {
                                            try {
                                                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                                                String[] parts = s.split(" ");
                                                double calls = Double.valueOf(parts[0].replace("\"",""));
                                                double puts = Double.valueOf(parts[1].replace("\"",""));

                                                double live_pcr = Math.round((puts / calls) * 100.0) / 100.0;

                                                String exp_date = parts[2];

                                                double prev_pcr = 99999;

                                                if (getSP("prev_pcr") != "") {
                                                    prev_pcr = Double.valueOf(getSP("prev_pcr"));
                                                }

                                                double percent_change = Math.round(((live_pcr - prev_pcr) / prev_pcr * 100) * 100.0) / 100.0;

                                                String status = "";

                                                if (prev_pcr > live_pcr) {
                                                    status = "SHORT";
                                                } else if (prev_pcr < live_pcr) {
                                                    status = "LONG";
                                                } else {
                                                    status = "NEUTRAL";
                                                }

                                                int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);

                                                if (hour > 0 && hour < 9) {
                                                    setSP("prev_pcr", String.valueOf(live_pcr));
                                                }

                                                ((TextView) findViewById(R.id.prev_pcr)).setText(String.valueOf(prev_pcr));
                                                ((TextView) findViewById(R.id.live_pcr)).setText(String.valueOf(live_pcr));
                                                ((TextView) findViewById(R.id.p_change)).setText(String.valueOf(percent_change));
                                                ((TextView) findViewById(R.id.exp_date)).setText(exp_date);
                                                ((TextView) findViewById(R.id.status)).setText(status);


                                                ((Button) findViewById(R.id.refresh)).setEnabled(true);

                                            }
                                            catch (Exception e){
                                                Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                                            }

                                        }});
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        });




                    }
                });
            }
        });

        ((Button) findViewById(R.id.refresh)).setEnabled(true);

    }

    public void setSP(String key,String value){
        SharedPreferences sp = this.getSharedPreferences("mcxdata",MODE_PRIVATE);
        SharedPreferences.Editor e = sp.edit();
        e.putString(key,value);
        e.commit();
    }

    public String getSP(String key){
        SharedPreferences sp = this.getSharedPreferences("mcxdata",MODE_PRIVATE);
        return sp.getString(key,"");
    }

    @Override
    protected void onResume() {
        super.onResume();



        final WebView ms = (WebView) findViewById(R.id.main_stuff);

        ms.getSettings().setJavaScriptEnabled(true);
        ms.getSettings().setLoadWithOverviewMode(true);
        ms.getSettings().setUseWideViewPort(true);

        ms.getSettings().setSupportZoom(true);
        ms.getSettings().setBuiltInZoomControls(true);
        ms.getSettings().setDisplayZoomControls(false);

        ms.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        ms.setScrollbarFadingEnabled(false);

        ms.loadUrl("https://www.mcxindia.com/market-data/option-chain");

        final Context c = this;

        ((Button) findViewById(R.id.refresh)).setEnabled(false);

        ms.setWebViewClient(new WebViewClient(){

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onPageFinished(final WebView view, String url) {
                Toast.makeText(getApplicationContext(),"Loaded", Toast.LENGTH_LONG).show();

                final String js = "javascript:(function(){"+
                        "document.getElementById(\"cph_InnerContainerRight_C008_hdnCommodity\").value = \"CRUDEOIL\";"+
                        "document.getElementById(\"ctl00_cph_InnerContainerRight_C008_ddlSymbols\").value = \"CRUDEOIL\";"+
                        "var Expiries = $(vTick).filter(function (idx, n) { return n.Symbol === $(\"#ctl00_cph_InnerContainerRight_C008_ddlSymbols\").val(); });$(\"#cph_InnerContainerRight_C008_hdnSymbols\").attr(\"value\", $(\"#ctl00_cph_InnerContainerRight_C008_ddlSymbols\").val());$(\"#ddlExpiry\").empty();$(\"#ddlExpiry\").siblings(\".cs-options\").children(\"ul\").empty();$(\"#ddlExpiry\").append($(\"<option value=\\\"Select\\\">Select Expiry</option>\"));$.each(Expiries, function (idx, Data) {$(\"#ddlExpiry\").append($(\"<option></option>\").attr(\"value\", Data.ExpiryDate).text(Data.ExpiryDate));});"+
                        "GetData(\"/backpage.aspx/GetOptionChain\", \"{'Commodity':'CRUDEOIL','Expiry':'\"+document.getElementById(\"ddlExpiry\").childNodes[1].value+\"'}\", OnSuccessOptionChain, OnFailedOptionChain);"+
                        "})()";

                view.evaluateJavascript(js, new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String s) {
                        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
                        boolean detsObtained = false;

                        ((MainActivity) c).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    new Thread().sleep(10000);
                                    String pjs = "javascript:(function(){"+
                                            "return document.getElementById(\"tblOptionChain\").getElementsByTagName(\"tr\")[document.getElementById(\"tblOptionChain\").getElementsByTagName(\"tr\").length-1].getElementsByTagName(\"td\")[1].innerText+\" \"+document.getElementById(\"tblOptionChain\").getElementsByTagName(\"tr\")[document.getElementById(\"tblOptionChain\").getElementsByTagName(\"tr\").length-1].getElementsByTagName(\"td\")[document.getElementById(\"tblOptionChain\").getElementsByTagName(\"tr\")[document.getElementById(\"tblOptionChain\").getElementsByTagName(\"tr\").length-1].getElementsByTagName(\"td\").length-1].innerText+\" \"+document.getElementById(\"ddlExpiry\").childNodes[1].value;"+
                                            "})()";
                                    view.evaluateJavascript(pjs, new ValueCallback<String>() {
                                        @Override
                                        public void onReceiveValue(String s) {
                                            try {
                                                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                                                String[] parts = s.split(" ");
                                                double calls = Double.valueOf(parts[0].replace("\"",""));
                                                double puts = Double.valueOf(parts[1].replace("\"",""));

                                                double live_pcr = Math.round((puts / calls) * 100.0) / 100.0;

                                                String exp_date = parts[2];

                                                double prev_pcr = 99999;

                                                if (getSP("prev_pcr") != "") {
                                                    prev_pcr = Double.valueOf(getSP("prev_pcr"));
                                                }

                                                double percent_change = Math.round(((live_pcr - prev_pcr) / prev_pcr * 100) * 100.0) / 100.0;

                                                String status = "";

                                                if (prev_pcr > live_pcr) {
                                                    status = "SHORT";
                                                } else if (prev_pcr < live_pcr) {
                                                    status = "LONG";
                                                } else {
                                                    status = "NEUTRAL";
                                                }

                                                int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);

                                                if (hour > 0 && hour < 9) {
                                                    setSP("prev_pcr", String.valueOf(live_pcr));
                                                }

                                                ((TextView) findViewById(R.id.prev_pcr)).setText(String.valueOf(prev_pcr));
                                                ((TextView) findViewById(R.id.live_pcr)).setText(String.valueOf(live_pcr));
                                                ((TextView) findViewById(R.id.p_change)).setText(String.valueOf(percent_change));
                                                ((TextView) findViewById(R.id.exp_date)).setText(exp_date.replace("\"",""));
                                                ((TextView) findViewById(R.id.status)).setText(status);


                                                ((Button) findViewById(R.id.refresh)).setEnabled(true);

                                            }
                                            catch (Exception e){
                                                Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                                            }

                                        }});
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        });




                    }
                });
            }
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                if (handler != null){
                    handler.proceed();
                } else {
                    super.onReceivedSslError(view, null, error);
                }
            }
        });
        ms.getSettings().setJavaScriptEnabled(true);
        ms.getSettings().setDomStorageEnabled(true);
    }
}