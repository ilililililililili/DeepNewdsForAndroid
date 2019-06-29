package com.google.android.gms.location;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import com.google.android.gms.common.api.Api.ApiOptions.NoOptions;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.internal.zzg;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzbj;
import com.google.android.gms.tasks.Task;

public class ActivityRecognitionClient extends GoogleApi<NoOptions> {
    @Hide
    public ActivityRecognitionClient(Activity activity) {
        super(activity, LocationServices.API, null, new zzg());
    }

    @Hide
    public ActivityRecognitionClient(Context context) {
        super(context, LocationServices.API, null, new zzg());
    }

    public Task<Void> removeActivityTransitionUpdates(PendingIntent pendingIntent) {
        return zzbj.zzb(ActivityRecognition.ActivityRecognitionApi.zza(zzahw(), pendingIntent));
    }

    public Task<Void> removeActivityUpdates(PendingIntent pendingIntent) {
        return zzbj.zzb(ActivityRecognition.ActivityRecognitionApi.removeActivityUpdates(zzahw(), pendingIntent));
    }

    public Task<Void> requestActivityTransitionUpdates(ActivityTransitionRequest activityTransitionRequest, PendingIntent pendingIntent) {
        return zzbj.zzb(ActivityRecognition.ActivityRecognitionApi.zza(zzahw(), activityTransitionRequest, pendingIntent));
    }

    public Task<Void> requestActivityUpdates(long j, PendingIntent pendingIntent) {
        return zzbj.zzb(ActivityRecognition.ActivityRecognitionApi.requestActivityUpdates(zzahw(), j, pendingIntent));
    }
}
