package com.mes.test;

import com.mes.utils.JdbcUtils;
import org.junit.Test;

import java.sql.Connection;

public class JdbcUtilsTest {

    @Test
    public void testJdbcUtils() {
        for (int i = 0; i < 100; i++) {
            Connection connection = JdbcUtils.getConnection();
            System.out.println(connection);
//            //  每次连接完成必须释放连接
//            JdbcUtils.close(connection);
        }
    }
}
