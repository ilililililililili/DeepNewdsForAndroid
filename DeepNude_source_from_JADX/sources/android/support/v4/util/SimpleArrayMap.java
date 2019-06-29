package android.support.v4.util;

import java.util.ConcurrentModificationException;

public class SimpleArrayMap<K, V> {
    private static final int BASE_SIZE = 4;
    private static final int CACHE_SIZE = 10;
    private static final boolean CONCURRENT_MODIFICATION_EXCEPTIONS = true;
    private static final boolean DEBUG = false;
    private static final String TAG = "ArrayMap";
    static Object[] mBaseCache;
    static int mBaseCacheSize;
    static Object[] mTwiceBaseCache;
    static int mTwiceBaseCacheSize;
    Object[] mArray;
    int[] mHashes;
    int mSize;

    public SimpleArrayMap() {
        this.mHashes = ContainerHelpers.EMPTY_INTS;
        this.mArray = ContainerHelpers.EMPTY_OBJECTS;
        this.mSize = 0;
    }

    public SimpleArrayMap(int i) {
        if (i == 0) {
            this.mHashes = ContainerHelpers.EMPTY_INTS;
            this.mArray = ContainerHelpers.EMPTY_OBJECTS;
        } else {
            allocArrays(i);
        }
        this.mSize = 0;
    }

    public SimpleArrayMap(SimpleArrayMap<K, V> simpleArrayMap) {
        this();
        if (simpleArrayMap != null) {
            putAll(simpleArrayMap);
        }
    }

    private void allocArrays(int i) {
        Object[] objArr;
        if (i == 8) {
            synchronized (ArrayMap.class) {
                if (mTwiceBaseCache != null) {
                    objArr = mTwiceBaseCache;
                    this.mArray = objArr;
                    mTwiceBaseCache = (Object[]) objArr[0];
                    this.mHashes = (int[]) objArr[1];
                    objArr[1] = null;
                    objArr[0] = null;
                    mTwiceBaseCacheSize--;
                    return;
                }
            }
        } else if (i == 4) {
            synchronized (ArrayMap.class) {
                if (mBaseCache != null) {
                    objArr = mBaseCache;
                    this.mArray = objArr;
                    mBaseCache = (Object[]) objArr[0];
                    this.mHashes = (int[]) objArr[1];
                    objArr[1] = null;
                    objArr[0] = null;
                    mBaseCacheSize--;
                    return;
                }
            }
        }
        this.mHashes = new int[i];
        this.mArray = new Object[(i << 1)];
    }

    private static int binarySearchHashes(int[] r0, int r1, int r2) {
        /* JADX: method processing error */
/*
Error: java.lang.NullPointerException
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.searchTryCatchDominators(ProcessTryCatchRegions.java:75)
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.process(ProcessTryCatchRegions.java:45)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.postProcessRegions(RegionMakerVisitor.java:63)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:58)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:34)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:56)
	at jadx.core.ProcessClass.process(ProcessClass.java:39)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:282)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:200)
	at jadx.api.JadxDecompiler$$Lambda$8/318857719.run(Unknown Source)
*/
        /*
        r0 = android.support.v4.util.ContainerHelpers.binarySearch(r0, r1, r2);	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0005 }
        return r0;
    L_0x0005:
        r0 = new java.util.ConcurrentModificationException;
        r0.<init>();
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.util.SimpleArrayMap.binarySearchHashes(int[], int, int):int");
    }

    private static void freeArrays(int[] iArr, Object[] objArr, int i) {
        int i2;
        if (iArr.length == 8) {
            synchronized (ArrayMap.class) {
                if (mTwiceBaseCacheSize < 10) {
                    objArr[0] = mTwiceBaseCache;
                    objArr[1] = iArr;
                    for (i2 = (i << 1) - 1; i2 >= 2; i2--) {
                        objArr[i2] = null;
                    }
                    mTwiceBaseCache = objArr;
                    mTwiceBaseCacheSize++;
                }
            }
        } else if (iArr.length == 4) {
            synchronized (ArrayMap.class) {
                if (mBaseCacheSize < 10) {
                    objArr[0] = mBaseCache;
                    objArr[1] = iArr;
                    for (i2 = (i << 1) - 1; i2 >= 2; i2--) {
                        objArr[i2] = null;
                    }
                    mBaseCache = objArr;
                    mBaseCacheSize++;
                }
            }
        }
    }

    public void clear() {
        int i = this.mSize;
        if (i > 0) {
            int[] iArr = this.mHashes;
            Object[] objArr = this.mArray;
            this.mHashes = ContainerHelpers.EMPTY_INTS;
            this.mArray = ContainerHelpers.EMPTY_OBJECTS;
            this.mSize = 0;
            freeArrays(iArr, objArr, i);
        }
        if (this.mSize > 0) {
            throw new ConcurrentModificationException();
        }
    }

    public boolean containsKey(Object obj) {
        return indexOfKey(obj) >= 0 ? CONCURRENT_MODIFICATION_EXCEPTIONS : false;
    }

    public boolean containsValue(Object obj) {
        return indexOfValue(obj) >= 0 ? CONCURRENT_MODIFICATION_EXCEPTIONS : false;
    }

    public void ensureCapacity(int i) {
        int i2 = this.mSize;
        Object obj = this.mHashes;
        if (obj.length < i) {
            Object obj2 = this.mArray;
            allocArrays(i);
            if (this.mSize > 0) {
                System.arraycopy(obj, 0, this.mHashes, 0, i2);
                System.arraycopy(obj2, 0, this.mArray, 0, i2 << 1);
            }
            freeArrays(obj, obj2, i2);
        }
        if (this.mSize != i2) {
            throw new ConcurrentModificationException();
        }
    }

    public boolean equals(java.lang.Object r7) {
        /* JADX: method processing error */
/*
Error: java.lang.NullPointerException
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.searchTryCatchDominators(ProcessTryCatchRegions.java:75)
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.process(ProcessTryCatchRegions.java:45)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.postProcessRegions(RegionMakerVisitor.java:63)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:58)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:34)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:56)
	at jadx.core.ProcessClass.process(ProcessClass.java:39)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:282)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:200)
	at jadx.api.JadxDecompiler$$Lambda$8/318857719.run(Unknown Source)
*/
        /*
        r6 = this;
        r0 = 1;
        if (r6 != r7) goto L_0x0004;
    L_0x0003:
        return r0;
    L_0x0004:
        r1 = r7 instanceof android.support.v4.util.SimpleArrayMap;
        r2 = 0;
        if (r1 == 0) goto L_0x003e;
    L_0x0009:
        r7 = (android.support.v4.util.SimpleArrayMap) r7;
        r1 = r6.size();
        r3 = r7.size();
        if (r1 == r3) goto L_0x0016;
    L_0x0015:
        return r2;
    L_0x0016:
        r1 = 0;
    L_0x0017:
        r3 = r6.mSize;	 Catch:{ NullPointerException -> 0x003d, NullPointerException -> 0x003d }
        if (r1 >= r3) goto L_0x003c;	 Catch:{ NullPointerException -> 0x003d, NullPointerException -> 0x003d }
    L_0x001b:
        r3 = r6.keyAt(r1);	 Catch:{ NullPointerException -> 0x003d, NullPointerException -> 0x003d }
        r4 = r6.valueAt(r1);	 Catch:{ NullPointerException -> 0x003d, NullPointerException -> 0x003d }
        r5 = r7.get(r3);	 Catch:{ NullPointerException -> 0x003d, NullPointerException -> 0x003d }
        if (r4 != 0) goto L_0x0032;	 Catch:{ NullPointerException -> 0x003d, NullPointerException -> 0x003d }
    L_0x0029:
        if (r5 != 0) goto L_0x0031;	 Catch:{ NullPointerException -> 0x003d, NullPointerException -> 0x003d }
    L_0x002b:
        r3 = r7.containsKey(r3);	 Catch:{ NullPointerException -> 0x003d, NullPointerException -> 0x003d }
        if (r3 != 0) goto L_0x0039;	 Catch:{ NullPointerException -> 0x003d, NullPointerException -> 0x003d }
    L_0x0031:
        return r2;	 Catch:{ NullPointerException -> 0x003d, NullPointerException -> 0x003d }
    L_0x0032:
        r3 = r4.equals(r5);	 Catch:{ NullPointerException -> 0x003d, NullPointerException -> 0x003d }
        if (r3 != 0) goto L_0x0039;
    L_0x0038:
        return r2;
    L_0x0039:
        r1 = r1 + 1;
        goto L_0x0017;
    L_0x003c:
        return r0;
    L_0x003d:
        return r2;
    L_0x003e:
        r1 = r7 instanceof java.util.Map;
        if (r1 == 0) goto L_0x0076;
    L_0x0042:
        r7 = (java.util.Map) r7;
        r1 = r6.size();
        r3 = r7.size();
        if (r1 == r3) goto L_0x004f;
    L_0x004e:
        return r2;
    L_0x004f:
        r1 = 0;
    L_0x0050:
        r3 = r6.mSize;	 Catch:{ NullPointerException -> 0x0076, NullPointerException -> 0x0076 }
        if (r1 >= r3) goto L_0x0075;	 Catch:{ NullPointerException -> 0x0076, NullPointerException -> 0x0076 }
    L_0x0054:
        r3 = r6.keyAt(r1);	 Catch:{ NullPointerException -> 0x0076, NullPointerException -> 0x0076 }
        r4 = r6.valueAt(r1);	 Catch:{ NullPointerException -> 0x0076, NullPointerException -> 0x0076 }
        r5 = r7.get(r3);	 Catch:{ NullPointerException -> 0x0076, NullPointerException -> 0x0076 }
        if (r4 != 0) goto L_0x006b;	 Catch:{ NullPointerException -> 0x0076, NullPointerException -> 0x0076 }
    L_0x0062:
        if (r5 != 0) goto L_0x006a;	 Catch:{ NullPointerException -> 0x0076, NullPointerException -> 0x0076 }
    L_0x0064:
        r3 = r7.containsKey(r3);	 Catch:{ NullPointerException -> 0x0076, NullPointerException -> 0x0076 }
        if (r3 != 0) goto L_0x0072;	 Catch:{ NullPointerException -> 0x0076, NullPointerException -> 0x0076 }
    L_0x006a:
        return r2;	 Catch:{ NullPointerException -> 0x0076, NullPointerException -> 0x0076 }
    L_0x006b:
        r3 = r4.equals(r5);	 Catch:{ NullPointerException -> 0x0076, NullPointerException -> 0x0076 }
        if (r3 != 0) goto L_0x0072;
    L_0x0071:
        return r2;
    L_0x0072:
        r1 = r1 + 1;
        goto L_0x0050;
    L_0x0075:
        return r0;
    L_0x0076:
        return r2;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.util.SimpleArrayMap.equals(java.lang.Object):boolean");
    }

    public V get(Object obj) {
        int indexOfKey = indexOfKey(obj);
        return indexOfKey >= 0 ? this.mArray[(indexOfKey << 1) + 1] : null;
    }

    public int hashCode() {
        int[] iArr = this.mHashes;
        Object[] objArr = this.mArray;
        int i = this.mSize;
        int i2 = 0;
        int i3 = 0;
        int i4 = 1;
        while (i2 < i) {
            Object obj = objArr[i4];
            i3 += (obj == null ? 0 : obj.hashCode()) ^ iArr[i2];
            i2++;
            i4 += 2;
        }
        return i3;
    }

    int indexOf(Object obj, int i) {
        int i2 = this.mSize;
        if (i2 == 0) {
            return -1;
        }
        int binarySearchHashes = binarySearchHashes(this.mHashes, i2, i);
        if (binarySearchHashes < 0 || obj.equals(this.mArray[binarySearchHashes << 1])) {
            return binarySearchHashes;
        }
        int i3 = binarySearchHashes + 1;
        while (i3 < i2 && this.mHashes[i3] == i) {
            if (obj.equals(this.mArray[i3 << 1])) {
                return i3;
            }
            i3++;
        }
        binarySearchHashes--;
        while (binarySearchHashes >= 0 && this.mHashes[binarySearchHashes] == i) {
            if (obj.equals(this.mArray[binarySearchHashes << 1])) {
                return binarySearchHashes;
            }
            binarySearchHashes--;
        }
        return i3 ^ -1;
    }

    public int indexOfKey(Object obj) {
        return obj == null ? indexOfNull() : indexOf(obj, obj.hashCode());
    }

    int indexOfNull() {
        int i = this.mSize;
        if (i == 0) {
            return -1;
        }
        int binarySearchHashes = binarySearchHashes(this.mHashes, i, 0);
        if (binarySearchHashes < 0 || this.mArray[binarySearchHashes << 1] == null) {
            return binarySearchHashes;
        }
        int i2 = binarySearchHashes + 1;
        while (i2 < i && this.mHashes[i2] == 0) {
            if (this.mArray[i2 << 1] == null) {
                return i2;
            }
            i2++;
        }
        binarySearchHashes--;
        while (binarySearchHashes >= 0 && this.mHashes[binarySearchHashes] == 0) {
            if (this.mArray[binarySearchHashes << 1] == null) {
                return binarySearchHashes;
            }
            binarySearchHashes--;
        }
        return i2 ^ -1;
    }

    int indexOfValue(Object obj) {
        int i = this.mSize * 2;
        Object[] objArr = this.mArray;
        if (obj == null) {
            for (int i2 = 1; i2 < i; i2 += 2) {
                if (objArr[i2] == null) {
                    return i2 >> 1;
                }
            }
        } else {
            for (int i3 = 1; i3 < i; i3 += 2) {
                if (obj.equals(objArr[i3])) {
                    return i3 >> 1;
                }
            }
        }
        return -1;
    }

    public boolean isEmpty() {
        return this.mSize <= 0 ? CONCURRENT_MODIFICATION_EXCEPTIONS : false;
    }

    public K keyAt(int i) {
        return this.mArray[i << 1];
    }

    public V put(K k, V v) {
        int indexOfNull;
        int i;
        int i2 = this.mSize;
        if (k == null) {
            indexOfNull = indexOfNull();
            i = 0;
        } else {
            indexOfNull = k.hashCode();
            i = indexOfNull;
            indexOfNull = indexOf(k, indexOfNull);
        }
        if (indexOfNull >= 0) {
            int i3 = (indexOfNull << 1) + 1;
            Object[] objArr = this.mArray;
            V v2 = objArr[i3];
            objArr[i3] = v;
            return v2;
        }
        int i4;
        indexOfNull ^= -1;
        if (i2 >= this.mHashes.length) {
            i4 = 4;
            if (i2 >= 8) {
                i4 = (i2 >> 1) + i2;
            } else if (i2 >= 4) {
                i4 = 8;
            }
            Object obj = this.mHashes;
            Object obj2 = this.mArray;
            allocArrays(i4);
            if (i2 == this.mSize) {
                Object obj3 = this.mHashes;
                if (obj3.length > 0) {
                    System.arraycopy(obj, 0, obj3, 0, obj.length);
                    System.arraycopy(obj2, 0, this.mArray, 0, obj2.length);
                }
                freeArrays(obj, obj2, i2);
            } else {
                throw new ConcurrentModificationException();
            }
        }
        if (indexOfNull < i2) {
            Object obj4 = this.mHashes;
            i4 = indexOfNull + 1;
            System.arraycopy(obj4, indexOfNull, obj4, i4, i2 - indexOfNull);
            obj4 = this.mArray;
            System.arraycopy(obj4, indexOfNull << 1, obj4, i4 << 1, (this.mSize - indexOfNull) << 1);
        }
        int i5 = this.mSize;
        if (i2 == i5) {
            int[] iArr = this.mHashes;
            if (indexOfNull < iArr.length) {
                iArr[indexOfNull] = i;
                objArr = this.mArray;
                indexOfNull <<= 1;
                objArr[indexOfNull] = k;
                objArr[indexOfNull + 1] = v;
                this.mSize = i5 + 1;
                return null;
            }
        }
        throw new ConcurrentModificationException();
    }

    public void putAll(SimpleArrayMap<? extends K, ? extends V> simpleArrayMap) {
        int i = simpleArrayMap.mSize;
        ensureCapacity(this.mSize + i);
        int i2 = 0;
        if (this.mSize != 0) {
            while (i2 < i) {
                put(simpleArrayMap.keyAt(i2), simpleArrayMap.valueAt(i2));
                i2++;
            }
        } else if (i > 0) {
            System.arraycopy(simpleArrayMap.mHashes, 0, this.mHashes, 0, i);
            System.arraycopy(simpleArrayMap.mArray, 0, this.mArray, 0, i << 1);
            this.mSize = i;
        }
    }

    public V remove(Object obj) {
        int indexOfKey = indexOfKey(obj);
        return indexOfKey >= 0 ? removeAt(indexOfKey) : null;
    }

    public V removeAt(int i) {
        int i2;
        Object[] objArr = this.mArray;
        int i3 = i << 1;
        V v = objArr[i3 + 1];
        int i4 = this.mSize;
        if (i4 <= 1) {
            freeArrays(this.mHashes, objArr, i4);
            this.mHashes = ContainerHelpers.EMPTY_INTS;
            this.mArray = ContainerHelpers.EMPTY_OBJECTS;
            i2 = 0;
        } else {
            i2 = i4 - 1;
            int[] iArr = this.mHashes;
            int i5 = 8;
            if (iArr.length <= 8 || i4 >= iArr.length / 3) {
                if (i < i2) {
                    Object obj = this.mHashes;
                    int i6 = i + 1;
                    int i7 = i2 - i;
                    System.arraycopy(obj, i6, obj, i, i7);
                    Object obj2 = this.mArray;
                    System.arraycopy(obj2, i6 << 1, obj2, i3, i7 << 1);
                }
                Object[] objArr2 = this.mArray;
                i3 = i2 << 1;
                objArr2[i3] = null;
                objArr2[i3 + 1] = null;
            } else {
                if (i4 > 8) {
                    i5 = i4 + (i4 >> 1);
                }
                Object obj3 = this.mHashes;
                Object obj4 = this.mArray;
                allocArrays(i5);
                if (i4 == this.mSize) {
                    if (i > 0) {
                        System.arraycopy(obj3, 0, this.mHashes, 0, i);
                        System.arraycopy(obj4, 0, this.mArray, 0, i3);
                    }
                    if (i < i2) {
                        int i8 = i + 1;
                        int i9 = i2 - i;
                        System.arraycopy(obj3, i8, this.mHashes, i, i9);
                        System.arraycopy(obj4, i8 << 1, this.mArray, i3, i9 << 1);
                    }
                } else {
                    throw new ConcurrentModificationException();
                }
            }
        }
        if (i4 == this.mSize) {
            this.mSize = i2;
            return v;
        }
        throw new ConcurrentModificationException();
    }

    public V setValueAt(int i, V v) {
        i = (i << 1) + 1;
        Object[] objArr = this.mArray;
        V v2 = objArr[i];
        objArr[i] = v;
        return v2;
    }

    public int size() {
        return this.mSize;
    }

    public String toString() {
        if (isEmpty()) {
            return "{}";
        }
        StringBuilder stringBuilder = new StringBuilder(this.mSize * 28);
        stringBuilder.append('{');
        for (int i = 0; i < this.mSize; i++) {
            if (i > 0) {
                stringBuilder.append(", ");
            }
            SimpleArrayMap keyAt = keyAt(i);
            String str = "(this Map)";
            if (keyAt != this) {
                stringBuilder.append(keyAt);
            } else {
                stringBuilder.append(str);
            }
            stringBuilder.append('=');
            keyAt = valueAt(i);
            if (keyAt != this) {
                stringBuilder.append(keyAt);
            } else {
                stringBuilder.append(str);
            }
        }
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    public V valueAt(int i) {
        return this.mArray[(i << 1) + 1];
    }
}
