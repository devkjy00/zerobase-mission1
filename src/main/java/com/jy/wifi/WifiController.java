package com.jy.wifi;


import java.util.List;
import javax.servlet.http.HttpServlet;

public class WifiController extends HttpServlet {
    private final WifiService wifiService = new WifiService();
    private final WifiDataApi wifiDataApi = new WifiDataApi();

    public void setup(){
        // todo: 메서드 순서에 종속되지 않도록 수정
        wifiService.dropTableIfExists();
        wifiService.createTable();
    }

    public void batchWifiInfo(){
        WifiInfoDto data = null;
        int totalPage = wifiDataApi.requestData(1, 1).getList_total_count();
        final int BATCH_SIZE = 1000;

        for (int i = 1; i <= totalPage; i += BATCH_SIZE) {
        data = wifiDataApi.requestData(i, i + BATCH_SIZE - 1);
        wifiService.batchWifiInfo(data.getRow());
        }
    }

    public List<Wifi> getData(){
        return wifiService.getWifiInfos();
    }

    public List<Wifi> getDataByDistance(double lat, double lng ){
        return wifiService.getWifiInfosByDistance(lat, lng);
    }

    public List<SearchHistory> getSearchHistory(){
        return wifiService.getHistorys();
    }

}