package me.tx.app.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.net.Uri;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.io.File;

public class PicassoLoader {
    private static PicassoLoader picassoLoader;
    private IDefult iDefult;

    public interface IDefult{
        int defultLoading();
        int defultError();
    }

    public static PicassoLoader getInstance(IDefult iDefult){
        if(picassoLoader==null){
            picassoLoader=new PicassoLoader();
            picassoLoader.iDefult = iDefult;
        }
        return picassoLoader;
    }

    public void loadImg(String url, ImageView imageView) {
        try {
            Picasso.get()
                    .load(url)
                    .error(iDefult.defultError())
                    .into(imageView);
        }catch (IllegalArgumentException e){
            e.printStackTrace();
        }
    }

    public void loadImg(String url, ImageView imageView,float x,float y) {
        try {
            Picasso.get()
                    .load(url)
                    .resize((int)x*150,(int)y*150)
                    .error(iDefult.defultError())
                    .into(imageView);
        }catch (IllegalArgumentException e){
            e.printStackTrace();
        }
    }

    public void loadImg(File file, ImageView imageView,float x,float y) {
        try {
            Picasso.get()
                    .load(file)
                    .resize((int)x*150,(int)y*150)
                    .error(iDefult.defultError())
                    .into(imageView);
        }catch (IllegalArgumentException e){
            e.printStackTrace();
        }
    }


    public void loadImg(File file, ImageView imageView) {
        try {
        Picasso.get()
                .load(file)
                .error(iDefult.defultError())
                .into(imageView);
        }catch (IllegalArgumentException e){
            e.printStackTrace();
        }

    }

    public void loadImg(int src, ImageView imageView) {
        try{
        Picasso.get()
                .load(src)
                .error(iDefult.defultError())
                .into(imageView);
        }catch (IllegalArgumentException e){
            e.printStackTrace();
        }
    }

    public void loadImg(Uri uri, ImageView imageView) {
        try{
        Picasso.get()
                .load(uri)
                .error(iDefult.defultError())
                .into(imageView);
        }catch (IllegalArgumentException e){
            e.printStackTrace();
        }
    }

    public void loadImg(String url, ImageView imageView,int corner) {
        try{
        Picasso.get()
                .load(url)
                .transform(new RoundedTransformation(corner))
                .error(iDefult.defultError())
                .into(imageView);
        }catch (IllegalArgumentException e){
            e.printStackTrace();
        }
    }

    public void loadImg(File file, ImageView imageView,int corner) {
        try{
        Picasso.get()
                .load(file)
                .transform(new RoundedTransformation(corner))
                .error(iDefult.defultError())
                .into(imageView);
        }catch (IllegalArgumentException e){
            e.printStackTrace();
        }
    }

    public void loadImg(int src, ImageView imageView,int corner) {
        try{
        Picasso.get()
                .load(src)
                .transform(new RoundedTransformation(corner))
                .error(iDefult.defultError())
                .into(imageView);
        }catch (IllegalArgumentException e){
            e.printStackTrace();
        }
    }

    public void loadImg(Uri uri, ImageView imageView,int corner) {
        try{
        Picasso.get()
                .load(uri)
                .transform(new RoundedTransformation(corner))
                .error(iDefult.defultError())
                .into(imageView);
        }catch (IllegalArgumentException e){
            e.printStackTrace();
        }
    }

    public class RoundedTransformation implements com.squareup.picasso.Transformation {
        private int radius;
        public RoundedTransformation(int radius) {
            this.radius = radius;
        }
        @Override
        public Bitmap transform(final Bitmap source) {
            final Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setShader(new BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
            Bitmap output = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(output);
            canvas.drawRoundRect(new RectF(0, 0, source.getWidth(), source.getHeight()), radius, radius, paint);
            if (source != output) {
                source.recycle();
            }
            return output;
        }

        @Override
        public String key() {
            return "rounded";
        }
    }

}
