package android.support.v4.content.res;

import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.content.res.TypedArray;
import android.graphics.LinearGradient;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.util.Xml;
import java.util.List;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import p000a.p005b.p006a.C0015i;

final class GradientColorInflaterCompat {
    private static final int TILE_MODE_CLAMP = 0;
    private static final int TILE_MODE_MIRROR = 2;
    private static final int TILE_MODE_REPEAT = 1;

    static final class ColorStops {
        final int[] mColors;
        final float[] mOffsets;

        ColorStops(int i, int i2) {
            this.mColors = new int[]{i, i2};
            this.mOffsets = new float[]{0.0f, 1.0f};
        }

        ColorStops(int i, int i2, int i3) {
            this.mColors = new int[]{i, i2, i3};
            this.mOffsets = new float[]{0.0f, 0.5f, 1.0f};
        }

        ColorStops(List<Integer> list, List<Float> list2) {
            int size = list.size();
            this.mColors = new int[size];
            this.mOffsets = new float[size];
            for (int i = 0; i < size; i++) {
                this.mColors[i] = ((Integer) list.get(i)).intValue();
                this.mOffsets[i] = ((Float) list2.get(i)).floatValue();
            }
        }
    }

    private GradientColorInflaterCompat() {
    }

    private static ColorStops checkColors(ColorStops colorStops, int i, int i2, boolean z, int i3) {
        return colorStops != null ? colorStops : z ? new ColorStops(i, i3, i2) : new ColorStops(i, i2);
    }

    static Shader createFromXml(Resources resources, XmlPullParser xmlPullParser, Theme theme) {
        AttributeSet asAttributeSet = Xml.asAttributeSet(xmlPullParser);
        while (true) {
            int next = xmlPullParser.next();
            if (next == 2 || next == 1) {
                if (next == 2) {
                    return createFromXmlInner(resources, xmlPullParser, asAttributeSet, theme);
                }
                throw new XmlPullParserException("No start tag found");
            }
        }
        if (next == 2) {
            return createFromXmlInner(resources, xmlPullParser, asAttributeSet, theme);
        }
        throw new XmlPullParserException("No start tag found");
    }

    static Shader createFromXmlInner(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Theme theme) {
        XmlPullParser xmlPullParser2 = xmlPullParser;
        String name = xmlPullParser.getName();
        if (name.equals("gradient")) {
            Theme theme2 = theme;
            TypedArray obtainAttributes = TypedArrayUtils.obtainAttributes(resources, theme2, attributeSet, C0015i.GradientColor);
            float namedFloat = TypedArrayUtils.getNamedFloat(obtainAttributes, xmlPullParser2, "startX", C0015i.GradientColor_android_startX, 0.0f);
            float namedFloat2 = TypedArrayUtils.getNamedFloat(obtainAttributes, xmlPullParser2, "startY", C0015i.GradientColor_android_startY, 0.0f);
            float namedFloat3 = TypedArrayUtils.getNamedFloat(obtainAttributes, xmlPullParser2, "endX", C0015i.GradientColor_android_endX, 0.0f);
            float namedFloat4 = TypedArrayUtils.getNamedFloat(obtainAttributes, xmlPullParser2, "endY", C0015i.GradientColor_android_endY, 0.0f);
            float namedFloat5 = TypedArrayUtils.getNamedFloat(obtainAttributes, xmlPullParser2, "centerX", C0015i.GradientColor_android_centerX, 0.0f);
            float namedFloat6 = TypedArrayUtils.getNamedFloat(obtainAttributes, xmlPullParser2, "centerY", C0015i.GradientColor_android_centerY, 0.0f);
            int namedInt = TypedArrayUtils.getNamedInt(obtainAttributes, xmlPullParser2, "type", C0015i.GradientColor_android_type, 0);
            int namedColor = TypedArrayUtils.getNamedColor(obtainAttributes, xmlPullParser2, "startColor", C0015i.GradientColor_android_startColor, 0);
            String str = "centerColor";
            boolean hasAttribute = TypedArrayUtils.hasAttribute(xmlPullParser2, str);
            int namedColor2 = TypedArrayUtils.getNamedColor(obtainAttributes, xmlPullParser2, str, C0015i.GradientColor_android_centerColor, 0);
            int namedColor3 = TypedArrayUtils.getNamedColor(obtainAttributes, xmlPullParser2, "endColor", C0015i.GradientColor_android_endColor, 0);
            int namedInt2 = TypedArrayUtils.getNamedInt(obtainAttributes, xmlPullParser2, "tileMode", C0015i.GradientColor_android_tileMode, 0);
            float f = namedFloat5;
            float namedFloat7 = TypedArrayUtils.getNamedFloat(obtainAttributes, xmlPullParser2, "gradientRadius", C0015i.GradientColor_android_gradientRadius, 0.0f);
            obtainAttributes.recycle();
            ColorStops checkColors = checkColors(inflateChildElements(resources, xmlPullParser, attributeSet, theme), namedColor, namedColor3, hasAttribute, namedColor2);
            if (namedInt == 1) {
                float f2 = f;
                if (namedFloat7 > 0.0f) {
                    int[] iArr = checkColors.mColors;
                    return new RadialGradient(f2, namedFloat6, namedFloat7, iArr, checkColors.mOffsets, parseTileMode(namedInt2));
                }
                throw new XmlPullParserException("<gradient> tag requires 'gradientRadius' attribute with radial type");
            } else if (namedInt != 2) {
                return new LinearGradient(namedFloat, namedFloat2, namedFloat3, namedFloat4, checkColors.mColors, checkColors.mOffsets, parseTileMode(namedInt2));
            } else {
                return new SweepGradient(f, namedFloat6, checkColors.mColors, checkColors.mOffsets);
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(xmlPullParser.getPositionDescription());
        stringBuilder.append(": invalid gradient color tag ");
        stringBuilder.append(name);
        throw new XmlPullParserException(stringBuilder.toString());
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static android.support.v4.content.res.GradientColorInflaterCompat.ColorStops inflateChildElements(android.content.res.Resources r8, org.xmlpull.v1.XmlPullParser r9, android.util.AttributeSet r10, android.content.res.Resources.Theme r11) {
        /*
        r0 = r9.getDepth();
        r1 = 1;
        r0 = r0 + r1;
        r2 = new java.util.ArrayList;
        r3 = 20;
        r2.<init>(r3);
        r4 = new java.util.ArrayList;
        r4.<init>(r3);
    L_0x0012:
        r3 = r9.next();
        if (r3 == r1) goto L_0x008a;
    L_0x0018:
        r5 = r9.getDepth();
        if (r5 >= r0) goto L_0x0021;
    L_0x001e:
        r6 = 3;
        if (r3 == r6) goto L_0x008a;
    L_0x0021:
        r6 = 2;
        if (r3 == r6) goto L_0x0025;
    L_0x0024:
        goto L_0x0012;
    L_0x0025:
        if (r5 > r0) goto L_0x0012;
    L_0x0027:
        r3 = r9.getName();
        r5 = "item";
        r3 = r3.equals(r5);
        if (r3 != 0) goto L_0x0034;
    L_0x0033:
        goto L_0x0012;
    L_0x0034:
        r3 = p000a.p005b.p006a.C0015i.GradientColorItem;
        r3 = android.support.v4.content.res.TypedArrayUtils.obtainAttributes(r8, r11, r10, r3);
        r5 = p000a.p005b.p006a.C0015i.GradientColorItem_android_color;
        r5 = r3.hasValue(r5);
        r6 = p000a.p005b.p006a.C0015i.GradientColorItem_android_offset;
        r6 = r3.hasValue(r6);
        if (r5 == 0) goto L_0x006a;
    L_0x0048:
        if (r6 == 0) goto L_0x006a;
    L_0x004a:
        r5 = p000a.p005b.p006a.C0015i.GradientColorItem_android_color;
        r6 = 0;
        r5 = r3.getColor(r5, r6);
        r6 = p000a.p005b.p006a.C0015i.GradientColorItem_android_offset;
        r7 = 0;
        r6 = r3.getFloat(r6, r7);
        r3.recycle();
        r3 = java.lang.Integer.valueOf(r5);
        r4.add(r3);
        r3 = java.lang.Float.valueOf(r6);
        r2.add(r3);
        goto L_0x0012;
    L_0x006a:
        r8 = new org.xmlpull.v1.XmlPullParserException;
        r10 = new java.lang.StringBuilder;
        r10.<init>();
        r9 = r9.getPositionDescription();
        r10.append(r9);
        r9 = ": <item> tag requires a 'color' attribute and a 'offset' ";
        r10.append(r9);
        r9 = "attribute!";
        r10.append(r9);
        r9 = r10.toString();
        r8.<init>(r9);
        throw r8;
    L_0x008a:
        r8 = r4.size();
        if (r8 <= 0) goto L_0x0096;
    L_0x0090:
        r8 = new android.support.v4.content.res.GradientColorInflaterCompat$ColorStops;
        r8.<init>(r4, r2);
        return r8;
    L_0x0096:
        r8 = 0;
        return r8;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.content.res.GradientColorInflaterCompat.inflateChildElements(android.content.res.Resources, org.xmlpull.v1.XmlPullParser, android.util.AttributeSet, android.content.res.Resources$Theme):android.support.v4.content.res.GradientColorInflaterCompat$ColorStops");
    }

    private static TileMode parseTileMode(int i) {
        return i != 1 ? i != 2 ? TileMode.CLAMP : TileMode.MIRROR : TileMode.REPEAT;
    }
}
