package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DbUtils {
    private static String DRIVER = null; //驱动包
    private static String URL = null;//数据库的连接地址及指定字符编码
    private static String ACCOUNT = null;//数据库账号
    private static String PASSWORD = null;//数据库密码

    static {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("src/db.properties"));
            DRIVER = properties.getProperty("driver");
            URL = properties.getProperty("url");
            ACCOUNT = properties.getProperty("account");
            PASSWORD = properties.getProperty("password");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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

    //add update delete
    public static void executeSQL(String sql, Object... obj) {
        if (sql == null || sql == "" || obj == null || obj.length <= 0) {
            return;
        }
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql);
            for (int i = 0; i < obj.length; i++) {
                ps.setObject(i + 1, obj[i]); //给第i+1个参数设置值 位置从1开始
            }
            int i = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(conn, ps);
        }
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
