package com.google.android.gms.common.internal;

import android.content.Intent;
import android.support.v4.app.Fragment;

final class zzx extends zzv {
    private /* synthetic */ Fragment val$fragment;
    private /* synthetic */ Intent val$intent;
    private /* synthetic */ int val$requestCode;

    zzx(Intent intent, Fragment fragment, int i) {
        this.val$intent = intent;
        this.val$fragment = fragment;
        this.val$requestCode = i;
    }

    public final void zzamo() {
        Intent intent = this.val$intent;
        if (intent != null) {
            this.val$fragment.startActivityForResult(intent, this.val$requestCode);
        }
    }
}
