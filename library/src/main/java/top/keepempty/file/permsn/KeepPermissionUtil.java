package top.keepempty.file.permsn;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @auth: frey tse
 */
public class KeepPermissionUtil {

    private static ArrayList<String> permissionList = new ArrayList<>();
    /**
     * 检查权限
     * @param context  上下文对象
     */
    public static boolean verifyPermissions(Context context) {
        if (isOverMarshmallow()) {
            if (context.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            }
            if (context.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            return permissionList.isEmpty();
        }
        return true;
    }

    public static void toRequestPermission(final Context context, OnKeepPermsnCallback permissionCallback) {
        // 检测权限是否注册
        checkPmsnRegManifest(context, permissionList);
        // 请求权限
        KeepPmsnFragment.newInstance(permissionList).preparePersimssion((Activity)context, permissionCallback);
    }

    /**
     * 检测存储相关权限是否已经在清单文件中进行注册
     *
     * @param context
     * @param requestPermissions 请求的权限组
     */
    private static void checkPmsnRegManifest(Context context, List<String> requestPermissions) {
        List<String> manifestPermissions = getManifestPermissions(context);
        if (manifestPermissions.isEmpty()) {
            throw new RuntimeException("No permissions are registered in the manifest file");
        }
        for (String permission : requestPermissions) {
            if (!manifestPermissions.contains(permission)) {
                throw new RuntimeException(permission + ": Permissions are not registered in the manifest file");
            }
        }
    }

    /**
     * 返回应用程序在清单文件中注册的权限
     */
    private static List<String> getManifestPermissions(Context context) {
        try {
            String[] requestedPermissions = context.getPackageManager().getPackageInfo(context.getPackageName(),
                    PackageManager.GET_PERMISSIONS).requestedPermissions;
            if (requestedPermissions == null || requestedPermissions.length == 0) {
                return new ArrayList<>();
            }
            return Arrays.asList(requestedPermissions);
        } catch (PackageManager.NameNotFoundException ignored) {
            return new ArrayList<>();
        }
    }

    /**
     * 是否是6.0以上版本
     */
    public static boolean isOverMarshmallow() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }


}
