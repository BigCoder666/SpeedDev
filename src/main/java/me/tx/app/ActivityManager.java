//package me.tx.app;
//
//import android.app.Activity;
//
//import java.util.HashMap;
//
//import me.tx.app.ui.activity.BaseActivity;
//
//public class ActivityManager {
//    private HashMap<String,Activity> activityHashMap =new HashMap<>();
//
//    private static ActivityManager activityManager;
//    public static ActivityManager getInstance(){
//        if(activityManager == null){
//            activityManager=new ActivityManager();
//        }
//        return activityManager;
//    }
//
//    public void add(Activity activity){
//        activityHashMap.put(activity.getClass().getSimpleName(),activity);
//    }
//
//    public void finish(Class c){
//        Activity activity = activityHashMap.get(c.getSimpleName());
//        if(activity!=null) {
//            activityHashMap.get(c.getSimpleName()).finish();
//            activityHashMap.remove(c.getSimpleName());
//        }
//    }
//
//    public void finishAll(){
//        for(String key : activityHashMap.keySet()){
//            Activity activity =activityHashMap.get(key);
//            if(activity!=null) {
//                activity.finish();
//            }
//        }
//        activityHashMap.clear();
//    }
//
//    public void finishOther(Class c){
//        for(String key : activityHashMap.keySet()){
//            if(!c.getSimpleName().equals(key)) {
//                Activity activity =activityHashMap.get(key);
//                if(activity!=null) {
//                    activity.finish();
//                }
//            }
//        }
//        activityHashMap.clear();
//    }
//}
