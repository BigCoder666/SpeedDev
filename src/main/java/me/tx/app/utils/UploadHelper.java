package me.tx.app.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.os.Environment;

import com.alibaba.fastjson.JSON;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import id.zelory.compressor.Compressor;
import me.tx.app.network.IData;
import me.tx.app.network.IResponse;
import me.tx.app.network.ParamList;
import me.tx.app.ui.activity.BaseActivity;
import okhttp3.Call;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UploadHelper {
    public final String TAG = "UploadHelper";

    public interface IProgress {
        void suc(IData iData);

        void fail(String message);
    }

    public UploadHelper() {
    }

    public void uploadImgs(final String action,
                           final BaseActivity activity,
                           final List<File> fileList,
                           final IProgress iProgress) {
        activity.center.showLoad();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < fileList.size(); i++) {
                    try {
                        File compressedImageFile = new Compressor(activity)
                                .compressToFile(fileList.get(i));
                        fileList.set(i, compressedImageFile);
                    } catch (IOException e) {
                        e.printStackTrace();
                        iProgress.fail("compress failed");
                    }
                }

                MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
                RequestBody requestBody;
                for (File file : fileList) {
                    int lastIndexOf = file.getAbsolutePath().lastIndexOf(".");
                    //获取文件的后缀名 .jpg
                    String suffix = file.getAbsolutePath().substring(lastIndexOf);
                    RequestBody fileBody = RequestBody.create(MediaType.parse("image/png"), file);
                    try {
                        builder.addFormDataPart("file", URLEncoder.encode("LibsOfSpeedDev-"+System.currentTimeMillis()+"-img"+suffix, "UTF-8"), fileBody);
                    } catch (UnsupportedEncodingException e) {
                        iProgress.fail("urlencode failed");
                        e.printStackTrace();
                    }
                }
                requestBody = builder.build();

                Request.Builder b = new Request.Builder();
                for (String key : activity.getHeader().keySet()) {
                    b.addHeader(key, activity.getHeader().get(key));
                }
                Request request =b.url(action)
                        .post(requestBody)
                        .build();

                Response response = null;
                try {
                    Call call = new OkHttpClient().newCall(request);
                    response = call.execute();
                    String jsonString = response.body().string();
                    if (!response.isSuccessful()) {
                        iProgress.fail(response.message());
                    } else {
                        IData iData = JSON.parseObject(jsonString, IData.class);
                        iProgress.suc(iData);
                    }
                    activity.center.dismissLoad();
                } catch (IOException e) {
                    e.printStackTrace();
                    iProgress.fail("error");
                    activity.center.dismissLoad();
                }
            }
        }).start();
    }

    public void uploadFace(final String action, final BaseActivity activity, final byte[] face, final IProgress iProgress) {
        activity.center.showLoad();
        new Thread(new Runnable() {
            @Override
            public void run() {
                final Request.Builder b = new Request.Builder();
                for (String key : activity.getHeader().keySet()) {
                    b.addHeader(key, activity.getHeader().get(key));
                }
                RequestBody fileBody = RequestBody.create(MediaType.parse("image/jpg"), face);
                MultipartBody.Builder mb = new MultipartBody.Builder();
                mb.addFormDataPart("file", "myface", fileBody);

                RequestBody requestBody = mb.build();

                Request request = b.url(action)
                        .post(requestBody)
                        .build();

                Response response = null;
                try {
                    Call call = new OkHttpClient().newCall(request);
                    response = call.execute();
                    String jsonString = response.body().string();
                    IData iData = JSON.parseObject(jsonString, IData.class);
                    if (!response.isSuccessful()) {
                        iProgress.fail(iData.getMessage());
                    } else {
                        iProgress.suc(iData);
                    }
                    activity.center.dismissLoad();
                } catch (IOException e) {
                    e.printStackTrace();
                    iProgress.fail("error");
                    activity.center.dismissLoad();
                }
            }
        }).start();
    }

    public void uploadImgWithParam(final String action,
                                   final BaseActivity activity,
                                   final byte[] img,
                                   final ParamList paramList,
                                   final IProgress iProgress) {
        activity.center.showLoad();
        new Thread(new Runnable() {
            @Override
            public void run() {
                final Request.Builder b = new Request.Builder();
                for (String key : activity.getHeader().keySet()) {
                    b.addHeader(key, activity.getHeader().get(key));
                }
                RequestBody fileBody = RequestBody.create(MediaType.parse("image/jpg"), img);
                MultipartBody.Builder mb = new MultipartBody.Builder();
                mb.addFormDataPart("file", "file", fileBody);
                for (ParamList.IParam p : paramList.getParamList()) {
                    mb.addFormDataPart(p.getKey(), p.getValue().toString());
                }

                RequestBody requestBody = mb.build();

                Request request = b.url(action)
                        .post(requestBody)
                        .build();

                Response response = null;
                try {
                    Call call = new OkHttpClient().newCall(request);
                    response = call.execute();
                    String jsonString = response.body().string();
                    IData iData = JSON.parseObject(jsonString, IData.class);
                    if (!response.isSuccessful()) {
                        iProgress.fail(iData.getMessage());
                    } else {
                        iProgress.suc(iData);
                    }
                    activity.center.dismissLoad();
                } catch (IOException e) {
                    e.printStackTrace();
                    iProgress.fail("error");
                    activity.center.dismissLoad();
                }
            }
        }).start();
    }


//    public void uploadID(final String action, final BaseActivity activity, final String front, final String back, final IProgress iProgress) {
//        activity.center.showLoad();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                final Request.Builder b = new Request.Builder();
//                for (String key : activity.getHeader().keySet()) {
//                    b.addHeader(key, activity.getHeader().get(key));
//                }
//                RequestBody frontBody = RequestBody.create(MediaType.parse("image/jpg"), new File(front));
//                RequestBody backBody = RequestBody.create(MediaType.parse("image/jpg"), new File(back));
//                MultipartBody.Builder mb = new MultipartBody.Builder();
//                mb.addFormDataPart("files", "front", frontBody);
//                mb.addFormDataPart("files", "back", backBody);
//
//                RequestBody requestBody = mb.build();
//
//                Request request =b.url(action)
//                        .post(requestBody)
//                        .build();
//
//                Response response = null;
//                try {
//                    Call call = new OkHttpClient().newCall(request);
//                    response = call.execute();
//                    String jsonString = response.body().string();
//                    IData iData = JSON.parseObject(jsonString, IData.class);
//                    if (!response.isSuccessful()) {
//                        iProgress.fail(iData.getMessage());
//                    } else {
//                        iProgress.suc(iData);
//                    }
//                    activity.center.dismissLoad();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    iProgress.fail("error");
//                    activity.center.dismissLoad();
//                }
//            }
//        }).start();
//    }
}
