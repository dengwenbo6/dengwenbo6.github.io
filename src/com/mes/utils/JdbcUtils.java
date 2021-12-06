package com.mes.utils;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class JdbcUtils {
    public static DruidDataSource dataSource;
    private static ThreadLocal<Connection> conns = new ThreadLocal<Connection>();

    static {


        try {

            Properties properties = new Properties();
            // 读取jdbc.properties属性配置文件
            InputStream inputStream = JdbcUtils.class.getClassLoader().getResourceAsStream("jdbc.properties");
            //  从流找中加载数据
            properties.load(inputStream);
            //  创建数据库连接池
            dataSource = (DruidDataSource) DruidDataSourceFactory.createDataSource(properties);
            System.out.println(dataSource.getConnection());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    /**
     * 获取数据库连接池中的连接
     * @return  如果获取null就说明连接失败，有值就说明连接成功
     */
    public static Connection getConnection(){
        Connection conn = conns.get(); // 从线程中获取连接线程，
        // 如果有值，说明已经获取了连接，就不需要再从数据库中取出来连接，保证了一次事务操作只用一个连接
        if (conn ==null){
            try {
                conn = dataSource.getConnection();  // 从数据库连接池中获取连接
                conns.set(conn);  // 保存到ThreadLocal 对象中，供后面的jdbc使用
                conn.setAutoCommit(false);  //设置为手动管理事务
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return conn;
    }

    /**
     * 提交事务并关闭释放连接
     */
    public static void commitAndClose(){
        Connection connection = conns.get(); // 获得连接对象
        if (connection!=null){ // 如果连接不等于空，表示之前使用过连接，操作过数据库
            try {
                connection.commit(); // 提交事务
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                try {
                    connection.close();  // 关闭连接释放资源
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        }
        // 最后必须使用remove操作，因为Tomact底层使用了线程池
        conns.remove();
    }
    /**
     * 回滚事务并关闭释放连接
     */
    public static void rollbackAndClose(){
        Connection connection = conns.get(); // 获得连接对象
        if (connection!=null){ // 如果连接不等于空，表示之前使用过连接，操作过数据库
            try {
                connection.rollback(); // 回滚事务
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                try {
                    connection.close();  // 关闭连接释放资源
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        }
        // 最后必须使用remove操作，因为Tomact底层使用了线程池
        conns.remove();
    }
//    /**
//     * 关闭连接，放回数据库连接池
//     * @param conn
//     */
//    public static void close(Connection conn){
//        if (conn != null) {
//            try {
//                conn.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//
//        }
//    }

}
