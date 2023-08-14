package com.jy.wifi;

import com.google.gson.Gson;
import java.io.IOException;
import java.util.Objects;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WifiDataApi {
    private final String key = "734c637158616e7734396451775178";
    private final String url = "http://openapi.seoul.go.kr:8088/" + key + "/json/TbPublicWifiInfo/";
    private final OkHttpClient client = new OkHttpClient();
    private final Gson json = new Gson();

    public WifiInfoDto requestData(int start, int end) {
        WifiApiDto dto = null;
        int count = 0;
        try{
            Request request = new Request.Builder()
                .url(url+start+"/"+end)
                .build();

            Response response = client.newCall(request)
                .execute();

            String result = response.body().string();

            dto = json.fromJson(result, WifiApiDto.class);

        }catch (IOException e){
            e.printStackTrace();
        }

        return Objects.requireNonNull(dto).getTbPublicWifiInfo();
    }

}
