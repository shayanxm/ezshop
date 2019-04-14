package com.example.shayanmoradi.ezshop.services;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import com.example.shayanmoradi.ezshop.Model.Product;
import com.example.shayanmoradi.ezshop.R;
import com.example.shayanmoradi.ezshop.itemDetail.ItemDetailActivity;
import com.example.shayanmoradi.ezshop.network.Api;
import com.example.shayanmoradi.ezshop.network.RetrofitClientInstance;
import com.example.shayanmoradi.ezshop.prefs.QueryPreferences;

import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class PollJobService extends JobService {
    private PollTask mCurrenctTask;
    public static final String TAG = "PollJobService";
    public static final int JOB_id = 1;
    Product laetesProduct;

    public static void scheduleService(Context context, int time) {
        JobScheduler jobScheduler = (JobScheduler) context.getSystemService(JOB_SCHEDULER_SERVICE);
        JobInfo jobInfo = new JobInfo.Builder(1, new ComponentName(context, PollJobService.class))
                .setPeriodic(TimeUnit.MINUTES.toMillis(1))
                .setPersisted(true)
                .build();
        if (!isScheduled(context))

            jobScheduler.schedule(jobInfo);
        jobScheduler.schedule(jobInfo);

    }

    public static boolean isScheduled(Context context) {
        JobScheduler jobScheduler = (JobScheduler) context.getSystemService(JOB_SCHEDULER_SERVICE);
        for (JobInfo jobInfo : jobScheduler.getAllPendingJobs()) {
            if (jobInfo.getId() == JOB_id) {
                return true;
            }
        }
        return false;
    }

    public PollJobService() {

    }


    @Override
    public boolean onStartJob(JobParameters params) {
        mCurrenctTask = new PollTask();
        Log.e("test", "on start job");
        Log.e("test", "on start job2");
        mCurrenctTask.execute(params);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            scheduleService(PollJobService.this, 1);
        }
//        pollServerAndSendNotigication(PollJobService.this, TAG);
        Log.e("test", "after job");

        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        if (mCurrenctTask != null)
            mCurrenctTask.cancel(true);

        return false;
    }

    private class PollTask extends AsyncTask<JobParameters, Void, Void> {

        @Override
        protected Void doInBackground(JobParameters... jobParameters) {
            //write code
          //  pollServerAndSendNotigication(PollJobService.this, TAG);
            jobFinished(jobParameters[0], false);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                scheduleService(PollJobService.this, 1);
            }
            return null;
        }
    }

    public void pollServerAndSendNotigication(Context context, String tag) {

//if last id aln != lat id perfence then notification

        RetrofitClientInstance.getRetrofitInstance().create(Api.class)
                .getNewest().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                List<Product> productList = response.body();
                laetesProduct = productList.get(0);


            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }
        });
      //  int savedLastId = QueryPreferences.getStoredQurey(this);
        int lastIdLive = laetesProduct.getmId();
//        if (savedLastId == lastIdLive) {
//            //no new res
//        } else {
        //send notification
        Log.e("test", "in background thread");
       // Intent i = new Intent(this, MainActivity.class);

        Intent i = ItemDetailActivity.newIntent(this,QueryPreferences.getStoredQurey(this) );

        PendingIntent pi = PendingIntent.getActivity(this, 0, i, 0);
        String channelId = getString(R.string.channel_id);
        Notification notification = new NotificationCompat.Builder(this, channelId)
                .setContentTitle("ezshop")
                //   .setSmallIcon()
                .setContentIntent(pi)
                .setAutoCancel(true)
                .build();

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(0, notification);
      // }
        QueryPreferences.setStoredQuery(this, lastIdLive);

    }


}
