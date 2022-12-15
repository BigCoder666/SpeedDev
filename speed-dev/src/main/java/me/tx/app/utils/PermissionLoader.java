package me.tx.app.utils;

import android.app.Activity;

import me.tx.app.Config;
import me.tx.app.ui.activity.BaseActivity;
import pub.devrel.easypermissions.EasyPermissions;

public class PermissionLoader {
    BaseActivity context;

    public PermissionLoader(BaseActivity context) {
        this.context = context;
    }

    public boolean hasPermission(String[] permissionArray){
        return EasyPermissions.hasPermissions(context, permissionArray);
    }

    public boolean Load(String[] permissionArray) {
        if (EasyPermissions.hasPermissions(context.getApplicationContext(), permissionArray)) {
            return true;
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(context, "使用该功能需要启用部分权限，请通过开启权限保证程序正常使用。",
                    Config.LOAD_PERMISSION_CODE, permissionArray);
            return false;
        }
    }

}
