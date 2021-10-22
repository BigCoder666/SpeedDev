package me.tx.app.utils;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

import java.util.Timer;
import java.util.TimerTask;

import me.tx.app.ui.activity.BaseActivity;

public class CodeText60 extends AppCompatTextView {
    private String defultString = "发送验证码";
    private String waitString = "S";
    private int count = 60;
    private BaseActivity activity;

    public interface IStart{
        void start();
    }

    Timer timer;
    TimerTask timerTask;

    boolean canClick= true;

    public CodeText60(Context context) {
        super(context);
    }

    public CodeText60(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CodeText60(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void init(BaseActivity context){
        activity =context;
        setText(defultString);
    }

    public void click(IStart iStart){
        if(canClick==false){
            return;
        }
        iStart.start();
        timer =new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                count--;
                if(activity==null){
                    CodeText60.this.cancel();
                }
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(count>0){
                            setText(count+waitString);
                        }else {
                            CodeText60.this.cancel();
                            setText(defultString);
                            canClick = true;
                            count = 60;
                        }
                    }
                });
            }
        };
        timer.schedule(timerTask,0,1000);
        canClick = false;
    }

    public void cancel(){
        if(timer!=null) {
            timer.cancel();
            timer = null;
        }
        if(timerTask!=null){
            timerTask.cancel();
            timerTask=null;
        }
    }
}
