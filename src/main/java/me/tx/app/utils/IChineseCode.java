package me.tx.app.utils;

import android.widget.ImageView;

import me.tx.app.network.IArray;
import me.tx.app.network.IObject;
import me.tx.app.network.ParamList;

public interface IChineseCode {
    void 请求数组(String 接口, ParamList 参数, IArray 数组回调);
    void 请求对象(String 接口, ParamList 参数, IObject 对象回调);
    ParamList 构造参数();
    void 加载图片(String 地址, ImageView 图片);
    void 下载文件(String 地址);
}
