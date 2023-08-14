package com.jy.wifi;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class WifiService {

    private PreparedStatement pstmt = null;
    private Connection con = null;
    private ResultSet rs = null;

    private String url = "jdbc:sqlite:wifi.sqlite";
    private String user = "user";
    private String password = "password";

    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public int batchWifiInfo(List<Wifi> wifis){

        try {
            con = DriverManager.getConnection(url);
            String sql = "INSERT INTO WIFI (MG_NO, DISTRICT, NAME, STREET_ADDR, DETAIL_ADDR, INSTALL_FLOOR, "
                + "INSTALL_INSTITUTE, INSTALL_TYPE, SERVICE_TYPE, NET_TYPE, INSTALL_DATE, IS_OUTDOOR, NET_ENV, LAT, LNT, WORK_DATE) "
                + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            pstmt = con.prepareStatement(sql);

            for (Wifi wifi : wifis) {
                pstmt.setString(1, wifi.getX_SWIFI_MGR_NO());
                pstmt.setString(2, wifi.getX_SWIFI_WRDOFC());
                pstmt.setString(3, wifi.getX_SWIFI_MAIN_NM());
                pstmt.setString(4, wifi.getX_SWIFI_ADRES1());
                pstmt.setString(5, wifi.getX_SWIFI_ADRES2());
                pstmt.setString(6, wifi.getX_SWIFI_INSTL_FLOOR());
                pstmt.setString(7, wifi.getX_SWIFI_INSTL_MBY());
                pstmt.setString(8, wifi.getX_SWIFI_INSTL_TY());
                pstmt.setString(9, wifi.getX_SWIFI_SVC_SE());
                pstmt.setString(10, wifi.getX_SWIFI_CMCWR());
                pstmt.setString(11, wifi.getX_SWIFI_CNSTC_YEAR());
                pstmt.setString(12, wifi.getX_SWIFI_INOUT_DOOR());
                pstmt.setString(13, wifi.getX_SWIFI_REMARS3());
                pstmt.setDouble(14, wifi.getLAT());
                pstmt.setDouble(15, wifi.getLNT());
                pstmt.setString(16, wifi.getWORK_DTTM());

                pstmt.addBatch();
            }

            int[] result = pstmt.executeBatch();

//            con.commit(); // 커밋 수행 (자동 커밋으로 불필요)

            System.out.println("Inserted " + result.length + " rows.");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try{
                closeIO();
            } catch (SQLException e) {
                e.printStackTrace();}
        }

        return 1;
    }


    public void createTable(){
        try {
            con = DriverManager.getConnection(url, user, password);
            String sql = "CREATE TABLE WIFI (\n"
                + "\tID\tINTEGER PRIMARY KEY AUTOINCREMENT ,\n"
                + "\t`MG_NO`\tvarchar(255)\tUNIQUE,\n"
                + "\t`DISTRICT`\tvarchar(255)\tNULL,\n"
                + "\t`NAME`\tvarchar(255)\tNULL,\n"
                + "\t`STREET_ADDR`\tvarchar(255)\tNULL,\n"
                + "\t`DETAIL_ADDR`\tvarchar(255)\tNULL,\n"
                + "\t`INSTALL_FLOOR`\tvarchar(255)\tNULL,\n"
                + "\t`INSTALL_INSTITUTE`\tvarchar(255)\tNULL,\n"
                + "\t`INSTALL_TYPE`\tvarchar(255)\tNULL,\n"
                + "\t`SERVICE_TYPE`\tvarchar(255)\tNULL,\n"
                + "\t`NET_TYPE`\tvarchar(255)\tNULL,\n"
                + "\t`INSTALL_DATE`\tDATE\tNULL,\n"
                + "\t`IS_OUTDOOR`\tvarchar(255)\tNULL,\n"
                + "\t`NET_ENV`\tvarchar(255)\tNULL,\n"
                + "\t`LAT`\tfloat\tNULL,\n"
                + "\t`LNT`\tfloat\tNULL,\n"
                + "\t`WORK_DATE`\tDATE\tNULL)\n;";
            pstmt = con.prepareStatement(sql);
            pstmt.execute();

            sql = "CREATE TABLE SEARCH_HISTORY (\n "
                + "\tID\tINTEGER PRIMARY KEY AUTOINCREMENT , "
                + "\t`LAT`\tfloat NOT NULL,\n"
                + "\t`LNT`\tfloat NOT NULL,\n"
                + "\t`DATE` DATETIME DEFAULT (datetime('now', 'localtime')));";
            pstmt = con.prepareStatement(sql);
            pstmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try{
                closeIO();
            } catch (SQLException e) {
                e.printStackTrace();}
        }

    }

    public void dropTableIfExists(){
        try {
            con = DriverManager.getConnection(url, user, password);
            String sql = " DROP TABLE IF EXISTS `WIFI`\n";
            pstmt = con.prepareStatement(sql);
            pstmt.execute();

            sql = " DROP TABLE IF EXISTS `SEARCH_HISTORY`;";
            pstmt = con.prepareStatement(sql);
            pstmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            try{
                closeIO();
            } catch (SQLException e) {
                e.printStackTrace();}
        }

    }

    public List<Wifi> getWifiInfos(){
        List<Wifi> wifis = new ArrayList<>();
        try {
            con = DriverManager.getConnection(url, user, password);

            String sql = "SELECT * FROM WIFI LIMIT 20";
            pstmt = con.prepareStatement(sql);

            rs = pstmt.executeQuery();

            wifis = toWifi(rs);

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            try{
                closeIO();
            } catch (SQLException e) {
                e.printStackTrace();}
        }

        return wifis;
    }

    public List<Wifi> getWifiInfosByDistance(double lat, double lng) {
        List<Wifi> wifis = new ArrayList<>();
        try {
            con = DriverManager.getConnection(url, user, password);

            String sql = "select * "
                + " from ( "
                + " select id, "
                + " 6371 * 2 * ASIN(SQRT( "
                + " POWER(SIN((RADIANS(?) - RADIANS(LAT)) / 2), 2) + "
                + " COS(RADIANS(?)) * COS(RADIANS(LAT)) * "
                + " POWER(SIN((RADIANS(?) - RADIANS(LNT)) / 2), 2) "
                + " )) AS distance_in_km "
                + " FROM WIFI "
                + " ) AS distance "
                + " JOIN WIFI ON distance.id = WIFI.id "
                + " ORDER BY distance.distance_in_km "
                + " LIMIT 20 ";

            pstmt = con.prepareStatement(sql);
            pstmt.setDouble(1, lat);
            pstmt.setDouble(2, lat);
            pstmt.setDouble(3, lng);


            rs = pstmt.executeQuery();

            saveHistory(lat, lng);
            wifis = toWifi(rs);

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            try{
                closeIO();
            } catch (SQLException e) {
                e.printStackTrace();}
        }

        return wifis;
    }

    private void saveHistory(double lat, double lng) throws SQLException {
        String sql = "INSERT INTO SEARCH_HISTORY (LAT, LNT) VALUES (?, ?);";
        pstmt = con.prepareStatement(sql);
        pstmt.setString(1, String.valueOf(lat));
        pstmt.setString(2, String.valueOf(lng));
        pstmt.execute();
    }

    private List<Wifi> toWifi(ResultSet rs) throws SQLException {
        List<Wifi> wifis = new ArrayList<>();
        while(rs.next()) {
            wifis.add(Wifi.builder()
                    .X_SWIFI_MGR_NO(rs.getString("MG_NO"))
                    .X_SWIFI_WRDOFC(rs.getString("DISTRICT"))
                    .X_SWIFI_MAIN_NM(rs.getString("NAME"))
                    .X_SWIFI_ADRES1(rs.getString("STREET_ADDR"))
                    .X_SWIFI_ADRES2(rs.getString("DETAIL_ADDR"))
                    .X_SWIFI_INSTL_FLOOR(rs.getString("INSTALL_FLOOR"))
                    .X_SWIFI_INSTL_MBY(rs.getString("INSTALL_INSTITUTE"))
                    .X_SWIFI_INSTL_TY(rs.getString("INSTALL_TYPE"))
                    .X_SWIFI_SVC_SE(rs.getString("SERVICE_TYPE"))
                    .X_SWIFI_CMCWR(rs.getString("NET_TYPE"))
                    .X_SWIFI_CNSTC_YEAR(rs.getString("INSTALL_DATE"))
                    .X_SWIFI_INOUT_DOOR(rs.getString("IS_OUTDOOR"))
                    .X_SWIFI_REMARS3(rs.getString("NET_ENV"))
                    .LAT(rs.getDouble("LAT"))
                    .LNT(rs.getDouble("LNT"))
                    .WORK_DTTM(rs.getString("WORK_DATE"))
                    .distance(rs.getDouble("distance_in_km"))
                    .build());
        }
        return wifis;
    }




    private void closeIO() throws SQLException {
        if (rs != null && !rs.isClosed()){
            rs.close();}
        if (pstmt != null && !pstmt.isClosed()){
            pstmt.close();}
        if (con != null && !con.isClosed()) {
            con.close();}
    }

    public List<SearchHistory> getHistorys(){
        List<SearchHistory> histories = new ArrayList<>();
        try {
            con = DriverManager.getConnection(url );

            String sql = "SELECT * FROM SEARCH_HISTORY LIMIT 20";
            pstmt = con.prepareStatement(sql);

            rs = pstmt.executeQuery();

            while(rs.next()){
                histories.add(SearchHistory.builder()
                        .id(rs.getInt("ID"))
                        .lat(rs.getDouble("LAT"))
                        .lng(rs.getDouble("LNT"))
                        .date(rs.getString("DATE"))
                        .build());
            }

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            try{
                closeIO();
            } catch (SQLException e) {
                e.printStackTrace();}
        }

        return histories;
    }

}
