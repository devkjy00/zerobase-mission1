public class JdbcTest {

    public static void main(String[] args) {


    }

    private static void test(String sql) {

        PreparedStatement pstmt = null;
        Connection con = null;
        ResultSet rs = null;


//        1. JDBC 드라이버 로드, 클래스의 이름으로 로드(ClassNotFoundException 예외 처리 필요)
        try {
            Class.forName("com.maraidb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

//        2. 데이터베이스 연결 (SQLException 예외 처리 필요)
        try {
            con = DriverManager.getConnection("jdbc:mariadb://southoftheriver.synology.me:3307/", "root",
                "Mwmfrlwk01!");

//        3. PreparedStatament 객체 생성후 SQL문 실행ㅓ
            // a. select 문
            String sql = "SELECT * FROM test where id = ?";

            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, "1");

            rs = pstmt.executeQuery();

            while (rs.next()) {
                System.out.println(rs.getString("name"));
            }

            // b. insert 문
            String sql = "INSERT INTO test (id, name) VALUES (?, ?)";

            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, "1");
            pstmt.setString(2, "홍길동");

            int affected = pstmt.executeUpdate();

            if(affected > 0) {
                System.out.println("성공");
            } else {
                System.out.println("실패");
            }

            // c. update 문
            String sql = "UPDATE test SET name = ? WHERE id = ?";

            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, "홍길동");
            pstmt.setString(2, "1");

            int affected = pstmt.executeUpdate();

            if(affected > 0) {
                System.out.println("성공");
            } else {
                System.out.println("실패");
            }

            // d. delete 문
            String sql = "DELETE FROM test WHERE id = ?";

            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, "1");

            int affected = pstmt.executeUpdate();

            if(affected > 0) {
                System.out.println("성공");
            } else {
                System.out.println("실패");
            }


        } catch (SQLException e) {
            e.printStackTrace();

        } finally {

//        6. 연결 종료, 객체가 null인지와 연결 상태를 확인한 후 종료
            try{
                if (rs != null && !rs.isClosed()){
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if (psmt != null and !pstmt.isClosed()){
                    pstmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if (!con.isClosed(){
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
