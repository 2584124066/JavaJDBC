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

    //更新  数据库操作通常包装一个类存放参数值  项目中方法参数最多3个 多出的封装一个类
    public static void update(User user){
        Connection conn = DbUtils.getConnection();
        String sql = "update user1 set name=?,age=? where id=? ";
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            ps.setObject(1,user.getName());
            ps.setObject(2,user.getAge());
            ps.setObject(3,user.getId());
            int row = ps.executeUpdate();
            if (row>0){
                System.out.println("更新成功");
            }else {
                System.out.println("更新失败");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DbUtils.close(conn,ps);
        }
    }

    public static void main(String[] args) {
//        select();
//        add();
//        del(11);
        update(new User(11,"诸葛亮",31));

    }
    //添加
    public static void add() {
        //调用自定义封装类的方法getConnection() 获取数据库连接对象
        Connection conn = DbUtils.getConnection();
        String sql = "insert into user1(id,name,age) values(?,?,?) ";
        //数据库里面id是主键且自增长 插入可以忽略或者设置null 数据库会自动给id设值
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            ps.setObject(1,null); //给第1个参数设置值
            ps.setObject(2,"曹操"); //给第2个参数设置值
            ps.setObject(3,56); //给第2个参数设置值
            int row = ps.executeUpdate();
            if (row>0){
                System.out.println("添加成功");
            }else {
                System.out.println("添加失败");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DbUtils.close(conn,ps);
        }
    }

    //删除    删除操作通常是绑定id
    public static void del(Integer id){
        Connection conn = DbUtils.getConnection();
        String sql = "delete from user1 where id=?";
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            ps.setObject(1,id);
            int row = ps.executeUpdate();
            if (row>0){
                System.out.println("删除成功");
            }else {
                System.out.println("删除失败");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtils.close(conn,ps);
        }
    }


}
