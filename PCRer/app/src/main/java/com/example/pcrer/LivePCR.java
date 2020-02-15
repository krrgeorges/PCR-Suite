package com.example.pcrer;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class LivePCR extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        String input = "";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    "PCRerForegroundServiceChannel",
                    "PCRerForeground Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
        Notification notification = new NotificationCompat.Builder(this, "PCRerForegroundServiceChannel")
                .setContentTitle("Monitoring NIFTY ratios..")
                .setContentText(input)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .build();

        startForeground(1011, notification);






        try {
            TimerTask tt = new TimerTask() {
                @Override
                public void run() {
                    try {

                        Document d = Jsoup.connect("https://www.nseindia.com/live_market/dynaContent/live_watch/option_chain/optionKeys.jsp").get();
                        Element table = d.getElementById("octable");
                        Elements trs = table.getElementsByTag("tr");
                        Element last_tr = trs.get(trs.size() - 1);
                        int calls = Integer.parseInt(last_tr.getElementsByTag("td").get(1).text().replace(",", "").trim());
                        int puts = Integer.parseInt(last_tr.getElementsByTag("td").get(7).text().replace(",", "").trim());

                        double pcr = Math.round((Double.valueOf(puts)/Double.valueOf(calls))*100.0)/100.0;

                        String notif_title="",notif_text="";

                        boolean doNotif = false;
                        if(getSharedPreferences("prev_pcr")!=""){
                            if(pcr < Double.valueOf(getSharedPreferences("prev_pcr"))){
                                double diff = Double.valueOf(getSharedPreferences("prev_pcr"))-pcr;
                                if(diff >= 0.05){
                                    notif_title = "Decrease from Previous PCR";
                                    notif_text = "Decrease by "+diff;
                                    doNotif = true;
                                }
                            }
                            else if(pcr > Double.valueOf(getSharedPreferences("prev_pcr"))){
                                double diff = pcr-Double.valueOf(getSharedPreferences("prev_pcr"));
                                if(diff >= 0.05){
                                    notif_title = "Increase from Previous PCR";
                                    notif_text = "Increase by "+diff;
                                    doNotif = true;
                                }
                            }
                        }
                        if(getSharedPreferences("live_pcr")!=""){
                            if(pcr == Double.valueOf(getSharedPreferences("live_pcr"))){
                                doNotif = false;
                            }
                        }
                        if(doNotif == true){
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                NotificationChannel serviceChannel = new NotificationChannel(
                                        "PCRERNotifChannel",
                                        "PCRERNotif Channel",
                                        NotificationManager.IMPORTANCE_HIGH
                                );

                                NotificationManager manager = getSystemService(NotificationManager.class);
                                manager.createNotificationChannel(serviceChannel);
                            }

                            Notification notification = new NotificationCompat.Builder(getApplicationContext(), "PCRERNotifChannel")
                                    .setContentTitle(notif_title)
                                    .setContentText(notif_text)
                                    .setStyle(new NotificationCompat.BigTextStyle().bigText(notif_text))
                                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                                    .setVibrate(new long[]{1000,1000,500,1000})
                                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                                    .build();

                            NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                            nm.notify(new Random().nextInt(1000),notification);
                        }

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

                        setSharedPreference("live_pcr", Double.toString(pcr));

                        setSharedPreference("live_calls", String.valueOf(calls));
                        setSharedPreference("live_puts", String.valueOf(puts));
                        if (hour > 15 && hour <= 23) {
                            setSharedPreference("prev_pcr", Double.toString(pcr));
                        }
                        setSharedPreference("exp_date", sv);






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










        return START_NOT_STICKY;
    }

    @Override
    public void onCreate() {

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
