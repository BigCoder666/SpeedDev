package me.tx.app.utils;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.PermissionChecker;

import java.util.ArrayList;

import pub.devrel.easypermissions.EasyPermissions;

public class PermissionLoader {
    Activity context;

    public PermissionLoader(Activity context) {
        this.context = context;
    }


    public boolean Load(String[] permissionArray) {
        if (EasyPermissions.hasPermissions(context, permissionArray)) {
            return true;
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(context, "使用该功能需要启用部分权限，请通过开启权限保证程序正常使用。",
                    9999, permissionArray);
            return false;
        }
    }

}
