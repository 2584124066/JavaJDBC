import utils.DbUtils;

import java.sql.*;

public class Test {
    //查询
    public static void select() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            //加载驱动
            Class.forName("com.mysql.jdbc.Driver");
            //连接数据库
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test?useSSL=false&?characterEncoding=utf-8", "root", "root");
            //sql语句
            String sql = "select * from user1";
            //准备语句
            ps = conn.prepareStatement(sql);
            //获取结果集
            rs = ps.executeQuery();
            //遍历
            while (rs.next()) {
                //getxxx(第i个字段)
                int id = rs.getInt(1);
                String name = rs.getString(2);
                int age = rs.getInt(3);
                System.out.println(id + "\t" + name + "\t" + age);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
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


    public static void main(String[] args) {
//        select();
//        add(new User("曹操", 31));
//        del(new User(6));
        update(new User(7, "诸葛亮", 31));
    }

    //添加  数据库操作通常包装一个类存放参数值  项目中方法参数最多3个 多出的封装一个类
    public static void add(User user) {
        //数据库里面id是主键且自增长 插入可以忽略 数据库会自动给id设值
        String sql = "insert into user1(name,age) values(?,?) ";
        //使用自定义封装方法 通用增删改 数据参数要一致
        DbUtils.executeSQL(sql, user.getName(), user.getAge());
    }

    //删除    删除操作通常是绑定id
    public static void del(User user) {
        String sql = "delete from user1 where id=?";
        DbUtils.executeSQL(sql, user.getId());
    }

    //更新
    public static void update(User user) {
        String sql = "update user1 set name=?,age=? where id=? ";
        DbUtils.executeSQL(sql, user.getName(), user.getAge(), user.getId());
    }
}
