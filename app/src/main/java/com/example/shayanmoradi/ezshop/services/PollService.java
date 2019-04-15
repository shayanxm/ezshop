package com.example.shayanmoradi.ezshop.services;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.SystemClock;
import android.util.Log;

import com.example.shayanmoradi.ezshop.Model.Product;
import com.example.shayanmoradi.ezshop.R;
import com.example.shayanmoradi.ezshop.itemDetail.ItemDetailActivity;
import com.example.shayanmoradi.ezshop.network.Api;
import com.example.shayanmoradi.ezshop.network.RetrofitClientInstance;
import com.example.shayanmoradi.ezshop.prefs.QueryPreferences;

import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PollService extends IntentService {
    Product laetesProduct;
    int lastIdLive;
    private static final String TAG = "PollService";
    private static final long POLL_INTERVAL = TimeUnit.MINUTES.toMillis(1);

    public static Intent newIntent(Context context) {
        return new Intent(context, PollService.class);
    }

    public static void setServiceAlarm(Context context, boolean isOn, int time) {
        Intent i = newIntent(context);
        PendingIntent pi = PendingIntent.getService(context, 0, i, 0);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        if (isOn) {
            alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME,
                    SystemClock.elapsedRealtime(), POLL_INTERVAL * time, pi);
        } else {
            alarmManager.cancel(pi);
            pi.cancel();
        }
        //   QueryPreferences.setAlarmOn(context, isOn);
    }

    public static boolean isAlarmOn(Context context) {
        Intent i = newIntent(context);
        PendingIntent pi = PendingIntent.getService(context,
                0,
                i,
                PendingIntent.FLAG_NO_CREATE);

        return pi != null;
    }

    public PollService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (!isNetworkAvailableAndConnected())
            return;

        Log.d("test", "received intent: " + intent);
        //Services.pollServerAndSendNotification(this, TAG);
        pollServerAndSendNotigication(this, TAG);
    }

    private boolean isNetworkAvailableAndConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        boolean isNetworkAvailable = cm.getActiveNetworkInfo() != null;
        boolean isNetworkConnected = isNetworkAvailable && cm.getActiveNetworkInfo().isConnected();

        return isNetworkConnected;
    }

    public void pollServerAndSendNotigication(Context context, String tag) {

//if last id aln != lat id perfence then notification
        // int lastIdLive=0;
        RetrofitClientInstance.getRetrofitInstance().create(Api.class)
                .getRot().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) {
                    Log.e("test", "is succesful ");
                    // Product product = response.body();
                    List<Product> productList = response.body();
                    //   Log.e("test", Arrays.toString(productList.toArray())+"array XD");

                    laetesProduct = productList.get(0);
                    lastIdLive = laetesProduct.getmId();

                    Log.e("test", "id is" + lastIdLive);
//////////////
                    int savedLastId = QueryPreferences.getStoredQurey(PollService.this);
                    if (savedLastId == lastIdLive) {
                        //no new res
                    } else {
//        send notification

                        Log.e("test", "id is" + savedLastId);
                        Intent i = ItemDetailActivity.newIntent(PollService.this, 81);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        PendingIntent pi = PendingIntent.getActivity(PollService.this, 0, i, 0);



                        String channelId = getString(R.string.channel_id);
                        Notification notification = new NotificationCompat.Builder(PollService.this, channelId)
                                .setContentTitle("محصولات جدیدی اضافه شده ! به ما سر بزنید")

                                .setSmallIcon(R.drawable.ezshoplogo)

                                .setContentIntent(pi)
                                .setAutoCancel(true)

                                .build();

                        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(PollService.this);
                        notificationManagerCompat.notify(0, notification);


                        /////////////
                        QueryPreferences.setStoredQuery(PollService.this, lastIdLive);

                    }
                    Log.d("test", "is not succ");
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.d("test", "fail" + t.getMessage());
            }
        });



    }

    }



