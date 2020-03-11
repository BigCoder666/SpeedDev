package me.tx.app.utils;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;

public class Downloader {
    public Downloader() {

    }

    public boolean downloadFile(String url, Context context,String mimeType) {
        Uri uri;
        try {
            uri = Uri.parse(url);
        } catch (IllegalArgumentException e) {
            return false;
        }
        DownloadManager.Request request = new DownloadManager.Request(uri);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            request.setRequiresDeviceIdle(false);
            request.setRequiresCharging(false);
        }

        if(!mimeType.isEmpty()){
            request.setMimeType(mimeType);
        }
        request.setVisibleInDownloadsUi(true);
        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        //创建目录
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).mkdir();
        //设置文件存放路径
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, url.substring(url.lastIndexOf("/") + 1));
        //获取下载管理器
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);

//        if (context != null) {
//            context.startActivity(new android.content.Intent(DownloadManager.ACTION_VIEW_DOWNLOADS));//启动系统下载界面
//        }

        //将下载任务加入下载队列，否则不会进行下载
        downloadManager.enqueue(request);
        return true;
    }
}
