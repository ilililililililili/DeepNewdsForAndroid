package com.google.android.gms.common.util;

import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.zzbq;
import java.util.Set;

public final class zzv {
    public static String[] zzc(Set<Scope> set) {
        String str = "scopes can't be null.";
        zzbq.checkNotNull(set, str);
        Scope[] scopeArr = (Scope[]) set.toArray(new Scope[set.size()]);
        zzbq.checkNotNull(scopeArr, str);
        String[] strArr = new String[scopeArr.length];
        for (int i = 0; i < scopeArr.length; i++) {
            strArr[i] = scopeArr[i].zzaie();
        }
        return strArr;
    }
}
