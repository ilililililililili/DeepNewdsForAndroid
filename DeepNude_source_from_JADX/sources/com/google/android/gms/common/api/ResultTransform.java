package com.google.android.gms.common.api;

import com.google.android.gms.common.api.internal.zzct;

public abstract class ResultTransform<R extends Result, S extends Result> {
    public final PendingResult<S> createFailedResult(Status status) {
        return new zzct(status);
    }

    public Status onFailure(Status status) {
        return status;
    }

    public abstract PendingResult<S> onSuccess(R r);
}
