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
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.internal.Util;
import okio.BufferedSink;
import okio.Okio;
import okio.Source;

import static java.lang.System.currentTimeMillis;

public class UploadHelper {
    public final String TAG = "UploadHelper";

    public interface IProgress{
        void suc(IData iData);
        void fail(String message);
    }

    public UploadHelper(){
    }

    public void uploadImgs(final String action,final BaseActivity activity, final List<File> fileList, final IProgress iProgress){
        activity.center.showLoad();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 0;i<fileList.size();i++) {
                    try {
                        File compressedImageFile = new Compressor(activity)
                                .compressToFile(fileList.get(i));
                        fileList.set(i,compressedImageFile);
                    } catch (IOException e) {
                        e.printStackTrace();
                        iProgress.fail("compress failed");
                    }
                }

                MultipartBody.Builder builder =new MultipartBody.Builder().setType(MultipartBody.FORM);
                RequestBody requestBody;
                for(File file :fileList) {
                    RequestBody fileBody = RequestBody.create(MediaType.parse("image/png"), file);
                    try {
                        builder.addFormDataPart("picture", URLEncoder.encode(file.getAbsolutePath(),"UTF-8"), fileBody);
                    } catch (UnsupportedEncodingException e) {
                        iProgress.fail("urlencode failed");
                        e.printStackTrace();
                    }
                }
                requestBody = builder.build();

                Request request = new Request.Builder()
                        .url(action)
                        .post(requestBody)
                        .build();

                Response response = null;
                try {
                    Call call =new okhttp3.OkHttpClient().newCall(request);
                    response = call.execute();
                    String jsonString = response.body().string();
                    if(!response.isSuccessful()){
                        iProgress.fail(response.message());
                    }else{
                        IData iData=JSON.parseObject(jsonString,IData.class);
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

    public void uploadFace(final String action, final BaseActivity activity, final String userId, final File file, final IProgress iProgress){
        activity.center.showLoad();
        new Thread(new Runnable() {
            @Override
            public void run() {
                File compressedImageFile = null;
                try {
                    compressedImageFile = new Compressor(activity)
                            .compressToFile(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                MultipartBody.Builder builder =new MultipartBody.Builder().setType(MultipartBody.FORM);
                RequestBody requestBody;

                    RequestBody fileBody = RequestBody.create(MediaType.parse("image/jpg"), compressedImageFile);
                    try {
                        builder.addFormDataPart("file", URLEncoder.encode(file.getAbsolutePath(),"UTF-8"), fileBody);
                        builder.addFormDataPart("user_id",userId);
                    } catch (UnsupportedEncodingException e) {
                        iProgress.fail("urlencode failed");
                        e.printStackTrace();
                    }

                requestBody = builder.build();

                Request request = new Request.Builder()
                        .url(action)
                        .post(requestBody)
                        .build();

                Response response = null;
                try {
                    Call call =new okhttp3.OkHttpClient().newCall(request);
                    response = call.execute();
                    String jsonString = response.body().string();
                    if(!response.isSuccessful()){
                        iProgress.fail(response.message());
                    }else{
                        IData iData=JSON.parseObject(jsonString,IData.class);
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
}
