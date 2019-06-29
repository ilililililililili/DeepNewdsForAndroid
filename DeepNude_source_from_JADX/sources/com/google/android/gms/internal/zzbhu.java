package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.v4.internal.view.SupportMenu;
import com.google.android.gms.common.internal.Hide;

@Hide
public final class zzbhu implements Creator<zzbhx> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzbgm.zzd(parcel);
        String str = null;
        int i = 0;
        zzbhq zzbhq = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            int i2 = SupportMenu.USER_MASK & readInt;
            if (i2 == 1) {
                i = zzbgm.zzg(parcel, readInt);
            } else if (i2 == 2) {
                str = zzbgm.zzq(parcel, readInt);
            } else if (i2 != 3) {
                zzbgm.zzb(parcel, readInt);
            } else {
                zzbhq = (zzbhq) zzbgm.zza(parcel, readInt, zzbhq.CREATOR);
            }
        }
        zzbgm.zzaf(parcel, zzd);
        return new zzbhx(i, str, zzbhq);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzbhx[i];
    }
}
