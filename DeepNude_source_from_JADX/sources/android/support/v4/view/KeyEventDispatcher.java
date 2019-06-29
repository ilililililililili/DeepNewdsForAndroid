package android.support.v4.view;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface.OnKeyListener;
import android.os.Build.VERSION;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.Window.Callback;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class KeyEventDispatcher {
    private static boolean sActionBarFieldsFetched = false;
    private static Method sActionBarOnMenuKeyMethod = null;
    private static boolean sDialogFieldsFetched = false;
    private static Field sDialogKeyListenerField;

    public interface Component {
        boolean superDispatchKeyEvent(KeyEvent keyEvent);
    }

    private KeyEventDispatcher() {
    }

    private static boolean actionBarOnMenuKeyEventPre28(android.app.ActionBar r6, android.view.KeyEvent r7) {
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
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:282)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:200)
	at jadx.api.JadxDecompiler$$Lambda$8/318857719.run(Unknown Source)
*/
        /*
        r0 = sActionBarFieldsFetched;
        r1 = 1;
        r2 = 0;
        if (r0 != 0) goto L_0x001a;
    L_0x0006:
        r0 = r6.getClass();	 Catch:{ NoSuchMethodException -> 0x0018 }
        r3 = "onMenuKeyEvent";	 Catch:{ NoSuchMethodException -> 0x0018 }
        r4 = new java.lang.Class[r1];	 Catch:{ NoSuchMethodException -> 0x0018 }
        r5 = android.view.KeyEvent.class;	 Catch:{ NoSuchMethodException -> 0x0018 }
        r4[r2] = r5;	 Catch:{ NoSuchMethodException -> 0x0018 }
        r0 = r0.getMethod(r3, r4);	 Catch:{ NoSuchMethodException -> 0x0018 }
        sActionBarOnMenuKeyMethod = r0;	 Catch:{ NoSuchMethodException -> 0x0018 }
    L_0x0018:
        sActionBarFieldsFetched = r1;
    L_0x001a:
        r0 = sActionBarOnMenuKeyMethod;
        if (r0 == 0) goto L_0x002d;
    L_0x001e:
        r1 = new java.lang.Object[r1];	 Catch:{ IllegalAccessException -> 0x002d, IllegalAccessException -> 0x002d }
        r1[r2] = r7;	 Catch:{ IllegalAccessException -> 0x002d, IllegalAccessException -> 0x002d }
        r6 = r0.invoke(r6, r1);	 Catch:{ IllegalAccessException -> 0x002d, IllegalAccessException -> 0x002d }
        r6 = (java.lang.Boolean) r6;	 Catch:{ IllegalAccessException -> 0x002d, IllegalAccessException -> 0x002d }
        r6 = r6.booleanValue();	 Catch:{ IllegalAccessException -> 0x002d, IllegalAccessException -> 0x002d }
        return r6;
    L_0x002d:
        return r2;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.view.KeyEventDispatcher.actionBarOnMenuKeyEventPre28(android.app.ActionBar, android.view.KeyEvent):boolean");
    }

    private static boolean activitySuperDispatchKeyEventPre28(Activity activity, KeyEvent keyEvent) {
        activity.onUserInteraction();
        Window window = activity.getWindow();
        if (window.hasFeature(8)) {
            ActionBar actionBar = activity.getActionBar();
            if (keyEvent.getKeyCode() == 82 && actionBar != null && actionBarOnMenuKeyEventPre28(actionBar, keyEvent)) {
                return true;
            }
        }
        if (window.superDispatchKeyEvent(keyEvent)) {
            return true;
        }
        View decorView = window.getDecorView();
        if (ViewCompat.dispatchUnhandledKeyEventBeforeCallback(decorView, keyEvent)) {
            return true;
        }
        return keyEvent.dispatch(activity, decorView != null ? decorView.getKeyDispatcherState() : null, activity);
    }

    private static boolean dialogSuperDispatchKeyEventPre28(Dialog dialog, KeyEvent keyEvent) {
        OnKeyListener dialogKeyListenerPre28 = getDialogKeyListenerPre28(dialog);
        if (dialogKeyListenerPre28 != null && dialogKeyListenerPre28.onKey(dialog, keyEvent.getKeyCode(), keyEvent)) {
            return true;
        }
        Window window = dialog.getWindow();
        if (window.superDispatchKeyEvent(keyEvent)) {
            return true;
        }
        View decorView = window.getDecorView();
        if (ViewCompat.dispatchUnhandledKeyEventBeforeCallback(decorView, keyEvent)) {
            return true;
        }
        return keyEvent.dispatch(dialog, decorView != null ? decorView.getKeyDispatcherState() : null, dialog);
    }

    public static boolean dispatchBeforeHierarchy(View view, KeyEvent keyEvent) {
        return ViewCompat.dispatchUnhandledKeyEventBeforeHierarchy(view, keyEvent);
    }

    public static boolean dispatchKeyEvent(Component component, View view, Callback callback, KeyEvent keyEvent) {
        boolean z = false;
        if (component == null) {
            return false;
        }
        if (VERSION.SDK_INT >= 28) {
            return component.superDispatchKeyEvent(keyEvent);
        }
        if (callback instanceof Activity) {
            return activitySuperDispatchKeyEventPre28((Activity) callback, keyEvent);
        }
        if (callback instanceof Dialog) {
            return dialogSuperDispatchKeyEventPre28((Dialog) callback, keyEvent);
        }
        if ((view != null && ViewCompat.dispatchUnhandledKeyEventBeforeCallback(view, keyEvent)) || component.superDispatchKeyEvent(keyEvent)) {
            z = true;
        }
        return z;
    }

    private static android.content.DialogInterface.OnKeyListener getDialogKeyListenerPre28(android.app.Dialog r3) {
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
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:282)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:200)
	at jadx.api.JadxDecompiler$$Lambda$8/318857719.run(Unknown Source)
*/
        /*
        r0 = sDialogFieldsFetched;
        if (r0 != 0) goto L_0x0016;
    L_0x0004:
        r0 = 1;
        r1 = android.app.Dialog.class;	 Catch:{ NoSuchFieldException -> 0x0014 }
        r2 = "mOnKeyListener";	 Catch:{ NoSuchFieldException -> 0x0014 }
        r1 = r1.getDeclaredField(r2);	 Catch:{ NoSuchFieldException -> 0x0014 }
        sDialogKeyListenerField = r1;	 Catch:{ NoSuchFieldException -> 0x0014 }
        r1 = sDialogKeyListenerField;	 Catch:{ NoSuchFieldException -> 0x0014 }
        r1.setAccessible(r0);	 Catch:{ NoSuchFieldException -> 0x0014 }
    L_0x0014:
        sDialogFieldsFetched = r0;
    L_0x0016:
        r0 = sDialogKeyListenerField;
        if (r0 == 0) goto L_0x0021;
    L_0x001a:
        r3 = r0.get(r3);	 Catch:{ IllegalAccessException -> 0x0021 }
        r3 = (android.content.DialogInterface.OnKeyListener) r3;	 Catch:{ IllegalAccessException -> 0x0021 }
        return r3;
    L_0x0021:
        r3 = 0;
        return r3;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.view.KeyEventDispatcher.getDialogKeyListenerPre28(android.app.Dialog):android.content.DialogInterface$OnKeyListener");
    }
}
