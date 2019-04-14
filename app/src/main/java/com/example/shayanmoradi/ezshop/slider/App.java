package com.example.shayanmoradi.ezshop.slider;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import com.example.shayanmoradi.ezshop.R;
import com.example.shayanmoradi.ezshop.database.DaoMaster;
import com.example.shayanmoradi.ezshop.database.DaoSession;

import org.greenrobot.greendao.database.Database;

import ss.com.bannerslider.Slider;

public class App extends Application {
    private static App app;

    private DaoSession daoSession;
    @Override
    public void onCreate() {
        super.onCreate();
        PicassoImageLoadingService imageLoadingService= new PicassoImageLoadingService(getApplicationContext());
        Slider.init(imageLoadingService);


        MyDevOpenHelper myDevOpenHelper = new MyDevOpenHelper(this, "DatabaseName");
        Database db = myDevOpenHelper.getWritableDb();

        daoSession = new DaoMaster(db).newSession();

        app = this;
        ///////
        createNotificationChannel();
        /////////
    }

    public static App getApp() {
        return app;
    }
    public DaoSession getDaoSession() {
        return daoSession;
    }
    /////
    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = getString(R.string.channel_id);
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(channelId, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

}
