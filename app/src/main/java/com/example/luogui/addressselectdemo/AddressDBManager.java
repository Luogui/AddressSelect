package com.example.luogui.addressselectdemo;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据库查询
 * Created by  luogui on 2017/5/23.
 */

public class AddressDBManager {

    private SQLiteDatabase database;

    public AddressDBManager() {
        database = SQLiteDatabase.openDatabase("/data/data/"+App.application.getPackageName()+"/databases/area.db", null, SQLiteDatabase.OPEN_READWRITE);
    }

    //获取省级列表
    public List<Address> getProvinceList() {
    	Cursor cursor = database.query("S_Province", null, null, null, null, null, null);
    	List<Address> list=new ArrayList<>();
         if (cursor.moveToFirst()) {
             do {
                 // 遍历Cursor对象，取出数据并打印 
                 String provinceName = cursor.getString(cursor. getColumnIndex("ProvinceName"));
                 int provinceId = cursor.getInt(cursor.getColumnIndex ("ProvinceID"));
                 Address province = new Address(provinceId, provinceName);
                 list.add(province);
             }while (cursor.moveToNext()); 
         } 
         cursor.close();
		return list;
	}
    
    public List<Address> getCityList(int province_id) {
    	List<Address> list=new ArrayList<Address>();
    	if (province_id == 0) {
    		return list;
    	}
    	Cursor cursor = database.query("S_City", null,"ProvinceID="+province_id, null, null, null, null);
         if (cursor.moveToFirst()) {
             do {
                 // 遍历Cursor对象，取出数据并打印 
                 String cityName = cursor.getString(cursor. getColumnIndex("CityName"));
                 int cityId = cursor.getInt(cursor.getColumnIndex ("CityID"));
                 Address city = new Address(cityId, cityName);
                 list.add(city);

             }while (cursor.moveToNext()); 
         } 
         cursor.close(); 
		return list;
	}

    public List<Address> getDistrictList(int cityId) {
    	List<Address> list=new ArrayList<Address>();
    	Cursor cursor = database.query("S_District", null,"CityID="+cityId, null, null, null, null);
         if (cursor.moveToFirst()) {
             do {
                 // 遍历Cursor对象，取出数据并打印
                 String districtName = cursor.getString(cursor. getColumnIndex("DistrictName"));
                 int districtId = cursor.getInt(cursor.getColumnIndex ("DistrictID"));
                 Address district = new Address(districtId, districtName);
                 list.add(district);
             }while (cursor.moveToNext());
         }
         cursor.close();
		return list;
	}


}
