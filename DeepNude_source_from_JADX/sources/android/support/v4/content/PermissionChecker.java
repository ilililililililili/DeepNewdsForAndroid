package android.support.v4.content;

import android.content.Context;
import android.os.Binder;
import android.os.Process;
import android.support.v4.app.AppOpsManagerCompat;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class PermissionChecker {
    public static final int PERMISSION_DENIED = -1;
    public static final int PERMISSION_DENIED_APP_OP = -2;
    public static final int PERMISSION_GRANTED = 0;

    @Retention(RetentionPolicy.SOURCE)
    public @interface PermissionResult {
    }

    private PermissionChecker() {
    }

    public static int checkCallingOrSelfPermission(Context context, String str) {
        return checkPermission(context, str, Binder.getCallingPid(), Binder.getCallingUid(), Binder.getCallingPid() == Process.myPid() ? context.getPackageName() : null);
    }

    public static int checkCallingPermission(Context context, String str, String str2) {
        return Binder.getCallingPid() == Process.myPid() ? -1 : checkPermission(context, str, Binder.getCallingPid(), Binder.getCallingUid(), str2);
    }

    public static int checkPermission(Context context, String str, int i, int i2, String str2) {
        if (context.checkPermission(str, i, i2) == -1) {
            return -1;
        }
        str = AppOpsManagerCompat.permissionToOp(str);
        if (str == null) {
            return 0;
        }
        if (str2 == null) {
            String[] packagesForUid = context.getPackageManager().getPackagesForUid(i2);
            if (packagesForUid != null) {
                if (packagesForUid.length > 0) {
                    str2 = packagesForUid[0];
                }
            }
            return -1;
        }
        return AppOpsManagerCompat.noteProxyOpNoThrow(context, str, str2) != 0 ? -2 : 0;
    }

    public static int checkSelfPermission(Context context, String str) {
        return checkPermission(context, str, Process.myPid(), Process.myUid(), context.getPackageName());
    }
}
