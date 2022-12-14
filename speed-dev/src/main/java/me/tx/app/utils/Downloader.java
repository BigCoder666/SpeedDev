package me.tx.app.utils;

import android.app.DownloadManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;

import com.alibaba.fastjson.JSON;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import me.tx.app.ui.activity.BaseActivity;

public class Downloader {
    BaseActivity context;
    public interface IFinish{
        void finish();
    }

    public void clear(){
        SharedPreferences sharedPreferences = context.getSharedPreferences("Downloader",Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().commit();
    }

    public Downloader(BaseActivity context) {
        this.context = context;
    }

    private void addDownloadRecord(DownloadInfo downloadInfo){
        SharedPreferences sharedPreferences = context.getSharedPreferences("Downloader",Context.MODE_PRIVATE);
        HashSet<String> set =  (HashSet<String>) sharedPreferences.getStringSet("path",new HashSet<String>());
        set.add(JSON.toJSONString(downloadInfo));
        sharedPreferences.edit().putStringSet("path",set).commit();
    }

    public ArrayList<DownloadInfo> getDownloadRecord(){
        SharedPreferences sharedPreferences = context.getSharedPreferences("Downloader",Context.MODE_PRIVATE);
        HashSet<String> set = (HashSet<String>) sharedPreferences.getStringSet("path",new HashSet<String>());
        ArrayList<DownloadInfo> infoList = new ArrayList<>();
        for(String str:set){
            infoList.add(JSON.parseObject(str,DownloadInfo.class));
        }
        return infoList;
    }

    public boolean hasVideoId(String vid){
        SharedPreferences sharedPreferences = context.getSharedPreferences("Downloader",Context.MODE_PRIVATE);
        HashSet<String> set = (HashSet<String>) sharedPreferences.getStringSet("path",new HashSet<String>());
        boolean have = false;
        for(String str:set){
            DownloadInfo d =JSON.parseObject(str,DownloadInfo.class);
            if(d.vid.equals(vid)){
                have = true;
                break;
            }
        }
        return have;
    }

    public void downloadFile(final DownloadInfo downloadInfo, final IFinish iFinish){
        final DownloadDialog downloadDialog =new DownloadDialog(context);
        FileDownloader.setup(context);
        FileDownloader.getImpl().create(downloadInfo.url)
                .setPath(downloadInfo.saveLocalPath)
                .setListener(new FileDownloadListener() {
                    @Override
                    protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                    }

                    @Override
                    protected void connected(BaseDownloadTask task, String etag, boolean isContinue, int soFarBytes, int totalBytes) {
                    }

                    @Override
                    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        downloadDialog.setProgress((int)((((float)soFarBytes)/((float)totalBytes))*100));
                    }

                    @Override
                    protected void blockComplete(BaseDownloadTask task) {
                    }

                    @Override
                    protected void retry(final BaseDownloadTask task, final Throwable ex, final int retryingTimes, final int soFarBytes) {
                    }

                    @Override
                    protected void completed(BaseDownloadTask task) {
                        addDownloadRecord(downloadInfo);
                        downloadDialog.setProgress(100);
                        context.center.toast("????????????");
                        iFinish.finish();
                    }

                    @Override
                    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                    }

                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {
                        downloadDialog.setProgress(100);
                        context.center.toast("????????????");
                    }

                    @Override
                    protected void warn(BaseDownloadTask task) {
                    }
                }).start();
    }

//    public boolean downloadFile(String url, Context context,String mimeType) {
//        Uri uri;
//        try {
//            uri = Uri.parse(url);
//        } catch (IllegalArgumentException e) {
//            return false;
//        }
//        DownloadManager.Request request = new DownloadManager.Request(uri);
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            request.setRequiresDeviceIdle(false);
//            request.setRequiresCharging(false);
//        }
//
//        if(!mimeType.isEmpty()){
//            request.setMimeType(mimeType);
//        }
//        request.setVisibleInDownloadsUi(true);
//        request.allowScanningByMediaScanner();
//        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
//        //????????????
//        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).mkdir();
//        //????????????????????????
//        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, url.substring(url.lastIndexOf("/") + 1));
//        //?????????????????????
//        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
//
////        if (context != null) {
////            context.startActivity(new android.content.Intent(DownloadManager.ACTION_VIEW_DOWNLOADS));//????????????????????????
////        }
//
//        //????????????????????????????????????????????????????????????
//        downloadManager.enqueue(request);
//        return true;
//    }
}
