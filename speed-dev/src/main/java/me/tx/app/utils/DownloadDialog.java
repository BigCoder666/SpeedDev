package me.tx.app.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.SeekBar;

import me.tx.app.R;

public class DownloadDialog {
    Context activity;
    AlertDialog dialog;
    SeekBar seekBar;

    public DownloadDialog(Context context){
        activity = context;
        dialog=create();
    }

    public void setProgress(int progress){
        seekBar.setProgress(progress);
        if(progress>=98){
            dialog.dismiss();
        }
    }

    private AlertDialog create(){
        View v = LayoutInflater.from(activity).inflate(R.layout.dialog_download,null);
        seekBar = v.findViewById(R.id.progress);
        AlertDialog dialog = new AlertDialog.Builder(activity)
                .setCancelable(false)
                .setView(v).create();
        dialog.show();
        return dialog;
    }
}
