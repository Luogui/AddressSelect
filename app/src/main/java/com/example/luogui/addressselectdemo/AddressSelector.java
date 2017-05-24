package com.example.luogui.addressselectdemo;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * 地址选择dialog
 * Created by  luogui on 2017/5/23.
 */
public class AddressSelector {

    private List<Address> provinceList ;
    private List<String> provinceList_string ;
    private List<Address> cityList ;
    private List<String> cityList_string ;
    private List<Address> districtList ;
    private List<String> districtList_string ;
    private AddressDBManager dbManager;
    private String[] address;
    private static AddressSelector instance;

    public static AddressSelector getInstance(){
        if (instance==null){
            synchronized (AddressSelector.class){
                if (instance ==null){
                    instance = new AddressSelector();
                }
            }
        }
        return instance;
    }

    private AddressSelector(){
        dbManager = new AddressDBManager();
    }

    public AddressSelector init(){
        initProvince();
        initCity(1);
        initDistrict(1);
        return this;
    }

    private void initProvince() {
        if (provinceList==null){
            provinceList = new ArrayList<>();
        }
        if (provinceList_string==null){
            provinceList_string = new ArrayList<>();
        }
        provinceList_string.clear();
        provinceList = dbManager.getProvinceList();
        for (int i=0;i<provinceList.size();i++){

            provinceList_string.add(provinceList.get(i).getAddressName());
        }
    }
    private void initCity(int index){
        if (cityList==null){
            cityList = new ArrayList<>();
        }
        if (cityList_string==null){
            cityList_string = new ArrayList<>();
        }
        cityList = dbManager.getCityList(index);
        cityList_string.clear();
        for (int i=0;i<cityList.size();i++){
            cityList_string.add(cityList.get(i).getAddressName());
        }
    }
    private List<Address> initDistrict(int index){
        if (districtList==null){
            districtList = new ArrayList<>();
        }
        if (districtList_string==null){
            districtList_string = new ArrayList<>();
        }
        districtList = dbManager.getDistrictList(index);
        districtList_string.clear();
        for (int i=0;i<districtList.size();i++){
            districtList_string.add(districtList.get(i).getAddressName());
        }
        return districtList;
    }


    public  void selectAddress(Context context, final AddressSelectListener selectListener) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = View.inflate(context, R.layout.dialog_address_select, null);
        builder.setView(view);
        builder.setTitle("请选择地址");
        final WheelView wvProvince = (WheelView) view.findViewById(R.id.wv_province);
        final WheelView wvCity = (WheelView) view.findViewById(R.id.wv_city);
        final WheelView wvDistrict = (WheelView) view.findViewById(R.id.wv_district);
        wvProvince.setItems(provinceList_string);
        wvCity.setItems(cityList_string);
        wvDistrict.setItems(districtList_string);
        address = new String[3];
        wvProvince.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
              @Override
              public void onSelected(int selectedIndex, String item) {
                  address[0] = item;
                  initCity(provinceList.get(selectedIndex-1).getAddressId());
                  wvCity.setSeletion(0);
                  wvDistrict.setSeletion(0);
                  initDistrict(cityList.get(0).getAddressId());
                  address[1] = null;
                  address[2] = null;
                  wvCity.setItems(cityList_string);
                  wvDistrict.setItems(districtList_string);
              }
          });
        wvCity.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                address[1] = item;
                initDistrict(cityList.get(selectedIndex-1).getAddressId());
                wvDistrict.setSeletion(0);
                address[2] = null;
                wvDistrict.setItems(districtList_string);
            }
        });
        wvDistrict.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                address[2] = item;
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() { //设置确定按钮
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //不滚动的时候 不触发wheelView的监听事件
                if (address[0] == null) address[0] = provinceList_string.get(0);
                if (address[1] == null) address[1] = cityList_string.get(0);
                if (address[2] == null) {
                    if (districtList.size()>0) address[2] = districtList_string.get(0);
                    else address[2] = "";
                }
                selectListener.onAddressSelect(address[0],address[1],address[2]);

                dialog.dismiss();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() { //设置取消按钮
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    public  interface AddressSelectListener{
         void onAddressSelect(String province, String city, String district);
    }

}
