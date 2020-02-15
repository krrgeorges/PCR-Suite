package com.example.bankpcr;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class LivePCR extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        try {
            TimerTask tt = new TimerTask() {
                @Override
                public void run() {
                    try {
                        Document d = Jsoup.connect("https://www.nseindia.com/live_market/dynaContent/live_watch/option_chain/optionKeys.jsp?symbolCode=-9999&symbol=BANKNIFTY&symbol=BANKNIFTY").get();
                        Element table = d.getElementById("octable");
                        Elements trs = table.getElementsByTag("tr");
                        Element last_tr = trs.get(trs.size() - 1);
                        int calls = Integer.parseInt(last_tr.getElementsByTag("td").get(1).text().replace(",", "").trim());
                        int puts = Integer.parseInt(last_tr.getElementsByTag("td").get(7).text().replace(",", "").trim());

                        double pcr = Math.round((Double.valueOf(puts)/Double.valueOf(calls))*100.0)/100.0;

                        Elements options = d.getElementById("date").children();
                        String sv = "";
                        for (Element option : options) {
                            if (option.hasAttr("selected")) {
                                sv = option.val();
                                break;
                            }
                        }

                        Calendar c = Calendar.getInstance();
                        int hour = c.get(Calendar.HOUR_OF_DAY);
                        setSharedPreference("blive_pcr", Double.toString(pcr));
                        setSharedPreference("live_calls", String.valueOf(calls));
                        setSharedPreference("live_puts", String.valueOf(puts));
                        if (hour > 15 && hour <= 23) {
                            setSharedPreference("bprev_pcr", Double.toString(pcr));
                        }
                        setSharedPreference("bexp_date", sv);
                    } catch (Exception e) {
                        setSharedPreference("exc",e.getMessage());
                    }
                }
            };
            Timer t = new Timer();
            t.scheduleAtFixedRate(tt, 1000, 60000 * 10);
        }
        catch (Exception e){
        }
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
