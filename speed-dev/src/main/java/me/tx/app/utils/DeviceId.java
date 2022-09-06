package me.tx.app.utils;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;

public class DeviceId {
//   d

    public static String getID(Context context) {
        //制造商 （Manufacturer）
        String manufacturer = android.os.Build.MANUFACTURER;

        //型号（Model）
        String model = android.os.Build.MODEL;

        //品牌（Brand）
        String brand = android.os.Build.BRAND;

        //设备名 （Device）
        String device = android.os.Build.DEVICE;

        //产品的名称
        String product = Build.PRODUCT;

        //设备序列号（Serial Number, SN）
        String serialNum = android.os.Build.SERIAL;

        String dId =
                manufacturer + "*"
                        + model + "*"
                        + brand + "*"
                        + device + "*"
                        + product + "*"
                        + serialNum + "*"
                        + Build.ID + "*"
                        + Settings.Secure.getString(context.getContentResolver(),
                        Settings.Secure.ANDROID_ID); ;
        return dId;
    }
}
