package zerobase.mission1;

import com.google.gson.Gson;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WifiApiController {
    private String key = "734c637158616e7734396451775178";
    private String url = "http://openapi.seoul.go.kr:8088/" + key + "/json/TbPublicWifiInfo/";
    private OkHttpClient client = new OkHttpClient();
    private Gson json = new Gson();
    private WifiService wifiService = new WifiService();

    private final int BATCH_SIZE = 1000;

    public void setup(){
        // todo: 메서드 순서에 종속되지 않도록 수정
        WifiService wifiService = new WifiService();
        wifiService.dropTableIfExists();
        wifiService.createTable();
    }

    public void batchWifiInfo(){

        int totalPage = getWifiDatas(1, 1).getList_total_count();
        for (int i = 1; i <= totalPage; i += BATCH_SIZE) {
        WifiInfoDto data = getWifiDatas(i, i + BATCH_SIZE - 1);

        wifiService.batchWifiInfo(data.getRow());
    }
}

    private WifiInfoDto getWifiDatas(int start, int end){
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

    public void getData(){
        wifiService.select();
    }

}
