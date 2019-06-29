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
import java.util.List;

public class GeofencingClient extends GoogleApi<NoOptions> {
    @Hide
    public GeofencingClient(Activity activity) {
        super(activity, LocationServices.API, null, new zzg());
    }

    @Hide
    public GeofencingClient(Context context) {
        super(context, LocationServices.API, null, new zzg());
    }

    public Task<Void> addGeofences(GeofencingRequest geofencingRequest, PendingIntent pendingIntent) {
        return zzbj.zzb(LocationServices.GeofencingApi.addGeofences(zzahw(), geofencingRequest, pendingIntent));
    }

    public Task<Void> removeGeofences(PendingIntent pendingIntent) {
        return zzbj.zzb(LocationServices.GeofencingApi.removeGeofences(zzahw(), pendingIntent));
    }

    public Task<Void> removeGeofences(List<String> list) {
        return zzbj.zzb(LocationServices.GeofencingApi.removeGeofences(zzahw(), (List) list));
    }
}
