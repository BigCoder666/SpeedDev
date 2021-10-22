package me.tx.app.utils;

import android.app.Activity;

import pub.devrel.easypermissions.EasyPermissions;

public class PermissionLoader {
    Activity context;

    public PermissionLoader(Activity context) {
        this.context = context;
    }


    public boolean Load(String[] permissionArray) {
        if (EasyPermissions.hasPermissions(context.getApplicationContext(), permissionArray)) {
            return true;
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(context, "使用该功能需要启用部分权限，请通过开启权限保证程序正常使用。",
                    9999, permissionArray);
            return false;
        }
    }

    public boolean Load(String[] permissionArray,int code) {
        if (EasyPermissions.hasPermissions(context.getApplicationContext(), permissionArray)) {
            return true;
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(context, "使用该功能需要启用部分权限，请通过开启权限保证程序正常使用。",
                    code, permissionArray);
            return false;
        }
    }

}
