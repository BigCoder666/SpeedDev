package me.tx.app.ui.activity;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.viewbinding.ViewBinding;

import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.jaeger.library.StatusBarUtil;
import com.luck.picture.lib.basic.PictureSelector;
import com.luck.picture.lib.config.SelectMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.interfaces.OnResultCallbackListener;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//import me.tx.app.ActivityManager;
import me.tx.app.Config;
import me.tx.app.R;
import me.tx.app.network.HttpBuilder;
import me.tx.app.network.IResponse;
import me.tx.app.network.Mapper;
import me.tx.app.network.ParamList;
import me.tx.app.utils.AndroidBug5497Workaround;
import me.tx.app.utils.DownloadInfo;
import me.tx.app.utils.Downloader;
import me.tx.app.utils.LoadingController;
import me.tx.app.utils.NotificationHelper;
import me.tx.app.utils.PermissionLoader;
import me.tx.app.utils.PicassoEngine;
import me.tx.app.utils.PicassoLoader;
import me.tx.app.utils.ShareGetter;
import me.tx.app.utils.Toaster;
import me.tx.app.utils.UploadHelper;
import okhttp3.Cookie;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

import org.greenrobot.eventbus.EventBus;

public abstract class BaseActivity<VB extends ViewBinding> extends AppCompatActivity implements PicassoLoader.IDefult, LoadingController.ILoadSrc, IResponse.BadToken,EasyPermissions.PermissionCallbacks  {
    public Center center;

    public VB vb;

    //虚函数区开始
    public abstract HashMap<String,String> getHeader();

    public abstract void setView();

    public abstract void resume();

    public abstract void pause();

    public abstract VB getVb();

    public abstract void stop();

    public abstract void destroy();

    public abstract void load();
    //虚函数区结束

    //重写区开始
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        overridePendingTransition(R.anim.move_in,R.anim.fade_out);

        super.onCreate(savedInstanceState);

        center = new Center(this);

        AndroidBug5497Workaround.assistActivity(this);

        EventBus.getDefault().register(this);

        vb = getVb();

        setContentView(vb.getRoot());
        //view设置
        setView();

        //view加载
        load();
    }

    @Override
    public void finish() {
        super.finish();
    }

    @Override
    public void onResume() {
        super.onResume();
        resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        pause();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        center.dismissLoad();
        stop();
    }

    @Override
    public void onDestroy() {
        destroy();
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> list) {
        if(center.iPermission!=null){
            center.iPermission.onGranted(requestCode,list);
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> list) {
        if(center.iPermission!=null){
            center.iPermission.onDenied(requestCode,list);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
    //重写区结束

    //通用方法区开始
    private void selectImg(int max,IGetImgCallback callback){
        PictureSelector.create(BaseActivity.this)
                .openGallery(SelectMimeType.ofImage())
                .setImageEngine(PicassoEngine.createPicassoEngine())
                .setMaxSelectNum(max)
                .forResult(new OnResultCallbackListener<LocalMedia>() {
                    @Override
                    public void onResult(ArrayList<LocalMedia> result) {
                        List<String> pathResultList = new ArrayList<>();
                        for (LocalMedia l:result){
                            pathResultList.add(l.getRealPath());
                        }
                        callback.pathResult(pathResultList);
                    }

                    @Override
                    public void onCancel() {

                    }
                });
    }

    public interface IGetImgCallback{
        void pathResult(List<String> result);
    }

    public void getImgWithListener(final int max,IGetImgCallback callback){
        center.loadPermission(new String[]{
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE}, new Center.IPermission() {
            @Override
            public void pass() {
                selectImg(max,callback);
            }

            @Override
            public void notPass() {

            }

            @Override
            public void onDenied(int requestCode, List<String> list) {

            }

            @Override
            public void onGranted(int requestCode, List<String> list) {
                selectImg(max,callback);
            }
        });
    }

    /**
     * 检测GPS、位置权限是否开启
     */
    public boolean showGPSContacts() {
        LocationManager lm = (LocationManager) this.getSystemService(this.LOCATION_SERVICE);
        boolean ok = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!ok){
            center.toast("系统检测到未开启GPS定位服务,请开启");
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivityForResult(intent, 123);
            return false;
        }else {
            return true;
        }
    }

    public void hideSystemKeyBoard() {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
    }

    public void showSystemKeyBoard(View view) {
        if (view.requestFocus()) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public void statusBarTextBlack() {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }

    public void statusBarTextWhite() {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
    }
    //通用方法区结束

    //事件核心类
    public static class Center {
        PermissionLoader permissionLoader;
        PicassoLoader picassoLoader;
        Downloader downloader;
        Toaster toaster;
        LoadingController loadingController;
        ShareGetter shareGetter;
        BaseActivity activity;
        UploadHelper uploadHelper;
        NotificationHelper notificationHelper;
        IPermission iPermission;

        public Center(BaseActivity ac) {
            activity = ac;
            //初始化开始

            //权限申请器
            permissionLoader = new PermissionLoader(activity);
            //图片加载器
            picassoLoader = PicassoLoader.getInstance(activity);
            //文件下载器
            downloader = new Downloader(ac);
            //提示器
            toaster = new Toaster(activity);
            //加载器
            loadingController = new LoadingController(activity, activity);
            //缓存器
            shareGetter = new ShareGetter(activity);
            //上传器
            uploadHelper = new UploadHelper();
            //notification
            notificationHelper = new NotificationHelper();
            //状态栏颜色控制
            statusBarSet();
        }

        public interface IPermission{
            void pass();
            void notPass();
            void onDenied(int requestCode, List<String> list);
            void onGranted(int requestCode, List<String> list);
        }

        public void notificationWithFile(String title,String info,int big,int small,File file){
            notificationHelper.show(activity,title,info,big,small,openFileIntent(activity,file));
        }

        public PendingIntent openFileIntent(Context context,File file){
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            String extension = android.webkit.MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(file).toString());
            String mimetype = android.webkit.MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
            //7.0以上需要
            if (Build.VERSION.SDK_INT >= 24) {
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                String pkgName = activity.getPackageName();
                Uri uri = FileProvider.getUriForFile(activity, pkgName + ".fileProvider", file);
                intent.setDataAndType(uri, mimetype);
            } else {
                intent.setDataAndType(Uri.fromFile(file), mimetype);
            }
            PendingIntent pi = PendingIntent.getActivity(context,0,intent,0);
            return pi;
        }

        public void openFile(File file) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            String extension = android.webkit.MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(file).toString());
            String mimetype = android.webkit.MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
            //7.0以上需要
            if (Build.VERSION.SDK_INT >= 24) {
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                String pkgName = activity.getPackageName();
                Uri uri = FileProvider.getUriForFile(activity, pkgName + ".fileProvider", file);
                intent.setDataAndType(uri, mimetype);
            } else {
                intent.setDataAndType(Uri.fromFile(file), mimetype);
            }
            activity.startActivity(intent);
        }

        public UploadHelper getUploadHelper() {
            return uploadHelper;
        }

        public HttpBuilder.IRequestFunction reqJson(String action, Object o) {
            loadingController.show();
            return new HttpBuilder(loadingController).initJson(action,o,activity.getHeader());
        }

        public HttpBuilder.IRequestFunction reqForm(String action, Mapper mapper) {
            loadingController.show();
            return new HttpBuilder(loadingController).initForm(action,mapper,activity.getHeader());
        }

        public void statusBarSet() {
            StatusBarUtil.setTranslucent(activity);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
                activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_OVERSCAN);
            }
            activity.statusBarTextBlack();
        }

        public void loadPermission(String[] permission,IPermission iPermission) {
            this.iPermission = iPermission;
            boolean ok = permissionLoader.Load(permission);
            if(ok){
                iPermission.pass();
            }else {
                iPermission.notPass();
            }
        }

        public void loadImg(String url, ImageView imageView) {
            picassoLoader.loadImg(url, imageView);
        }
        public void loadImg(String url, ImageView imageView, float x, float y) {
            picassoLoader.loadImg(url, imageView, x, y);
        }
        public void loadImg(File file, ImageView imageView, float x, float y) {
            picassoLoader.loadImg(file, imageView, x, y);
        }
        public void loadImg(File file, ImageView imageView) {
            picassoLoader.loadImg(file, imageView);
        }
        public void loadImg(int src, ImageView imageView) {
            picassoLoader.loadImg(src, imageView);
        }
        public void loadImg(Uri uri, ImageView imageView) {
            picassoLoader.loadImg(uri, imageView);
        }
        public void loadImg(String url, ImageView imageView, int corner) {
            picassoLoader.loadImg(url, imageView, corner);
        }
        public void loadImg(File file, ImageView imageView, int corner) {
            picassoLoader.loadImg(file, imageView, corner);
        }
        public void loadImg(int src, ImageView imageView, int corner) {
            picassoLoader.loadImg(src, imageView, corner);
        }
        public void loadImg(int src, String url, ImageView imageView) {
            if (src == 0) {
                picassoLoader.loadImg(url, imageView);
            } else {
                picassoLoader.loadImg(src, imageView);
            }

        }
        public void loadImg(Uri uri, ImageView imageView, int corner) {
            picassoLoader.loadImg(uri, imageView, corner);
        }


        public void download(final DownloadInfo downloadInfo, final Downloader.IFinish iFinish) {
            loadPermission(new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
            }, new IPermission() {
                @Override
                public void pass() {
                    downloader.downloadFile(downloadInfo,iFinish);
                }

                @Override
                public void notPass() {

                }

                @Override
                public void onDenied(int requestCode, List<String> list) {

                }

                @Override
                public void onGranted(int requestCode, List<String> list) {
                    downloader.downloadFile(downloadInfo,iFinish);
                }
            });
        }

        public ShareGetter getShareGetter() {
            return shareGetter;
        }

        public void toast(final String msg) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    toaster.showToast(msg);
                }
            });

        }

        public void toastLong(final String msg) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    toaster.showToastLong(msg);
                }
            });

        }

        public void showLoad() {
            if (loadingController == null) {
                return;
            }
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    loadingController.show();
                }
            });
        }

        public void showLoad(final boolean cancelable) {
            if (loadingController == null) {
                return;
            }
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    loadingController.show(cancelable);
                }
            });
        }

        public void dismissLoad() {
            if (loadingController == null) {
                return;
            }
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    loadingController.dismiss();
                }
            });
        }
    }
}
