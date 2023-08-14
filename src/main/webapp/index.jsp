<%@ page import="com.jy.wifi.WifiController" %>
<%@ page import="com.jy.wifi.Wifi" %>
<%@ page import="java.util.List" %>
<%@ page import="com.jy.wifi.WifiController" %>
<%@ page import="java.util.ArrayList" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Wifi Data List</title>
    <style>
      body {
        font-family: Arial, sans-serif;
        background-color: #f0f0f0;
        margin: 0;
        padding: 0;
        display: flex;
        flex-direction: column;
        align-items: center;
      }

      h1 {
        text-align: center;
        padding: 20px;
      }

      table {
        width: 80%;
        border-collapse: collapse;
        border: 1px solid #ddd;
        background-color: white;
        margin-top: 20px;
      }

      th, td {
        padding: 10px;
        text-align: left;
      }

      th {
        background-color: #f2f2f2;
      }

      tr:nth-child(even) {
        background-color: #f2f2f2;
      }
    </style>

    <script>
        function getGeo(){
          window.navigator.geolocation.getCurrentPosition( function(position){ //OK
                document.getElementById('latInput').value = position.coords.latitude;
                document.getElementById('lngInput').value = position.coords.longitude;
              } ,
              function(error){ //error
                switch(error.code){
                  case error.PERMISSION_DENIED:
                    str="사용자 거부";
                    break;
                  case error.POSITION_UNAVAILABLE:
                    str="지리정보 없음";
                    break;
                  case error.TIMEOUT:
                    str="시간 초과";
                    break;
                }
              });
        }
    </script>
</head>
<body>
<%
    WifiController wifiController = new WifiController();
    List<Wifi> wifis = new ArrayList<>();
    String lat = request.getParameter("lat");
    String lng = request.getParameter("lng");
    if (lat != null && lng != null) {
        wifis = wifiController.getDataByDistance(Double.parseDouble(lat.trim()), Double.parseDouble(lng.trim()));
    }
%>

<h1>와이파이 정보 검색</h1>

<p><a href="index.jsp"> 홈 </a> | <a href="searchHistory.jsp"> 위치 히스토리 목록 </a> | <a href="getWifiData.jsp"> Open API 와이파이 정보 가져오기 </a></p>


<button onclick="getGeo()">내 위치 가져오기</button><br>
<form action="index.jsp" method="get">
    <input id="latInput" type="text" name="lat" placeholder="위도">
    <input id="lngInput" type="text" name="lng" placeholder="경도">
    <input type="submit" value="검색">
</form>


<table border="1">
    <tr>
        <th>거리(km)</th>
        <th>관리번호</th>
        <th>자치구</th>
        <th>도로명주소</th>
        <th>상세주소</th>
        <th>설치위치(층)</th>
        <th>설치유형</th>
        <th>설치기관</th>
        <th>서비스구분</th>
        <th>망종류</th>
        <th>설치년도</th>
        <th>wifi 접속환경</th>
        <th>LAT</th>
        <th>LNT</th>
        <th>작업일자</th>

    </tr>

    <%
        for (Wifi wifi : wifis) {
    %>
    <tr>
        <td><%=Math.round(wifi.getDistance()*1000)/1000.0%></td>
        <td><%=wifi.getX_SWIFI_MGR_NO()%></td>
        <td><%=wifi.getX_SWIFI_WRDOFC()%></td>
        <td><%=wifi.getX_SWIFI_ADRES1()%></td>
        <td><%=wifi.getX_SWIFI_ADRES2()%></td>
        <td><%=wifi.getX_SWIFI_INSTL_FLOOR()%></td>
        <td><%=wifi.getX_SWIFI_INSTL_TY()%></td>
        <td><%=wifi.getX_SWIFI_INSTL_MBY()%></td>
        <td><%=wifi.getX_SWIFI_SVC_SE()%></td>
        <td><%=wifi.getX_SWIFI_CMCWR()%></td>
        <td><%=wifi.getX_SWIFI_CNSTC_YEAR()%></td>
        <td><%=wifi.getX_SWIFI_REMARS3()%></td>
        <td><%=wifi.getLAT()%></td>
        <td><%=wifi.getLNT()%></td>
        <td><%=wifi.getWORK_DTTM()%></td>
    </tr>
    <%
        }
    %>

</table>
</body>
</html>
