package com.google.android.gms.iid;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;
import com.google.android.gms.common.internal.Hide;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Hide
public abstract class zzb extends Service {
    private final Object mLock = new Object();
    final ExecutorService zzimc = Executors.newSingleThreadExecutor();
    private Binder zzimd;
    private int zzime;
    private int zzimf = 0;

    private final void zzh(Intent intent) {
        if (intent != null) {
            WakefulBroadcastReceiver.completeWakefulIntent(intent);
        }
        synchronized (this.mLock) {
            this.zzimf--;
            if (this.zzimf == 0) {
                stopSelfResult(this.zzime);
            }
        }
    }

    @Hide
    public abstract void handleIntent(Intent intent);

    @Hide
    public final synchronized IBinder onBind(Intent intent) {
        if (Log.isLoggable("EnhancedIntentService", 3)) {
            Log.d("EnhancedIntentService", "Service received bind request");
        }
        if (this.zzimd == null) {
            this.zzimd = new zzf(this);
        }
        return this.zzimd;
    }

    @Hide
    public final int onStartCommand(Intent intent, int i, int i2) {
        synchronized (this.mLock) {
            this.zzime = i2;
            this.zzimf++;
        }
        if (intent == null) {
            zzh(intent);
            return 2;
        }
        this.zzimc.execute(new zzc(this, intent, intent));
        return 3;
    }
}
