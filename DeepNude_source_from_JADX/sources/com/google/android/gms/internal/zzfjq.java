package com.google.android.gms.internal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

final class zzfjq extends zzfgs {
    private static final int[] zzprr;
    private final int zzprs;
    private final zzfgs zzprt;
    private final zzfgs zzpru;
    private final int zzprv;
    private final int zzprw;

    static {
        List arrayList = new ArrayList();
        int i = 1;
        int i2 = 1;
        while (i > 0) {
            arrayList.add(Integer.valueOf(i));
            int i3 = i2 + i;
            i2 = i;
            i = i3;
        }
        arrayList.add(Integer.valueOf(ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED));
        zzprr = new int[arrayList.size()];
        i = 0;
        while (true) {
            int[] iArr = zzprr;
            if (i < iArr.length) {
                iArr[i] = ((Integer) arrayList.get(i)).intValue();
                i++;
            } else {
                return;
            }
        }
    }

    private zzfjq(zzfgs zzfgs, zzfgs zzfgs2) {
        this.zzprt = zzfgs;
        this.zzpru = zzfgs2;
        this.zzprv = zzfgs.size();
        this.zzprs = this.zzprv + zzfgs2.size();
        this.zzprw = Math.max(zzfgs.zzcxr(), zzfgs2.zzcxr()) + 1;
    }

    static zzfgs zza(zzfgs zzfgs, zzfgs zzfgs2) {
        if (zzfgs2.size() == 0) {
            return zzfgs;
        }
        if (zzfgs.size() == 0) {
            return zzfgs2;
        }
        int size = zzfgs.size() + zzfgs2.size();
        if (size < 128) {
            return zzb(zzfgs, zzfgs2);
        }
        if (zzfgs instanceof zzfjq) {
            zzfjq zzfjq = (zzfjq) zzfgs;
            if (zzfjq.zzpru.size() + zzfgs2.size() < 128) {
                return new zzfjq(zzfjq.zzprt, zzb(zzfjq.zzpru, zzfgs2));
            } else if (zzfjq.zzprt.zzcxr() > zzfjq.zzpru.zzcxr() && zzfjq.zzcxr() > zzfgs2.zzcxr()) {
                return new zzfjq(zzfjq.zzprt, new zzfjq(zzfjq.zzpru, zzfgs2));
            }
        }
        return size >= zzprr[Math.max(zzfgs.zzcxr(), zzfgs2.zzcxr()) + 1] ? new zzfjq(zzfgs, zzfgs2) : new zzfjs().zzc(zzfgs, zzfgs2);
    }

    private static zzfgs zzb(zzfgs zzfgs, zzfgs zzfgs2) {
        int size = zzfgs.size();
        int size2 = zzfgs2.size();
        byte[] bArr = new byte[(size + size2)];
        zzfgs.zza(bArr, 0, 0, size);
        zzfgs2.zza(bArr, 0, size, size2);
        return zzfgs.zzba(bArr);
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzfgs)) {
            return false;
        }
        zzfgs zzfgs = (zzfgs) obj;
        if (this.zzprs != zzfgs.size()) {
            return false;
        }
        if (this.zzprs == 0) {
            return true;
        }
        int zzcxt = zzcxt();
        int zzcxt2 = zzfgs.zzcxt();
        if (zzcxt != 0 && zzcxt2 != 0 && zzcxt != zzcxt2) {
            return false;
        }
        Iterator zzfjt = new zzfjt(this);
        zzfgs zzfgs2 = (zzfgy) zzfjt.next();
        Iterator zzfjt2 = new zzfjt(zzfgs);
        zzfgs zzfgs3 = (zzfgy) zzfjt2.next();
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        while (true) {
            int size = zzfgs2.size() - i;
            int size2 = zzfgs3.size() - i2;
            int min = Math.min(size, size2);
            if (!(i == 0 ? zzfgs2.zza(zzfgs3, i2, min) : zzfgs3.zza(zzfgs2, i, min))) {
                return false;
            }
            i3 += min;
            int i4 = this.zzprs;
            if (i3 >= i4) {
                break;
            }
            if (min == size) {
                zzfgs2 = (zzfgy) zzfjt.next();
                i = 0;
            } else {
                i += min;
            }
            if (min == size2) {
                zzfgs3 = (zzfgy) zzfjt2.next();
                i2 = 0;
            } else {
                i2 += min;
            }
        }
        if (i3 == i4) {
            return true;
        }
        throw new IllegalStateException();
    }

    public final int size() {
        return this.zzprs;
    }

    final void zza(zzfgr zzfgr) {
        this.zzprt.zza(zzfgr);
        this.zzpru.zza(zzfgr);
    }

    public final zzfgs zzaa(int i, int i2) {
        int zzh = zzfgs.zzh(i, i2, this.zzprs);
        if (zzh == 0) {
            return zzfgs.zzpnw;
        }
        if (zzh == this.zzprs) {
            return this;
        }
        zzh = this.zzprv;
        if (i2 <= zzh) {
            return this.zzprt.zzaa(i, i2);
        }
        if (i >= zzh) {
            return this.zzpru.zzaa(i - zzh, i2 - zzh);
        }
        zzfgs zzfgs = this.zzprt;
        return new zzfjq(zzfgs.zzaa(i, zzfgs.size()), this.zzpru.zzaa(0, i2 - this.zzprv));
    }

    protected final void zzb(byte[] bArr, int i, int i2, int i3) {
        int i4 = i + i3;
        int i5 = this.zzprv;
        if (i4 <= i5) {
            this.zzprt.zzb(bArr, i, i2, i3);
        } else if (i >= i5) {
            this.zzpru.zzb(bArr, i - i5, i2, i3);
        } else {
            i5 -= i;
            this.zzprt.zzb(bArr, i, i2, i5);
            this.zzpru.zzb(bArr, 0, i2 + i5, i3 - i5);
        }
    }

    public final zzfhb zzcxq() {
        return zzfhb.zzh(new zzfju(this));
    }

    protected final int zzcxr() {
        return this.zzprw;
    }

    protected final boolean zzcxs() {
        return this.zzprs >= zzprr[this.zzprw];
    }

    protected final int zzg(int i, int i2, int i3) {
        int i4 = i2 + i3;
        int i5 = this.zzprv;
        if (i4 <= i5) {
            return this.zzprt.zzg(i, i2, i3);
        }
        if (i2 >= i5) {
            return this.zzpru.zzg(i, i2 - i5, i3);
        }
        i5 -= i2;
        return this.zzpru.zzg(this.zzprt.zzg(i, i2, i5), 0, i3 - i5);
    }

    public final byte zzld(int i) {
        zzfgs.zzab(i, this.zzprs);
        int i2 = this.zzprv;
        return i < i2 ? this.zzprt.zzld(i) : this.zzpru.zzld(i - i2);
    }
}
