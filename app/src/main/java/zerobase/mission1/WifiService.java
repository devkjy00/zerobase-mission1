package zerobase.mission1;

import java.sql.*;
import java.util.List;

public class WifiService {

    private PreparedStatement pstmt = null;
    private Connection con = null;
    private ResultSet rs = null;

    private String url = "jdbc:sqlite:test.db";
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
            con = DriverManager.getConnection(url, user, password);
            String sql = "INSERT INTO WIFI (MG_NO, DISTRICT, NAME, STREET_ADDR, DETAIL_ADDR, INSTALL_FLOOR, "
                + "INSTALL_INSTITUTE, SERVICE_TYPE, NET_TYPE, INSTALL_DATE, IS_OUTDOOR, NET_ENV, LAT, LNT, WORK_DATE) "
                + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            pstmt = con.prepareStatement(sql);

            for (Wifi wifi : wifis) {
                pstmt.setString(1, wifi.getX_SWIFI_MGR_NO());
                pstmt.setString(2, wifi.getX_SWIFI_WRDOFC());
                pstmt.setString(3, wifi.getX_SWIFI_MAIN_NM());
                pstmt.setString(4, wifi.getX_SWIFI_ADRES1());
                pstmt.setString(5, wifi.getX_SWIFI_ADRES2());
                pstmt.setString(6, wifi.getX_SWIFI_INSTL_FLOOR());
                pstmt.setString(7, wifi.getX_SWIFI_INSTL_TY());
                pstmt.setString(8, wifi.getX_SWIFI_SVC_SE());
                pstmt.setString(9, wifi.getX_SWIFI_CMCWR());
                pstmt.setString(10, wifi.getX_SWIFI_CNSTC_YEAR());
                pstmt.setString(11, wifi.getX_SWIFI_INOUT_DOOR());
                pstmt.setString(12, wifi.getX_SWIFI_REMARS3());
                pstmt.setDouble(13, wifi.getLAT());
                pstmt.setDouble(14, wifi.getLNT());
                pstmt.setString(15, wifi.getWORK_DTTM());

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
                + "\t`SERVICE_TYPE`\tvarchar(255)\tNULL,\n"
                + "\t`NET_TYPE`\tvarchar(255)\tNULL,\n"
                + "\t`INSTALL_DATE`\tDATE\tNULL,\n"
                + "\t`IS_OUTDOOR`\tvarchar(255)\tNULL,\n"
                + "\t`NET_ENV`\tvarchar(255)\tNULL,\n"
                + "\t`LAT`\tfloat\tNULL,\n"
                + "\t`LNT`\tfloat\tNULL,\n"
                + "\t`WORK_DATE`\tDATE\tNULL\n"
                + ");";

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
            String sql = "DROP TABLE IF EXISTS `WIFI`;";

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

    public void select(){
        try {
            con = DriverManager.getConnection(url, user, password);

            String sql = "SELECT * FROM WIFI LIMIT 100";
            pstmt = con.prepareStatement(sql);
//            pstmt.setString(1, "1");

            rs = pstmt.executeQuery();
            while(rs.next()) {
                System.out.println(rs.getString("id") + " " + rs.getString("name"));
            }

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            try{
                closeIO();
            } catch (SQLException e) {
                e.printStackTrace();}
        }
    }



    private void closeIO() throws SQLException {
        if (rs != null && !rs.isClosed()){
            rs.close();}
        if (pstmt != null && !pstmt.isClosed()){
            pstmt.close();}
        if (con != null && !con.isClosed()) {
            con.close();}
    }
}
