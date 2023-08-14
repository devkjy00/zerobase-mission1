<%@ page import="com.jy.wifi.WifiController" %>
<%@ page import="com.jy.wifi.WifiController" %><%--
  Created by IntelliJ IDEA.
  User: jy_air
  Date: 2023/08/09
  Time: 3:24 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>



<%
    WifiController wifiController = new WifiController();
    wifiController.setup();
    wifiController.batchWifiInfo();
%>

<h1> 데이터를 가져왔습니다 </h1>
<a href="index.jsp"> 홈으로 돌아가기 </a>



</body>
</html>
