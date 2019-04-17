package top.keepempty.file.permsn;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import top.keepempty.file.library.R;

/**
 * @author : frey tse
 * @date : 2019-03-27
 */
public class KeepPmsnFragment extends Fragment {
    // 请求的权限
    private static final String PERMISSION_GROUP = "permission_group";
    // 请求码
    private static final String REQUEST_CODE = "request_code";
    // 请求设置界面
    private static final int REQUEST_CODE_SETTING = 1101;

    private static OnKeepPermsnCallback callback;

    public static KeepPmsnFragment newInstance(ArrayList<String> permissions) {

        KeepPmsnFragment fragment = new KeepPmsnFragment();
        Bundle bundle = new Bundle();
        int requestCode = new Random().nextInt(255);
        bundle.putInt(REQUEST_CODE, requestCode);
        bundle.putStringArrayList(PERMISSION_GROUP, permissions);
        fragment.setArguments(bundle);
        return fragment;
    }

    public void preparePersimssion(Activity activity,OnKeepPermsnCallback onPermissionCallback) {
        callback = onPermissionCallback;
        activity.getFragmentManager().beginTransaction().add(this, activity.getClass().getName()).commit();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        requestPersimssion();
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void requestPersimssion() {
        ArrayList<String> permissions = getArguments().getStringArrayList(PERMISSION_GROUP);
        requestPermissions(permissions.toArray(new String[permissions.size() - 1]), getArguments().getInt(REQUEST_CODE));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (!KeepPermissionUtil.verifyPermissions(getActivity())) {
            if(callback!=null){

            }
            showDialog(Arrays.asList(permissions));
            return;
        }
        getFragmentManager().beginTransaction().remove(this).commit();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(REQUEST_CODE_SETTING == requestCode ){
            requestPersimssion();
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void showDialog(final List<String> permissionList) {
        new AlertDialog.Builder(getActivity())
                .setTitle(R.string.tips)
                .setMessage(getString(R.string.content,getAppName(getActivity())))
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                getActivity().finish();
            }
        }).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                for (String per : permissionList) {
                    //永久拒绝
                    if (!shouldShowRequestPermissionRationale(per)) {
                        Uri myPackage = Uri.parse("package:" + getActivity().getApplication().getPackageName());
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, myPackage);
                        startActivityForResult(intent, REQUEST_CODE_SETTING);
                        return;
                    }
                    requestPersimssion();
                }
            }
        }).create().show();
    }


    /**
     * 获取应用程序名称
     */
    public static String getAppName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
