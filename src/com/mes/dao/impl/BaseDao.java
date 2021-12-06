package com.mes.dao.impl;

import com.mes.utils.JdbcUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * 操作数据库
 * abstract  为了方便进行复用代码
 */
public abstract class BaseDao {
    // 使用DbUtils操作数据库
    private QueryRunner queryRunner = new QueryRunner();

    /**
     *update() 方法用来执行：Insert、update、delete语句
     * @param sql  sql 语句
     * @param args  传入的参数
     * @return  如果返回-1表示执行失败，其他的返回表示影响的行数
     */
    public int update(String sql,Object ...args){
        Connection connection = JdbcUtils.getConnection();

        try {
            return queryRunner.update(connection,sql,args);
        } catch (SQLException e) {
            e.printStackTrace();
            throw  new RuntimeException(e);
        }
    }

    /**
     * 查询返回一个JavaBean的sql语句
     * @param type  返回的对象类型
     * @param sql  执行的sql语句
     * @param args  sql对应的参数值
     * @param <T>  返回的类型的泛型
     * @return   查询数据，如果查询到了返回整条数据，否则返回null
     */
    public <T>T queryForOne(Class<T> type,String sql,Object...args ){
        Connection connection = JdbcUtils.getConnection();
        try {
            return queryRunner.query(connection,sql,new BeanHandler<T>(type),args);
        } catch (SQLException e) {
            e.printStackTrace();
            throw  new RuntimeException(e);
        }


    }

    /**
     * 查询返回多个JavaBean的sql语句
     * @param type  返回的对象类型
     * @param sql  执行的sql语句
     * @param args  sql对应的参数值
     * @param <T>  返回的类型的泛型
     * @return
     */
    public <T> List<T> queryForList(Class<T> type, String sql, Object...args ){
        Connection connection = JdbcUtils.getConnection();
        try {
            return queryRunner.query(connection,sql,new BeanListHandler<T>(type),args);
        } catch (SQLException e) {
            e.printStackTrace();
            throw  new RuntimeException(e);
        }
    }
    /**
     * 执行返回一行一列的sql语句
     * @param sql  执行的sql语句
     * @param args  sql对应的参数值
     * @param <T>  返回的类型的泛型
     * @return
     */
    public Object querySingleValue(String sql,Object...args){
        Connection connection = JdbcUtils.getConnection();
        try {
            return queryRunner.query(connection,sql,new ScalarHandler(),args);
        } catch (SQLException e) {
            e.printStackTrace();
            throw  new RuntimeException(e);
        }
    }

}
