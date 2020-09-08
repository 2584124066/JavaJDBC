package utils;

import java.sql.*;

public class DbUtils {
    private final static String DRIVER = "com.mysql.jdbc.Driver"; //驱动包
    private final static String URL = "jdbc:mysql://localhost:3306/test?characterEncoding=utf-8&useSSL=false";//数据库的连接地址及指定字符编码
    private final static String ACCOUNT = "root";
    private final static String PASSWORD = "root";

    //获取数据库连接对象
    public static Connection getConnection() {
        Connection conn = null;
        try {
            //加载驱动
            Class.forName(DRIVER);
            //连接数据库
            conn = DriverManager.getConnection(URL, ACCOUNT, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    //关闭数据库连接
    public static void close(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void close(Connection conn, PreparedStatement ps) {
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public static void close(Connection conn, PreparedStatement ps, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
