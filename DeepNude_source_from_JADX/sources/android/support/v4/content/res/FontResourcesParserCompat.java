package android.support.v4.content.res;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Build.VERSION;
import android.support.v4.provider.FontRequest;
import android.util.Base64;
import android.util.TypedValue;
import android.util.Xml;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import p000a.p005b.p006a.C0015i;

public class FontResourcesParserCompat {
    private static final int DEFAULT_TIMEOUT_MILLIS = 500;
    public static final int FETCH_STRATEGY_ASYNC = 1;
    public static final int FETCH_STRATEGY_BLOCKING = 0;
    public static final int INFINITE_TIMEOUT_VALUE = -1;
    private static final int ITALIC = 1;
    private static final int NORMAL_WEIGHT = 400;

    public interface FamilyResourceEntry {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface FetchStrategy {
    }

    public static final class FontFileResourceEntry {
        private final String mFileName;
        private boolean mItalic;
        private int mResourceId;
        private int mTtcIndex;
        private String mVariationSettings;
        private int mWeight;

        public FontFileResourceEntry(String str, int i, boolean z, String str2, int i2, int i3) {
            this.mFileName = str;
            this.mWeight = i;
            this.mItalic = z;
            this.mVariationSettings = str2;
            this.mTtcIndex = i2;
            this.mResourceId = i3;
        }

        public String getFileName() {
            return this.mFileName;
        }

        public int getResourceId() {
            return this.mResourceId;
        }

        public int getTtcIndex() {
            return this.mTtcIndex;
        }

        public String getVariationSettings() {
            return this.mVariationSettings;
        }

        public int getWeight() {
            return this.mWeight;
        }

        public boolean isItalic() {
            return this.mItalic;
        }
    }

    public static final class FontFamilyFilesResourceEntry implements FamilyResourceEntry {
        private final FontFileResourceEntry[] mEntries;

        public FontFamilyFilesResourceEntry(FontFileResourceEntry[] fontFileResourceEntryArr) {
            this.mEntries = fontFileResourceEntryArr;
        }

        public FontFileResourceEntry[] getEntries() {
            return this.mEntries;
        }
    }

    public static final class ProviderResourceEntry implements FamilyResourceEntry {
        private final FontRequest mRequest;
        private final int mStrategy;
        private final int mTimeoutMs;

        public ProviderResourceEntry(FontRequest fontRequest, int i, int i2) {
            this.mRequest = fontRequest;
            this.mStrategy = i;
            this.mTimeoutMs = i2;
        }

        public int getFetchStrategy() {
            return this.mStrategy;
        }

        public FontRequest getRequest() {
            return this.mRequest;
        }

        public int getTimeout() {
            return this.mTimeoutMs;
        }
    }

    private FontResourcesParserCompat() {
    }

    private static int getType(TypedArray typedArray, int i) {
        if (VERSION.SDK_INT >= 21) {
            return typedArray.getType(i);
        }
        TypedValue typedValue = new TypedValue();
        typedArray.getValue(i, typedValue);
        return typedValue.type;
    }

    public static FamilyResourceEntry parse(XmlPullParser xmlPullParser, Resources resources) {
        while (true) {
            int next = xmlPullParser.next();
            if (next == 2 || next == 1) {
                if (next == 2) {
                    return readFamilies(xmlPullParser, resources);
                }
                throw new XmlPullParserException("No start tag found");
            }
        }
        if (next == 2) {
            return readFamilies(xmlPullParser, resources);
        }
        throw new XmlPullParserException("No start tag found");
    }

    public static List<List<byte[]>> readCerts(Resources resources, int i) {
        if (i == 0) {
            return Collections.emptyList();
        }
        TypedArray obtainTypedArray = resources.obtainTypedArray(i);
        try {
            if (obtainTypedArray.length() == 0) {
                List<List<byte[]>> emptyList = Collections.emptyList();
                return emptyList;
            }
            List<List<byte[]>> arrayList = new ArrayList();
            if (getType(obtainTypedArray, 0) == 1) {
                for (i = 0; i < obtainTypedArray.length(); i++) {
                    int resourceId = obtainTypedArray.getResourceId(i, 0);
                    if (resourceId != 0) {
                        arrayList.add(toByteArrayList(resources.getStringArray(resourceId)));
                    }
                }
            } else {
                arrayList.add(toByteArrayList(resources.getStringArray(i)));
            }
            obtainTypedArray.recycle();
            return arrayList;
        } finally {
            obtainTypedArray.recycle();
        }
    }

    private static FamilyResourceEntry readFamilies(XmlPullParser xmlPullParser, Resources resources) {
        String str = "font-family";
        xmlPullParser.require(2, null, str);
        if (xmlPullParser.getName().equals(str)) {
            return readFamily(xmlPullParser, resources);
        }
        skip(xmlPullParser);
        return null;
    }

    private static FamilyResourceEntry readFamily(XmlPullParser xmlPullParser, Resources resources) {
        TypedArray obtainAttributes = resources.obtainAttributes(Xml.asAttributeSet(xmlPullParser), C0015i.FontFamily);
        String string = obtainAttributes.getString(C0015i.FontFamily_fontProviderAuthority);
        String string2 = obtainAttributes.getString(C0015i.FontFamily_fontProviderPackage);
        String string3 = obtainAttributes.getString(C0015i.FontFamily_fontProviderQuery);
        int resourceId = obtainAttributes.getResourceId(C0015i.FontFamily_fontProviderCerts, 0);
        int integer = obtainAttributes.getInteger(C0015i.FontFamily_fontProviderFetchStrategy, 1);
        int integer2 = obtainAttributes.getInteger(C0015i.FontFamily_fontProviderFetchTimeout, DEFAULT_TIMEOUT_MILLIS);
        obtainAttributes.recycle();
        if (string == null || string2 == null || string3 == null) {
            List arrayList = new ArrayList();
            while (xmlPullParser.next() != 3) {
                if (xmlPullParser.getEventType() == 2) {
                    if (xmlPullParser.getName().equals("font")) {
                        arrayList.add(readFont(xmlPullParser, resources));
                    } else {
                        skip(xmlPullParser);
                    }
                }
            }
            return arrayList.isEmpty() ? null : new FontFamilyFilesResourceEntry((FontFileResourceEntry[]) arrayList.toArray(new FontFileResourceEntry[arrayList.size()]));
        } else {
            while (xmlPullParser.next() != 3) {
                skip(xmlPullParser);
            }
            return new ProviderResourceEntry(new FontRequest(string, string2, string3, readCerts(resources, resourceId)), integer, integer2);
        }
    }

    private static FontFileResourceEntry readFont(XmlPullParser xmlPullParser, Resources resources) {
        TypedArray obtainAttributes = resources.obtainAttributes(Xml.asAttributeSet(xmlPullParser), C0015i.FontFamilyFont);
        int i = obtainAttributes.getInt(obtainAttributes.hasValue(C0015i.FontFamilyFont_fontWeight) ? C0015i.FontFamilyFont_fontWeight : C0015i.FontFamilyFont_android_fontWeight, NORMAL_WEIGHT);
        boolean z = 1 == obtainAttributes.getInt(obtainAttributes.hasValue(C0015i.FontFamilyFont_fontStyle) ? C0015i.FontFamilyFont_fontStyle : C0015i.FontFamilyFont_android_fontStyle, 0);
        int i2 = obtainAttributes.hasValue(C0015i.FontFamilyFont_ttcIndex) ? C0015i.FontFamilyFont_ttcIndex : C0015i.FontFamilyFont_android_ttcIndex;
        String string = obtainAttributes.getString(obtainAttributes.hasValue(C0015i.FontFamilyFont_fontVariationSettings) ? C0015i.FontFamilyFont_fontVariationSettings : C0015i.FontFamilyFont_android_fontVariationSettings);
        int i3 = obtainAttributes.getInt(i2, 0);
        i2 = obtainAttributes.hasValue(C0015i.FontFamilyFont_font) ? C0015i.FontFamilyFont_font : C0015i.FontFamilyFont_android_font;
        int resourceId = obtainAttributes.getResourceId(i2, 0);
        String string2 = obtainAttributes.getString(i2);
        obtainAttributes.recycle();
        while (xmlPullParser.next() != 3) {
            skip(xmlPullParser);
        }
        return new FontFileResourceEntry(string2, i, z, string, i3, resourceId);
    }

    private static void skip(XmlPullParser xmlPullParser) {
        int i = 1;
        while (i > 0) {
            int next = xmlPullParser.next();
            if (next == 2) {
                i++;
            } else if (next == 3) {
                i--;
            }
        }
    }

    private static List<byte[]> toByteArrayList(String[] strArr) {
        List<byte[]> arrayList = new ArrayList();
        for (String decode : strArr) {
            arrayList.add(Base64.decode(decode, 0));
        }
        return arrayList;
    }
}
