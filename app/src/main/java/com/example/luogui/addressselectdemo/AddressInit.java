package com.example.luogui.addressselectdemo;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static android.content.Context.MODE_PRIVATE;

/**
 * 数据文件拷贝
 * Created by  luogui on 2017/5/23.
 */

public class AddressInit {

    private static AddressInit instance;
    private Context context;
    private String path;
    public static AddressInit getInstance(Context context){
        if (instance==null){
            synchronized (AddressInit.class){
                if (instance ==null){
                    instance = new AddressInit(context);
                }
            }
        }
        return instance;
    }

    private AddressInit(Context context) {
        this.context = context;
        path = "/data/data/"+context.getPackageName()+"/databases/";
        copyDBFiles();
    }

    private void copyDBFiles(){
        if (getDBFlagFromSP()) return;
        if(checkDBFileExist()) return;
        try {
            InputStream input = context.getResources().openRawResource(R.raw.area);
            File file = new File(path);
            file.mkdir();
            FileOutputStream fos = new FileOutputStream(path+"area.db");
            int data = 0;
            byte[] bytes = new byte[1024];
            while ((data=input.read(bytes))>0){
                fos.write(bytes,0,data);
            }
            fos.close();
            input.close();
            setDBFlag();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setDBFlag(){
        SharedPreferences sp = context.getSharedPreferences("DB_SET", MODE_PRIVATE);
        sp.edit().putBoolean("DB_FLAG",true).apply();
    }

    private boolean checkDBFileExist(){
        File file = new File(path+"area.db");
        return file.exists();
    }

    private boolean getDBFlagFromSP(){
        SharedPreferences sp = context.getSharedPreferences("DB_SET",MODE_PRIVATE);
        return sp.getBoolean("DB_FLAG",false);
    }
}
