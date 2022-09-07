package com.wewin.speeddev.demo;

import com.wewin.spinner.CommonSpinner;

/**
 * @author tx
 * @date 2022/9/7 15:37
 */
public class SpinnerData implements CommonSpinner.NameId {
    public String name = "";
    public String id="";

    public SpinnerData(String n,String i){
        name = n;
        id = i;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getId() {
        return id;
    }
}
