package me.tx.app.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;
import me.tx.app.R;

public class Toaster {
    Toast toast;
    Context context;
    public Toaster(Context context){
        this.context = context;
        /*toast = new Toast(context);*/
    }

    public void showToast(String msg) {
        int showtime=Toast.LENGTH_SHORT;
        if (msg.indexOf("禁用")!=-1)
        {
            showtime=Toast.LENGTH_LONG;
        }
        Toast toast=Toasty.normal(context, msg, showtime);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
       /* View mView = LayoutInflater.from(context).inflate(
                R.layout.toast_layout1, null);
        TextView popTips = mView.findViewById(R.id.text);
        popTips.setText(charSequence);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(mView);
        toast.show();*/
    }

    public void showToastLong(String msg) {
        int showtime=Toast.LENGTH_LONG;
        Toast toast=Toasty.normal(context, msg, showtime);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
       /* View mView = LayoutInflater.from(context).inflate(
                R.layout.toast_layout1, null);
        TextView popTips = mView.findViewById(R.id.text);
        popTips.setText(charSequence);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(mView);
        toast.show();*/
    }
}
