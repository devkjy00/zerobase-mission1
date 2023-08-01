import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiController {

    public static void main(String[] args) {
        connect();

    }

    private static void connect(){
        try{
            String key = "734c637158616e7734396451775178";
            String s = "http://openapi.seoul.go.kr:8088/" + key + "/json/TbPublicWifiInfo/1/1000";
            URL url = new URL(s);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // 요청 결과를 읽어올 BufferedReader 생성
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                // 요청 결과를 읽어오기 위한 변수와 임시 저장용 변수
                String inputLine;
                StringBuilder response = new StringBuilder();

                // BufferedReader를 통해 응답 데이터 읽기
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // 응답 결과 출력
                System.out.println(response.toString());
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }


}
