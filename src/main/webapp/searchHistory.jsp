<%@ page import="com.jy.wifi.Wifi" %>
<%@ page import="com.jy.wifi.SearchHistory" %>
<%@ page import="com.jy.wifi.WifiController" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: jy_air
  Date: 2023/08/14
  Time: 3:34 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
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

    <title>Title</title>
</head>
<body>
<a href="index.jsp"> 홈 </a>

<%
    WifiController controller = new WifiController();
    List<SearchHistory> historys = controller.getSearchHistory();
%>
<table border="1">
    <tr>
        <th>ID</th>
        <th>LAT</th>
        <th>LNT</th>
        <th>조회일자<th>
        <th>비고</th>
    </tr>
<%
    for (SearchHistory history : historys) {
%>
    <tr>
        <td><%=history.getId()%></td>
        <td><%=history.getLat()%></td>
        <td><%=history.getLng()%></td>
        <td><%=history.getDate()%></td>
        <td><button> 삭제 </button><td>
    </tr>
<%
    }
%>
</table>

</body>
</html>
