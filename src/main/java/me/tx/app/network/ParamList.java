package me.tx.app.network;

import com.alibaba.fastjson.JSON;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class ParamList {
    public interface IParam<T>{
        String getKey();
        T getValue();
    }

    public static class ObjectParam implements IParam<Object>{
        String key;
        Object value;

        public ObjectParam(String key,Object value){
            this.key=key;
            this.value = value;
        }

        @Override
        public String getKey() {
            return key;
        }

        @Override
        public Object getValue() {
            return value;
        }
    }

    public static class StringParam implements IParam<String>{
        String key;
        String value;

        public StringParam(String key,String value){
            this.key=key;
            this.value = value;
        }

        @Override
        public String getKey() {
            return key;
        }

        @Override
        public String getValue() {
            return value;
        }
    }

    public ArrayList<IParam> getParamList() {
        return paramList;
    }

    private ArrayList<ParamList.IParam> paramList =new ArrayList<>();

    public ParamList copy(){
        ParamList copyList = new ParamList();
        copyList.getParamList().addAll(paramList);
        return copyList;
    }

    public ParamList add(ParamList.IParam param){
        paramList.add(param);
        return this;
    }

    public ParamList add(String key,String value){
        paramList.add(new StringParam(key,value));
        return this;
    }

    public ParamList addObject(String key,Object value){
        paramList.add(new ObjectParam(key,value));
        return this;
    }

    public IParam get(int p){
        return paramList.get(p);
    }

    public IParam get(String key){
        for(IParam iParam: paramList){
            if(iParam.getKey().equals(key)){
                return iParam;
            }
        }
        return null;
    }

    public boolean containsKey(String key){
        for(IParam iParam: paramList){
            if(iParam.getKey().equals(key)){
                return true;
            }
        }
        return false;
    }

    public String getJsonString(){
        HashMap<String,Object> json = new HashMap();
        for(IParam iParam:paramList){
            json.put(iParam.getKey(),iParam.getValue());
        }
        String jstr =JSON.toJSONString(json);
        return jstr;
    }

    public class SortByKey implements Comparator {
        public int compare(Object p1, Object p2) {
            IParam s1 = (IParam) p1;
            IParam s2 = (IParam) p2;
            Collator collator = Collator.getInstance(java.util.Locale.CHINA);
            return collator.getCollationKey(s1.getKey()).compareTo(collator.getCollationKey(s2.getKey()));
        }
    }

    public String getRequestString(){
        String str="";
        Collections.sort(paramList, new SortByKey());
        for(int i = 0; i < paramList.size();i++){
            str = str + paramList.get(i).getKey()+"="+ paramList.get(i).getValue();
            if(i!=paramList.size()-1){
                str = str+"&";
            }
        }
        return str;
    }
}
