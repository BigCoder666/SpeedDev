package me.tx.app.ui.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.jaeger.library.StatusBarUtil;

import java.io.File;
import java.lang.reflect.Field;
import java.util.List;

import me.tx.app.ActivityManager;
import me.tx.app.network.HttpFormArray;
import me.tx.app.network.HttpFormObject;
import me.tx.app.network.HttpGetArray;
import me.tx.app.network.HttpGetObject;
import me.tx.app.network.HttpJsonArray;
import me.tx.app.network.HttpJsonObject;
import me.tx.app.network.IResponse;
import me.tx.app.network.ParamList;
import me.tx.app.network.Signer;
import me.tx.app.utils.BasePopupWindow;
import me.tx.app.utils.Downloader;
import me.tx.app.utils.LoadingController;
import me.tx.app.utils.PermissionLoader;
import me.tx.app.utils.PicassoLoader;
import me.tx.app.utils.ShareGetter;
import me.tx.app.utils.Toaster;
import me.tx.app.utils.UploadHelper;
import pub.devrel.easypermissions.EasyPermissions;

public abstract class BaseActivity extends AppCompatActivity implements PicassoLoader.IDefult, LoadingController.ILoadSrc, IResponse.BadToken,EasyPermissions.PermissionCallbacks  {
    public Center center;
    public View root;
    public BasePopupWindow popupWindow;

    public final static int PERMISSION_REQUEST_INSTALL = 1991;

    public abstract List<ParamList.IParam> getDefultParam();

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }

    public static class Center {
        PermissionLoader permissionLoader;
        PicassoLoader picassoLoader;
        Downloader downloader;
        Toaster toaster;
        LoadingController loadingController;
        ShareGetter shareGetter;
        BaseActivity activity;
        List<ParamList.IParam> defultParam;
        UploadHelper uploadHelper;

        public Center(BaseActivity ac, List<ParamList.IParam> defultParam) {
            activity = ac;
            this.defultParam = defultParam;
            //初始化开始
            //权限申请器
            permissionLoader = new PermissionLoader(activity);
            //图片加载器
            picassoLoader = PicassoLoader.getInstance(activity);
            //文件下载器
            downloader = new Downloader();
            //提示器
            toaster = new Toaster(activity);
            //加载器
            loadingController = new LoadingController(activity, activity);
            //缓存器
            shareGetter = new ShareGetter(activity);
            //上传器
            uploadHelper = new UploadHelper();
            //状态栏颜色控制
            statusBarSet();
        }

        public UploadHelper getUploadHelper() {
            return uploadHelper;
        }

        public void req(String action, ParamList paramList, IResponse iResponse) {
            if (defultParam.size() > 0) {
                for (ParamList.IParam iParam : defultParam) {
                    paramList.add(iParam);
                }
            }
//            paramList = Signer.sign(paramList);
            if (iResponse.iamGet()) {
                if (iResponse.iamArray()) {
                    new HttpGetArray().build(action, paramList, iResponse);
                } else if (iResponse.iamObj()) {
                    new HttpGetObject().build(action, paramList, iResponse);
                }
            }
            if (iResponse.iamForm()) {
                if (iResponse.iamArray()) {
                    new HttpFormArray().build(action, paramList, iResponse);
                } else if (iResponse.iamObj()) {
                    new HttpFormObject().build(action, paramList, iResponse);
                }
            } else if (iResponse.iamJson()) {
                if (iResponse.iamArray()) {
                    new HttpJsonArray().build(action, paramList, iResponse);
                } else if (iResponse.iamObj()) {
                    new HttpJsonObject().build(action, paramList, iResponse);
                }
            }
        }


        public void statusBarSet() {
            StatusBarUtil.setTranslucent(activity);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
                activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_OVERSCAN);
            }
            activity.statusBarTextBlack();
        }

        public void dismissLoading() {
            loadingController.dismiss();
        }

        public boolean loadPermission(String[] permission) {
            return permissionLoader.Load(permission);
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


        public void download(String url) {
            boolean result = downloader.downloadFile(url, activity, "");
            if (!result) {
                toast("下载失败");
            }
        }

        public void download(String url, String mimeType) {
            boolean result = downloader.downloadFile(url, activity, mimeType);
            if (!result) {
                toast("下载失败");
            }
        }

        public boolean downloadApk(String url) {
            boolean haveInstallPermission = true;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                haveInstallPermission = activity.getPackageManager().canRequestPackageInstalls();
            }
            if (!haveInstallPermission) {
                Uri packageURI = Uri.parse("package:" + activity.getPackageName());
                Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, packageURI);
                activity.startActivityForResult(intent, PERMISSION_REQUEST_INSTALL);
                return false;
            }
            boolean result = downloader.downloadFile(url, activity, "application/vnd.android.package-archive");
            if (!result) {
                toast("下载失败");
            }
            return result;
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

    public abstract int setContentViewId();

    public abstract void setView();

    public abstract void resume();

    public abstract void pause();

    public abstract void bindid();

    public abstract void stop();

    public abstract void destroy();

    public abstract void load();

    public void statusBarTextBlack() {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }

    public void statusBarTextWhite() {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityManager.getInstance().add(this);

        root = LayoutInflater.from(this).inflate(setContentViewId(), null);

        center = new Center(this, getDefultParam());

        setContentView(setContentViewId());

        bindid();
        //view设置
        setView();

        //view加载
        load();
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
        center.dismissLoading();
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
        // Some permissions have been granted
        // ...
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> list) {
        // Some permissions have been denied
        // ...
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
}
